package ApplicationServices;

import ApplicationPorts.Infrastructure.ClientPort;
import ApplicationPorts.Infrastructure.ReservationPort;
import ApplicationPorts.User.ClientServiceUseCase;
import DomainModel.Client;
import DomainModel.Reservation;
import DomainModel.SportsFacility;
import exceptions.RepositoryConverterException;
import exceptions.RepositoryException;
import exceptions.ReservationError;
import exceptions.SportsFacilityDoesNotExists;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Named("ClientService")
public class ClientService implements ClientServiceUseCase {

    @Inject
    FacilityService facilityService;
    @Inject
    private ClientPort clientPort;
    @Inject
    private ReservationPort reservationPort;
    @Inject
    private ReservationService reservationService;

    private boolean checkDate(LocalDateTime start, LocalDateTime end) {
        if (end.compareTo(start) <= 0) {
            return false;
        }
        return start.compareTo(LocalDateTime.now()) > 0;
    }

    @Override
    public void createReservation(UUID clientId, UUID sportsFacilityId, LocalDateTime start, LocalDateTime end) throws RepositoryException, RepositoryConverterException, SportsFacilityDoesNotExists, ReservationError {
        Client client = clientPort.get(clientId);
        SportsFacility sportsFacility = facilityService.getSportsFacility(sportsFacilityId);
        if (sportsFacility == null) {
            throw new SportsFacilityDoesNotExists();
        }
        List<Reservation> clientsReservation = reservationService.getUserReservations(client);
        if (clientsReservation.stream().filter(Reservation::isActive).count() < Client.MAX_RESERVATIONS) {
            if (checkDate(start, end)) {
                if (!facilityService.canReserve(start, end, sportsFacility)) {
                    throw new ReservationError();
                }
            }
        }

        reservationPort.add(new Reservation(client.getId(), start, end, sportsFacility.getId().toString()));
    }

    @Override
    public List<Reservation> getClientReservation(UUID clientId) throws RepositoryException {
        return reservationService.getUserReservations(clientPort.get(clientId));
    }

    @Override
    public Client getClient(UUID id) throws RepositoryException {
        return clientPort.get(id);
    }

    @Override
    public List<Client> getAllClients() {
        return clientPort.getAll();
    }

    @Override
    public void updateClient(Client client) throws RepositoryException {
        clientPort.update(client);
    }

    @Override
    public void add(Client client) throws RepositoryException {
        clientPort.add(client);
    }
}
