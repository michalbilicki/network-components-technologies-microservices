package pl.lapciakbilicki.ApplicationPorts.Interfaces.Infrastructure;

import pl.lapciakbilicki.RepositoriesAdapter.Repository.repository.RepositoryException;

public interface AddPort<T> {
    public void add(T arg) throws RepositoryException;
}
