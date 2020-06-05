package business.Exceptions;

public class GroupNotFoundException extends Exception{
    public GroupNotFoundException(String groupName) {
        super("O grupo " + groupName + " não está registado!");
    }
}