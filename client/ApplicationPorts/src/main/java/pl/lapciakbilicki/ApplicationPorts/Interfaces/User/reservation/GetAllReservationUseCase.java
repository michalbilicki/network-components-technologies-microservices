package pl.lapciakbilicki.ApplicationPorts.Interfaces.User.reservation;

import pl.lapciakbilicki.ApplicationCore.DomainModel.Reservation;

import java.util.List;

public interface GetAllReservationUseCase {
    List<Reservation> getAll();
}
