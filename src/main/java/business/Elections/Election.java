package business.Elections;


/**
 * Class para registar eleição de fiel depositário
 * 
 * @author grupo 5
 * @version 1
 * 
 */
public class Election extends AbstractElection {

    /**
     * Construtor 
     * 
     * @param totalVoters total de votantes necessários para concluir a votação
     */
    public Election(Integer totalVoters) {
        super(totalVoters);
    }
    
}