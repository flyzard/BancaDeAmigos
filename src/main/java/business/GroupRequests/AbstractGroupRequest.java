package business.GroupRequests;

import java.util.HashMap;
import java.util.Set;

import business.User;

public abstract class AbstractGroupRequest implements GroupRequestInterface {
    
    private User from = null;
    private Status status = null;
    private User canceledBy = null;
    Set<User> voters = null;
    HashMap<User, Boolean> votes;

    public AbstractGroupRequest(final User from, final Set<User> voters) {
        this.from = from;
        this.status = Status.PROCESSING;
        this.voters = voters;
        this.votes = new HashMap<User, Boolean>();
    }

    public Status getStatus() {
        return this.status;
    }

    public boolean accept(final User user) {
        if (status == Status.PROCESSING) {
            votes.put(user, true);
            checkStatus();
            return true;
        }

        return false;
    }

    public boolean refuse(final User user) {
        if (status == Status.PROCESSING) {
            votes.put(user, false);
            checkStatus();
            return true;
        }

        return false;
    }

    public boolean cancel(final User user) {
        if (status == Status.PROCESSING) {
            this.canceledBy = user;
            this.status = Status.CANCELED;
            return true;
        }

        return false;
    }

    public boolean isRefused() {
        return this.status == Status.REFUSED;
    }

    public boolean isAccepted() {
        return this.status == Status.ACCEPTED;
    }

    public boolean isProcessing() {
        return this.status == Status.PROCESSING;
    }

    public User getFrom() {
        return this.from;
    }

    public boolean isCompleted() {
        return (votes.size() == voters.size()) && (status == Status.PROCESSING);
    }

    private void checkStatus() {
        if (this.status == Status.CANCELED) {
            return;
        }

        if (!isCompleted()) {
            this.status = Status.PROCESSING;
            return;
        }

        if (partialAcceptance()) {
            this.status = Status.ACCEPTED;
        } else {
            this.status = Status.REFUSED;
        }
    }

    private boolean partialAcceptance() {
        boolean state = true;

        for (final boolean vote : votes.values()) {
            state = state && vote;
        }

        return state;
    }
}