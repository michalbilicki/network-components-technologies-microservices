package ApplicationPorts.Infrastructure;

import DomainModel.Reservation;
import exceptions.RepositoryException;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface ReservationServicePort {

    void add(Reservation arg) throws RepositoryException;

    Reservation get(UUID id) throws RepositoryException;

    List<Reservation> getAll();

    void remove(Reservation arg) throws RepositoryException;

    void remove(UUID id) throws RepositoryException;

    void deactivateReservation(Reservation reservation) throws RepositoryException;

    List<Reservation> getFiltered(Predicate<Reservation> predicate);
}
