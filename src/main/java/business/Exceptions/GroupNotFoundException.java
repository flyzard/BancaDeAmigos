package business.Exceptions;

/**
 * Exceptção para ser levantado quando um dado grupo não existe
 * 
 * @author grupo 5
 * @version 1
 * 
 */
public class GroupNotFoundException extends Exception{
    /**
	 * ID para serialização
	 */
	private static final long serialVersionUID = -493376027144156521L;

	public GroupNotFoundException(String groupName) {
        super("O grupo " + groupName + " não está registado!");
    }
}