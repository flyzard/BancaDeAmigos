package business.Elections;

/**
 * Interface para definição de contrato Elections
 * ElectionInterface
 * 
 * @author grupo 5
 * @version 1
 * 
 */
public interface ElectionInterface {

    /**
     * Estado da eleição. Por defeito, ACTIVE
     */
    enum Status {
        ACTIVE, FINISHED, CANCELED
    }

    /**
     * Efectua voto de um utilizador noutro utilizador
     * 
     * @param voterId Identificador do votante
     * @param votedId Identificador do votado
     */
    public void voteFor(String voterId, String votedId);

    /**
     * @return Integer - O numero de votos em falta para terminat votação
     */
    public Integer countMissingVotes();

    /**
     * @return Integer - O numero de votos já efectuados
     */
    public Integer countVotes();

    /**
     * @return String - O identificador do vencedor da eleição
     */
    public String getWinner();

    /**
     * @return String - O identificador do utilizador que venceu a eleição ou null,
     *         caso não exista unanimidade.
     */
    public String getUnamimousWinner();

    /**
     * @return Boolean - Verifica se a eleição se encontra activa
     */
    public boolean isActive();

    /**
     * @return Boolean - Verifica se a eleição já terminou
     */
    public boolean isFinished();

    /**
     * @return Boolean - Verifica se a eleição foi cancelada
     */
    public boolean isCanceled();
}