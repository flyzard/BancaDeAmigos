package business.Exceptions;

/**
 * Exceptção para ser levantado quando um dado utilizador não existe
 * 
 * @author grupo 5
 * @version 1
 * 
 */
public class NoUserLoggedInException extends Exception {
    /**
     * ID para serialização
     */
    private static final long serialVersionUID = -167386689083473684L;

    public NoUserLoggedInException() {
        super("Não existe um utilizador activo!");
    }
}
