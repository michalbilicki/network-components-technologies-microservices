package ApplicationPorts.User;

import DomainModel.Client;
import DomainModel.Reservation;
import DomainModel.SportsFacility;
import exceptions.RepositoryException;
import exceptions.ReservationError;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ReservationServiceUseCase {

    void cancelReservation(UUID clientId, UUID reservationId) throws ReservationError, ReservationError, RepositoryException;

    List<Reservation> getAll();

    List<Reservation> getUserReservations(Client client);

    List<Reservation> getSportsFacilityReservations(SportsFacility sportsFacility);

    Reservation getReservation(UUID id) throws RepositoryException;

    void removeReservation(UUID reservationId) throws RepositoryException;
}
