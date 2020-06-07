package business;

import java.util.*;
import java.util.stream.Collectors;

import business.Elections.Election;
import business.Elections.ElectionInterface;
import business.GroupRequests.GroupRequestInterface;
import business.GroupRequests.IntegrateGroupRequest;
import business.transactions.*;

import java.security.Principal;
import java.security.acl.Group;

/**
 * Represents a Grupo 
 * 
 * @author grupo 5
 * @version 1
 * 
 */
public class Grupo implements Group {

	/**
	 * Name of the group
	 */
	private final String name;

	/**
	 * Status of the group
	 */
	private boolean active = false;

	/**
	 * The users of the group
	 */
	Set<User> users = new TreeSet<User>();

	/**
	 * The request made in the group
	 */
	private ArrayList<GroupRequestInterface> requests = null;

	/**
	 * The transactions made in the group
	 */
	private ArrayList<Transaction> transactions = null;

	/**
	 * The lecetions made in the group
	 */
	private Stack<ElectionInterface> elections = null;

	/**
	 * Fiel depositário
	 */
	private User responsible = null;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param responsible
	 * @param initialCredit
	 */
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

	/**
	 * Get's the name of the group
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Add a new member to the group
	 * 
	 * @param User
	 * @return boolean 
	 */
	@Override
	public boolean addMember(final Principal user) {
		if (users.contains(user)) {
			return false;
		}

		return users.add((User) user);
	}

	/**
	 * Remove member from group
	 * 
	 * @param User - the user to remove from the group
	 * @return Boolean
	 */
	@Override
	public boolean removeMember(final Principal user) {
		return users.remove((User) user);
	}

	/**
	 * Check if a given user is a member
	 * 
	 * @param User - check if a user is a member
	 * return boolean
	 */
	@Override
	public boolean isMember(final Principal user) {
		return users.contains((User) user);
	}

	/**
	 * @return Enumeration - All Users in the groups
	 */
	@Override
	public Enumeration<? extends Principal> members() {
		return Collections.enumeration(users);
	}

	/**
	 * 
	 * @return User - fiel depositario
	 */
	public User getResponsible() {
		return this.responsible;
	}

	/**
	 * Change responsible in the group
	 * 
	 * @param responsible
	 * @return Boolean
	 */
	public boolean setResponsible(final User responsible) {
		this.responsible = responsible;

		return true;
	}

	/**
	 * Makes a request to adhere to the group
	 * 
	 * @param user
	 * @return boolean
	 */
	public boolean requestMembership(final User user) {
		return active && this.requests.add(new IntegrateGroupRequest(user, this.users));
	}

	/**
	 * Accept new user request
	 * 
	 * @param loggedUser
	 * @param newUserEmail
	 * @return boolean
	 */
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

	/**
	 * Refure new member request
	 * 
	 * @param loggedUser
	 * @param newUserEmail
	 * @return Boolean
	 */
	public boolean refuseNewUser(final User loggedUser, final String newUserEmail) {
		for (final GroupRequestInterface request : getPendingMembershipRequests()) {
			if (request.getFrom().getEmail() == newUserEmail) {
				return request.refuse(loggedUser);
			}
		}

		return false;
	}

	/**
	 * Make a accout reinforcement on this group
	 * 
	 * @param loggedUser
	 * @param amount
	 * @return boolean
	 */
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

	/**
	 * Acknowledge the reinforcemente made by other user
	 * 
	 * @param loggedUser
	 * @param userEmail
	 * @param amount
	 * @return boolean
	 */
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

	/**
	 * Makes request to change fiel depositario
	 * 
	 * @param loggedUser
	 * @return boolean
	 */
	public boolean changeResponsible(final User loggedUser) {
		if (active && loggedUser == responsible) {
			if (this.elections.empty() || (!this.elections.pop().isActive())) {
				return this.elections.add(new Election(getActiveUsers()));
			}			
		}

		return false;
	}

	/**
	 * Vote for a new group fiel depositario
	 * 
	 * @param loggedUser
	 * @param newResponsibleEmail
	 * @return boolean
	 */
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

	/**
	 * Get the result of a voting for new responsible
	 * 
	 * @param loggedUser
	 * @return String
	 */
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

	/**
	 * Make a payment from one user on the group to another
	 * 
	 * @param loggedUser
	 * @param destinyEmail
	 * @param amount
	 * @return boolean
	 */
	public boolean makePayment(final User loggedUser, final String destinyEmail, final double amount) {
		return this.transactions.add(new PaymentTransaction(loggedUser, getUserByEmail(destinyEmail), amount));
	}

	/**
	 * The the balance of a user on a group
	 * 
	 * @param loggedUser
	 * @return String
	 */
	public String getMyBalance(final User loggedUser) {
		String result = "";
		List<Transaction> stream = this.getMyValidTransactions(loggedUser);
		Double actualValue = 0.0;

		for (Transaction trans : stream) {
			if (trans.getAcceptedDate() != null) {
				result = result + trans.toStringUser(loggedUser) + "\n";
				actualValue += trans.getValueToUser(loggedUser);
			}
			
		}

		return result + "Saldo atual: " + actualValue;
	}

	/**
	 * List all valid transactions for a given User on the group
	 * 
	 * @param loggedUser
	 * @return List<Transaction>
	 */
	private List<Transaction> getMyValidTransactions(User loggedUser) {
		return this.transactions
			.stream()
			.filter(t -> t.getCanceledDate() == null)
			.filter(t -> (t.getFrom() == loggedUser) || (t.getTo() == loggedUser))
			.collect(Collectors.toList());
	}

	/**
	 * Abandon group
	 * 
	 * @param loggedUser
	 * @return Boolean
	 */
	public boolean abandonGroup(final User loggedUser) {
		if (loggedUser == responsible) {
			return false;	
		}

		return users.remove(loggedUser);	
	}

	/**
	 * Close the group
	 * 
	 * @param loggedUser
	 * @return boolean
	 */
	public boolean closeGroup(final User loggedUser) {
		if (loggedUser != responsible) {
			return false;
		}

		this.active = false;

		return true;
	}

	/**
	 * Get pedding membership requests
	 * 
	 * @return List<GroupRequestInterface>
	 */
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


	/**
	 * Get a user on the group by his email
	 * 
	 * @param email
	 * @return User
	 */
	private User getUserByEmail(String email) {
		return users.stream().filter(u -> u.getEmail() == email).findFirst().get();
	}

	/**
	 * Get all active users from the group
	 * @return
	 */
	private Integer getActiveUsers() {
		return this.users.size();
	}
}
