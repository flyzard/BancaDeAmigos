package business.Exceptions;

public class NoUserLoggedInException extends Exception {
    public NoUserLoggedInException() {
        super("Não existe um utilizador activo!");
    }
}