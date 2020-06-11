package ApplicationServices;

import ApplicationPorts.Infrastructure.ClientPort;
import ApplicationPorts.Infrastructure.ReservationPort;
import ApplicationPorts.User.ReservationServiceUseCase;
import DomainModel.Client;
import DomainModel.Reservation;
import DomainModel.SportsFacility;
import exceptions.RepositoryException;
import exceptions.ReservationError;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReservationService implements ReservationServiceUseCase {

    @Inject
    private ReservationPort reservationPort;

    @Inject
    private ClientPort clientPort;

    @Override
    public List<Reservation> getUserReservations(Client client) {
        return reservationPort.getFiltered(reservation -> reservation.getClientId().equals(client.getId()));
    }

    @Override
    public List<Reservation> getSportsFacilityReservations(SportsFacility sportsFacility) {
        List<Reservation> reservations = reservationPort.getAll();
        return reservations.stream()
                .filter(reservation -> reservation.getSportsFacilityId().equals(sportsFacility.getId().toString()))
                .collect(Collectors.toList());
    }

    @Override
    public void removeReservation(UUID id) throws RepositoryException {
        reservationPort.remove(id);
    }

    @Override
    public void cancelReservation(UUID clientId, UUID reservationId) throws ReservationError, RepositoryException {
        boolean exceptionFlag = true;
        Client client = clientPort.get(clientId);
        List<Reservation> clientReservation = getUserReservations(client);
        for (Reservation reservation : clientReservation) {
            if (reservation.getId().equals(reservationId.toString())) {
                reservationPort.deactivateReservation(reservation);
                exceptionFlag = false;
            }
        }
        if (exceptionFlag)
            throw new ReservationError();
    }

    @Override
    public Reservation getReservation(UUID id) throws RepositoryException {
        return reservationPort.get(id);
    }

    @Override
    public List<Reservation> getAll() {
        return reservationPort.getAll();
    }
}
