package pl.lapciakbilicki.ApplicationPorts.Interfaces.User.reservation;

import pl.lapciakbilicki.ApplicationCore.DomainModel.Reservation;

import java.util.UUID;

public interface GetReservationUseCase {
    Reservation getReservation(UUID id);
}
