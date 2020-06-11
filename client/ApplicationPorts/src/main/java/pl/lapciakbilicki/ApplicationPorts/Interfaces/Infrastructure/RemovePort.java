package pl.lapciakbilicki.ApplicationPorts.Interfaces.Infrastructure;

import pl.lapciakbilicki.RepositoriesAdapter.Repository.repository.RepositoryException;

import java.util.UUID;

public interface RemovePort<T> {
    public void remove(T arg) throws RepositoryException;

    public void remove(UUID id) throws RepositoryException;
}
