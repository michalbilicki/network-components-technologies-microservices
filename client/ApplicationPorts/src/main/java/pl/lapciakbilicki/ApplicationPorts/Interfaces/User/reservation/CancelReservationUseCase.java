package pl.lapciakbilicki.ApplicationPorts.Interfaces.User.reservation;

import pl.lapciakbilicki.ApplicationCore.DomainModel.Client;
import pl.lapciakbilicki.ApplicationCore.DomainModel.exceptions.ReservationError;

import java.util.UUID;

public interface CancelReservationUseCase {

    void cancelReservation(UUID clientId, UUID reservationId) throws ReservationError;
}
