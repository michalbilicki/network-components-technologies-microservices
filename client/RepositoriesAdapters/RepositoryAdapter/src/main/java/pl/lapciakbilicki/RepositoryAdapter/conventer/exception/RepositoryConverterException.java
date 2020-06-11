package pl.lapciakbilicki.RepositoryAdapter.conventer.exception;

public class RepositoryConverterException extends Exception {
    public RepositoryConverterException(String msg){
        super("CONVERSION ERROR: " + msg);
    }
}
