package pl.lapciakbilicki.ApplicationPorts.Interfaces.Infrastructure;

import pl.lapciakbilicki.ApplicationCore.DomainModel.Reservation;

public interface ReservationDeactivationPort {

    void deactivationReservation(Reservation reservation);
}
