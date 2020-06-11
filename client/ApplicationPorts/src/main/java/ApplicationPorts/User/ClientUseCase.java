package ApplicationPorts.User;

import DomainModel.Client;
import DomainModel.Reservation;
import exceptions.RepositoryConverterException;
import exceptions.RepositoryException;
import exceptions.ReservationError;
import exceptions.SportsFacilityDoesNotExists;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ClientUseCase {

    void createReservation(UUID clientId, UUID sportsFacilityId, LocalDateTime start, LocalDateTime end) throws RepositoryException, RepositoryConverterException, SportsFacilityDoesNotExists, ReservationError;

    List<Reservation> getClientReservation(UUID clientId) throws RepositoryException;

    void add(Client client) throws RepositoryException;

    Client getClient(UUID id) throws RepositoryException;

    List<Client> getAllClients();

    void updateClient(Client client) throws RepositoryException;
}
