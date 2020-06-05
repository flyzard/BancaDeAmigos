package business.GroupRequests;

import java.util.HashMap;
import java.util.Set;

import business.User;

public class ChangeGroupResponsibleRequest extends AbstractGroupRequest {

    HashMap<User, User> votes;

    public ChangeGroupResponsibleRequest(final User from, final Set<User> voters) {
        super(from, voters);

        this.votes = new HashMap<User, User>();
    }

    public void userVoteForUser(final User voter, final User voted) {
        this.votes.put(voter, voted);
    }
}