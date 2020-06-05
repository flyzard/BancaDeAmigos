package business.GroupRequests;

import business.User;

public interface GroupRequestInterface {

    public enum Status {
        PROCESSING, ACCEPTED, REFUSED, CANCELED
    }

    public boolean accept(User user);

    public boolean refuse(User user);

    public boolean cancel(User user);

    public Status getStatus();

    public boolean isRefused();

    public boolean isAccepted();

    public User getFrom();

    public boolean isProcessing();

    public void userVoteForUser(User voter, User voted);
}