package pl.lapciakbilicki.ApplicationPorts.Interfaces.User.reservation;

import pl.lapciakbilicki.ApplicationCore.DomainModel.Reservation;

import java.util.List;
import java.util.UUID;

public interface GetClientReservationUseCase {
    List<Reservation> getClientReservation(UUID clientId);
}
