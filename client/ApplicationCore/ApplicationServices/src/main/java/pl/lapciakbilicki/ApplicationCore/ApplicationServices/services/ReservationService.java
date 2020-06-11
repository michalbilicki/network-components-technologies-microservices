package pl.lapciakbilicki.ApplicationCore.ApplicationServices.services;

import pl.lapciakbilicki.ApplicationCore.DomainModel.Client;
import pl.lapciakbilicki.ApplicationCore.DomainModel.Reservation;
import pl.lapciakbilicki.ApplicationCore.DomainModel.SportsFacility;
import pl.lapciakbilicki.ApplicationCore.DomainModel.exceptions.ReservationError;
import pl.lapciakbilicki.ApplicationPorts.Interfaces.Infrastructure.*;
import pl.lapciakbilicki.ApplicationPorts.Interfaces.User.reservation.*;
import pl.lapciakbilicki.RepositoriesAdapter.Repository.repository.RepositoryException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@Named("ReservationService")
public class ReservationService implements
        CancelReservationUseCase,
        RemoveReservationUseCase,
        GetReservationUseCase,
        GetAllReservationUseCase {

    @Inject
    private @Named("RepositoryReservationAdapter")
    RemovePort<Reservation> removeReservationPort;

    @Inject
    private @Named("RepositoryReservationAdapter")
    GetPort<Reservation> reservationGetPort;

    @Inject
    private @Named("RepositoryReservationAdapter")
    FilterPort<Reservation> reservationFilterPort;

    @Inject
    private ReservationDeactivationPort reservationDeactivationPort;

    @Inject
    private @Named("RepositoryClientAdapter")
    GetPort<Client> clientGetPort;

    public List<Reservation> getUserReservations(Client client) {
        return reservationFilterPort.getFiltered(reservation -> reservation.getClientId().equals(client.getId()));
    }

    public List<Reservation> getSportsFacilityReservations(SportsFacility sportsFacility) {
        List<Reservation> reservations = reservationGetPort.getAll();
        return reservations.stream()
                .filter(reservation -> reservation.getSportsFacilityId().equals(sportsFacility.getId().toString()))
                .collect(Collectors.toList());
    }

    public boolean removeReservation(UUID id) {
        try {
            removeReservationPort.remove(id);
            return true;
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void cancelReservation(UUID clientId, UUID reservationId) throws ReservationError {
        boolean exceptionFlag = true;
        Client client = clientGetPort.get(clientId);
        List<Reservation> clientReservation = getUserReservations(client);
        for (Reservation reservation : clientReservation) {
            if (reservation.getId().equals(reservationId.toString())) {
                reservationDeactivationPort.deactivationReservation(reservation);
                exceptionFlag = false;
            }
        }
        if (exceptionFlag)
            throw new ReservationError("User cannot cancel this reservation");
    }

    @Override
    public Reservation getReservation(UUID id) {
        return reservationGetPort.get(id);
    }

    @Override
    public List<Reservation> getAll() {
        return reservationGetPort.getAll();
    }
}
