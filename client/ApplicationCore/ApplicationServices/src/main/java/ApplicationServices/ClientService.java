package ApplicationServices;

import ApplicationPorts.Infrastructure.ClientServicePort;
import ApplicationPorts.Infrastructure.ReservationServicePort;
import ApplicationPorts.User.ClientUseCase;
import DomainModel.Client;
import DomainModel.Reservation;
import DomainModel.SportsFacility;
import exceptions.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Named("ClientService")
public class ClientService implements ClientUseCase {

    @Inject
    private ClientServicePort clientServicePort;

    @Inject
    private ReservationServicePort reservationServicePort;

    @Inject
    private ReservationService reservationService;

    @Inject
    FacilityService facilityService;

    private boolean checkDate(LocalDateTime start, LocalDateTime end) {
        if (end.compareTo(start) <= 0) {
            return false;
        }
        return start.compareTo(LocalDateTime.now()) > 0;
    }

    @Override
    public void createReservation(UUID clientId, UUID sportsFacilityId, LocalDateTime start, LocalDateTime end) throws RepositoryException, RepositoryConverterException, SportsFacilityDoesNotExists, ReservationError {
        Client client = clientServicePort.get(clientId);
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

        reservationServicePort.add(new Reservation(client.getId(), start, end, sportsFacility.getId().toString()));
    }

    @Override
    public List<Reservation> getClientReservation(UUID clientId) throws RepositoryException {
        return reservationService.getUserReservations(clientServicePort.get(clientId));
    }

    @Override
    public Client getClient(UUID id) throws RepositoryException {
        return clientServicePort.get(id);
    }

    @Override
    public List<Client> getAllClients() {
        return clientServicePort.getAll();
    }

    @Override
    public void updateClient(Client client) throws RepositoryException {
        clientServicePort.update(client);
    }

    @Override
    public void add(Client client) throws RepositoryException {
        clientServicePort.add(client);
    }
}
