package business;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import business.Elections.Election;
import business.Elections.ElectionInterface;
import business.GroupRequests.GroupRequestInterface;
import business.GroupRequests.IntegrateGroupRequest;
import business.transactions.*;

import java.security.Principal;
import java.security.acl.Group;

public class Grupo implements Group {

	private final String name;
	private boolean active = false;
	Set<User> users = new TreeSet<User>();
	private ArrayList<GroupRequestInterface> requests = null;
	private ArrayList<Transaction> transactions = null;
	private Stack<ElectionInterface> elections = null;

	// Fiel depositário
	private User responsible = null;

	public Grupo(final String name, final User responsible, final double initialCredit) {
		this.name = name;
		this.active = true;
		this.users = new TreeSet<User>();
		this.requests = new ArrayList<GroupRequestInterface>();
		this.transactions = new ArrayList<Transaction>();
		this.elections = new Stack<ElectionInterface>();
		this.setResponsible(responsible);
		this.addMember(responsible);

		this.transactions.add(new InitialTransaction(responsible, initialCredit));
	}

	public String getName() {
		return this.name;
	}

	@Override
	public boolean addMember(final Principal user) {
		if (users.contains(user)) {
			return false;
		}

		return users.add((User) user);
	}

	@Override
	public boolean removeMember(final Principal user) {
		return users.remove((User) user);
	}

	@Override
	public boolean isMember(final Principal user) {
		return users.contains((User) user);
	}

	@Override
	public Enumeration<? extends Principal> members() {
		return Collections.enumeration(users);
	}

	public User getResponsible() {
		return this.responsible;
	}

	public boolean setResponsible(final User responsible) {
		this.responsible = responsible;

		return true;
	}

	public boolean requestMembership(final User user) {
		return active && this.requests.add(new IntegrateGroupRequest(user, this.users));
	}

	public boolean acceptNewUser(final User loggedUser, final String newUserEmail) {
		for (final GroupRequestInterface request : getPendingMembershipRequests()) {
			final User newUser = request.getFrom();
			if (newUser.getEmail() == newUserEmail) {
				this.users.add(newUser);
				return request.accept(loggedUser);
			}
		}

		return false;
	}

	public boolean refuseNewUser(final User loggedUser, final String newUserEmail) {
		for (final GroupRequestInterface request : getPendingMembershipRequests()) {
			if (request.getFrom().getEmail() == newUserEmail) {
				return request.refuse(loggedUser);
			}
		}

		return false;
	}

	public boolean reinforceAccount(final User loggedUser, final double amount) {
		if (!active) {
			return false;
		}

		Transaction trans = new ReinforcementTransaction(loggedUser, amount);

		if (loggedUser == responsible) {
			trans.accept();
		}

		this.transactions.add(trans);

		return true;
	}

	public boolean acknoledgeReinforcement(final User loggedUser, final String userEmail, final double amount) {
		if (active && loggedUser == responsible) {
			final List<Transaction> transactions = this.getPendingReinforcements();

			for (final Transaction trans : transactions) {
				if ((trans.getAmount() == amount) && (trans.getFrom().getEmail() == userEmail)) {
					trans.accept();
					return true;
				}
			}
		}

		return false;
	}

	public boolean changeResponsible(final User loggedUser) {
		if (active && loggedUser == responsible) {
			if (this.elections.empty() || (!this.elections.pop().isActive())) {
				return this.elections.add(new Election(getActiveUsers()));
			}			
		}

		return false;
	}

	public boolean voteForGroupResponsible(final User loggedUser, final String newResponsibleEmail) {
		if (this.elections.empty()) {
			return false;	
		}

		ElectionInterface election = this.elections.pop();

		if (election.isActive()) {
			election.voteFor(loggedUser.getEmail(), newResponsibleEmail);
			this.elections.push(election);
			return true;
		}
		
		return false;
	}

	public String getChangeResponsibleVoting(final User loggedUser) {
		ElectionInterface election = this.elections.pop();

		String winner = election.getUnamimousWinner();
		if (election.isFinished()) {
			if (winner != null) {
				responsible = getUserByEmail(winner);

				return "The new responsible is: " + winner;	
			}

			return "A votação não foi unanime!";
		}

		return "A votação ainda não terminou!";
	}

	public boolean makePayment(final User loggedUser, final String destinyEmail, final double amount) {
		return this.transactions.add(new PaymentTransaction(loggedUser, getUserByEmail(destinyEmail), amount));
	}

	public double getMyBalance(final User loggedUser) {
		for (Transaction trans : this.transactions) {
			
		}

		double balance = 0;

		Stream<Transaction> stream = this.getMyValidTransactions(loggedUser);
		
		balance += stream
			.filter(trans -> {
				boolean condition = true;
				condition = condition && trans.getAcceptedDate() != null;
				condition = condition && (trans instanceof ReinforcementTransaction || trans instanceof InitialTransaction);
				return  condition;
			})
			.mapToDouble(t -> t.getAmount()).reduce(0, Double::sum);

		stream = this.getMyValidTransactions(loggedUser);
		balance += stream
			.filter(trans -> trans.getAcceptedDate() != null && trans instanceof PaymentTransaction)
			.filter(trans -> trans.getTo() == loggedUser)
			.mapToDouble(t -> t.getAmount())
			.reduce(0, Double::sum);

		stream = this.getMyValidTransactions(loggedUser);
		balance -= stream
			.filter(trans -> trans.getAcceptedDate() != null && trans instanceof PaymentTransaction)
			.filter(trans -> trans.getFrom() == loggedUser)
			.mapToDouble(t -> t.getAmount()).reduce(0, Double::sum);

		return balance;
	}

	private Stream<Transaction> getMyValidTransactions(User loggedUser) {
		return this.transactions.stream().filter(t -> t.getCanceledDate() == null)
				.filter(t -> (t.getFrom() == loggedUser) || (t.getTo() == loggedUser));
	}

	public boolean abandonGroup(final User loggedUser) {
		if (loggedUser == responsible) {
			return false;	
		}

		return users.remove(loggedUser);	
	}

	public boolean closeGroup(final User loggedUser) {
		if (loggedUser != responsible) {
			return false;
		}

		this.active = false;

		return true;
	}

	public List<GroupRequestInterface> getPendingMembershipRequests() {
		return this.requests
			.stream()
			.filter(req -> req instanceof IntegrateGroupRequest)
			.filter(req -> req.isProcessing())
			.collect(Collectors.toList());
	}

	// private methods
	private List<Transaction> getPendingReinforcements() {
		return this.transactions
			.stream()
			.filter(trans -> trans instanceof ReinforcementTransaction)
			.filter(trans -> trans.getCanceledDate() == null && trans.getAcceptedDate() == null)
			.collect(Collectors.toList());
	}


	private User getUserByEmail(String email) {
		return users.stream().filter(u -> u.getEmail() == email).findFirst().get();
	}

	private Integer getActiveUsers() {
		return this.users.size();
	}
}
