package pl.lapciakbilicki.ApplicationPorts.Interfaces.User.client;

import pl.lapciakbilicki.ApplicationCore.DomainModel.Client;

import java.util.List;
import java.util.UUID;

public interface GetClientUseCase {
    Client getClient(UUID id);

    List<Client> getAllClients();
}
