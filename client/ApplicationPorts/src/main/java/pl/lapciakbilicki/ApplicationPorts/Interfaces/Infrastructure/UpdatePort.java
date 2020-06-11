package pl.lapciakbilicki.ApplicationPorts.Interfaces.Infrastructure;

import pl.lapciakbilicki.RepositoriesAdapter.Repository.repository.RepositoryException;

public interface UpdatePort<T> {
    public void update(T arg) throws RepositoryException;
}
