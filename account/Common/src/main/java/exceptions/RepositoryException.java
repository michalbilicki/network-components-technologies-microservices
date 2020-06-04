package exceptions;

public class RepositoryException extends Exception {
    public RepositoryException(String msg){
        super("CONVERSION ERROR: " + msg);
    }
}
