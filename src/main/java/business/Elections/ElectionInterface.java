package business.Elections;

public interface ElectionInterface {

    enum Status {
        ACTIVE, FINISHED, CANCELED
    }

    public void voteFor(String voterId, String votedId);

    public Integer countMissingVotes();

    public Integer countVotes();

    public String getWinner();

    public String getUnamimousWinner();

    public boolean isActive();

    public boolean isFinished();

    public boolean isCanceled();
}