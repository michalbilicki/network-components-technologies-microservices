package pl.lapciakbilicki.ApplicationPorts.Interfaces.User.reservation;

import pl.lapciakbilicki.RepositoriesAdapter.Repository.repository.RepositoryException;

import java.util.UUID;

public interface RemoveReservationUseCase {

    boolean removeReservation(UUID reservationId);
}
