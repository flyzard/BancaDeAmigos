package business.Elections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Class asbtraca para implementação de funcionalidade de objectos do tipo
 * ElectionInterface
 * 
 * @author grupo 5
 * @version 1
 * 
 */
public abstract class AbstractElection implements ElectionInterface {

    /**
     * Total de votantes para terminar votação
     */
    private Integer totalVoters;

    /**
     * Estado da Eleição
     */
    private Status status;

    /**
     * Estreutura para guardar os votos de uma eleição
     */
    private HashMap<String, String> votes;

    /**
     * Constructor
     * 
     * @param totalVoters numero total de votantes para terminar a eleição
     */
    public AbstractElection(Integer totalVoters) {
        this.votes = new HashMap<String, String>();
        this.status = Status.ACTIVE;
        this.totalVoters = totalVoters;
    }

    /**
     * Efectua voto de um utilizador noutro utilizador
     * 
     * @param voterId Identificador do votante
     * @param votedId Identificador do votado
     */
    public void voteFor(String voterId, String votedId) {
        this.votes.put(voterId, votedId);

        if (countMissingVotes() == 0) {
            this.status = Status.FINISHED;
        }
    }

    /**
     * @return Integer - O numero de votos em falta para terminat votação
     */
    public Integer countMissingVotes() {
        return this.countVotes() - totalVoters;
    }

    /**
     * @return Integer - O numero de votos já efectuados
     */
    public Integer countVotes() {
        return this.votes.size();
    }

    /**
     * @return String - O identificador do vencedor da eleição
     */
    public String getWinner() {
        Collection<String> valueVotes = votes.values();
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String vote : valueVotes) {
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

    /**
     * @return String - O identificador do utilizador que 
     *                  venceu a eleição ou null, caso 
     *                  não exista unanimidade.
     */
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

    /**
     * @return Boolean - Verifica se a eleição se encontra activa
     */
    public boolean isActive() {
        return this.status == Status.ACTIVE;

    }

    /**
     * @return Boolean - Verifica se a eleição já terminou
     */
    public boolean isFinished() {
        return this.status == Status.FINISHED;
    }

    /**
     * @return Boolean - Verifica se a eleição foi cancelada
     */
    public boolean isCanceled() {
        return this.status == Status.CANCELED;
    }
}