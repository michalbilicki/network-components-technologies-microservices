package ApplicationPorts.Infrastructure;

import DomainModel.Client;
import exceptions.RepositoryException;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface ClientServicePort {

    Client get(UUID id) throws RepositoryException;

    List<Client> getAll();

    void update(Client arg) throws RepositoryException;

    List<Client> getFiltered(Predicate<Client> predicate);

    void add(Client arg) throws RepositoryException;
}
