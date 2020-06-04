package exceptions;

public class RepositoryConverterException extends Exception {
    public RepositoryConverterException(String msg){
        super("CONVERSION ERROR: " + msg);
    }
}
