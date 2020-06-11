package pl.lapciakbilicki.ApplicationPorts.Interfaces.User.reservation;

import pl.lapciakbilicki.ApplicationCore.DomainModel.Client;
import pl.lapciakbilicki.ApplicationCore.DomainModel.SportsFacility;
import pl.lapciakbilicki.RepositoriesAdapter.Repository.repository.RepositoryException;

import java.time.LocalDateTime;
import java.util.UUID;

public interface MakeReservationUseCase {

    boolean createReservation(UUID clientId, UUID sportsFacilityId, LocalDateTime start, LocalDateTime end);

}
