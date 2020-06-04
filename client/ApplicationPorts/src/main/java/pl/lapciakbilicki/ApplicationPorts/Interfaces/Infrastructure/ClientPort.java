package pl.lapciakbilicki.ApplicationPorts.Interfaces.Infrastructure;

import pl.lapciakbilicki.ApplicationCore.DomainModel.Client;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface ClientPort {

    public Client getClient(UUID id);

    public List<Client> getAllClients();

    public List<Client> filterClient(Predicate<Client> predicate);
}
