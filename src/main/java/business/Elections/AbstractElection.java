package business.Elections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractElection implements ElectionInterface {

    private Integer totalVoters;
    private Status status;
    private HashMap<String, String> votes;

    public AbstractElection(Integer totalVoters) {
        this.votes = new HashMap<String, String>();
        this.status = Status.ACTIVE;
        this.totalVoters = totalVoters;
    }

    public void voteFor(String voterId, String votedId) {
        this.votes.put(voterId, votedId);

        if (countMissingVotes() == 0) {
            this.status = Status.FINISHED;
        }
    }

    public Integer countMissingVotes() {
        return this.countVotes() - totalVoters;
    }

    public Integer countVotes() {
        return this.votes.size();
    }

    public String getWinner() {
        Collection<String> valueVotes = votes.values();
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String vote : valueVotes) {
            // Collections.frequency(valueVotes, vote)
            Integer count = frequencyMap.get(vote);
            if (count == null)
                count = 0;

            frequencyMap.put(vote, count + 1);
        }

        int max = 0;
        String voted = null;

        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                voted = entry.getKey();
            }
        }

        return voted;
    }

    public String getUnamimousWinner() {

        Collection<String> valueVotes = votes.values();
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String vote : valueVotes) {
            // Collections.frequency(valueVotes, vote)
            Integer count = frequencyMap.get(vote);
			if (count == null)
				count = 0;

			frequencyMap.put(vote, count + 1);
        }

        if (frequencyMap.size() > 1) {
            return null;
        }

        int max = 0;
        String voted = null;

        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            if(entry.getValue() > max) {
                max = entry.getValue();
                voted = entry.getKey();
            }
		}
        
        return voted;
    }

    public boolean isActive() {
        return this.status == Status.ACTIVE;

    }

    public boolean isFinished() {
        return this.status == Status.FINISHED;
    }

    public boolean isCanceled() {
        return this.status == Status.CANCELED;
    }
}