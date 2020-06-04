package pl.lapciakbilicki.RepositoriesAdapter.Repository.repository;

public class RepositoryException extends Exception {
    public RepositoryException(String msg){
        super("CONVERSION ERROR: " + msg);
    }
}
