package business;

import java.util.*;
import java.util.stream.Collectors;

import business.GroupRequests.ChangeGroupResponsibleRequest;
import business.GroupRequests.GroupRequestInterface;
import business.GroupRequests.IntegrateGroupRequest;
import business.transactions.InitialTransaction;
import business.transactions.*;

import java.security.Principal;
import java.security.acl.Group;

public class Grupo implements Group {

	private final String name;
	private boolean active = false;
	Set<User> users = new TreeSet<User>();
	private ArrayList<GroupRequestInterface> requests = null;
	private ArrayList<Transaction> transactions = null;

	// Fiel depositário
	private User responsible = null;

	public Grupo(final String name, final User responsible, final double initialCredit) {
		this.name = name;
		this.active = true;
		this.users = new TreeSet<User>();
		this.requests = new ArrayList<GroupRequestInterface>();
		this.transactions = new ArrayList<Transaction>();
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
		if (active) {
			return this.requests.add(new IntegrateGroupRequest(user, this.users));
		}

		return false;
	}

	public boolean acceptNewUser(final User loggedUser, final String newUserEmail) {
		for (final GroupRequestInterface request : getPendingMembershipRequests()) {
			if (request.getFrom().getEmail() == newUserEmail) {
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
			return this.requests.add(new ChangeGroupResponsibleRequest(loggedUser, this.users));
		}

		return false;
	}

	public boolean voteForGroupResponsible(final User loggedUser, final String newResponsibleEmail) {
		Optional<GroupRequestInterface> req = getChangeResponsibleRequest();

		if (req.isPresent()) {
			final User user = this.getUserByEmail(newResponsibleEmail);
			final GroupRequestInterface r = req.get();
			if (null != user) {
				r.userVoteForUser(loggedUser, user);
				return true;
			}			
		}

		return false;
	}

	public boolean getChangeResponsibleVoting(final User loggedUser) {
		Optional<GroupRequestInterface> req = getChangeResponsibleRequest();

		if (req.isPresent()) {
			final ChangeGroupResponsibleRequest r = (ChangeGroupResponsibleRequest) req.get();
			if (r.isAccepted()) {
				r.
			}
				;
				return true;
			}
		}
	}

	public boolean makePayment(final User loggedUser, final String destinyEmail, final double amount) {
		return false;
	}

	public boolean getMyBalance(final User loggedUser) {
		return false;
	}

	public boolean abandonGroup(final User loggedUser) {
		return false;
	}

	public boolean closeGroup(final User loggedUser) {
		return false;
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

	private Optional<GroupRequestInterface> getChangeResponsibleRequest() {
		return this.requests
			.stream()
			.filter(req -> req instanceof ChangeGroupResponsibleRequest)
			.filter(req -> req.isProcessing())
			.findFirst();
	}

	private User getUserByEmail(String email) {
		for (User u : this.users) {
			if (u.getEmail() == email) {
				return u;
			}
		}

		return null;
	}
}
