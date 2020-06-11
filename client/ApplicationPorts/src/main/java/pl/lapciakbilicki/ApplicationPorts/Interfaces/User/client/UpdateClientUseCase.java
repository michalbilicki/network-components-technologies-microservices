package pl.lapciakbilicki.ApplicationPorts.Interfaces.User.client;

import pl.lapciakbilicki.ApplicationCore.DomainModel.Client;

public interface UpdateClientUseCase {

    boolean updateClient(Client client);
}
