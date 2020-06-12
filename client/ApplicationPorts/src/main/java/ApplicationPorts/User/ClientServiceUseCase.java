package ApplicationPorts.User;

import DomainModel.Client;
import DomainModel.Reservation;
import exceptions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ClientServiceUseCase {

    void createReservation(UUID clientId, UUID sportsFacilityId, LocalDateTime start, LocalDateTime end) throws RepositoryException, RepositoryConverterException, SportsFacilityDoesNotExists, ReservationError, ClientDoesNotExistException;

    List<Reservation> getClientReservation(UUID clientId) throws RepositoryException;

    void add(Client client) throws RepositoryException;

    Client getClient(UUID id) throws RepositoryException;

    List<Client> getAllClients();

    void blockClient(UUID id) throws RepositoryException;

    void unblockClient(UUID id) throws RepositoryException;

    void removeClient(UUID id) throws RepositoryException;

    void updateClient(Client client) throws RepositoryException;
}
