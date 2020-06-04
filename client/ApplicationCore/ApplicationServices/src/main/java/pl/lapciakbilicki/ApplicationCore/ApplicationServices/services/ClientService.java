package pl.lapciakbilicki.ApplicationCore.ApplicationServices.services;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import pl.lapciakbilicki.ApplicationCore.ApplicationServices.queue.ClientResponse;
import pl.lapciakbilicki.ApplicationCore.ApplicationServices.queue.Sender;
import pl.lapciakbilicki.ApplicationCore.DomainModel.Client;
import pl.lapciakbilicki.ApplicationCore.DomainModel.Reservation;
import pl.lapciakbilicki.ApplicationCore.DomainModel.SportsFacility;
import pl.lapciakbilicki.ApplicationPorts.Interfaces.Infrastructure.*;
import pl.lapciakbilicki.ApplicationPorts.Interfaces.User.client.AddClientUseCase;
import pl.lapciakbilicki.ApplicationPorts.Interfaces.User.client.GetClientUseCase;
import pl.lapciakbilicki.ApplicationPorts.Interfaces.User.client.UpdateClientUseCase;
import pl.lapciakbilicki.ApplicationPorts.Interfaces.User.reservation.GetClientReservationUseCase;
import pl.lapciakbilicki.ApplicationPorts.Interfaces.User.reservation.MakeReservationUseCase;
import pl.lapciakbilicki.RepositoriesAdapter.Repository.repository.RepositoryException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@ApplicationScoped
@Named("ClientService")
public class ClientService implements MakeReservationUseCase, GetClientReservationUseCase, GetClientUseCase, UpdateClientUseCase, AddClientUseCase {

    @Inject
    private @Named("RepositoryClientAdapter")
    GetPort<Client> clientGetPort;

    @Inject
    private @Named("RepositoryClientAdapter")
    AddPort<Client> clientAddPort;

    @Inject
    private @Named("RepositoryClientAdapter")
    UpdatePort<Client> clientUpdatePort;

    @Inject
    private @Named("RepositoryReservationAdapter")
    AddPort<Reservation> reservationAddPort;

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
    public boolean createReservation(UUID clientId, UUID sportsFacilityId, LocalDateTime start, LocalDateTime end) {
        Client client = clientGetPort.get(clientId);
        SportsFacility sportsFacility = facilityService.getSportsFacility(sportsFacilityId);
        if (sportsFacility == null) {
            return false;
        }
        List<Reservation> clientsReservation = reservationService.getUserReservations(client);
        if (clientsReservation.stream().filter(Reservation::isActive).count() < Client.MAX_RESERVATIONS) {
            if (checkDate(start, end)) {
                if (!facilityService.canReserve(start, end, sportsFacility)) {
                    return false;
                }
            }
        }

        try {
            reservationAddPort.add(new Reservation(client.getId(), start, end, sportsFacility.getId().toString()));
            return true;
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Reservation> getClientReservation(UUID clientId) {
        return reservationService.getUserReservations(clientGetPort.get(clientId));
    }

    @Override
    public Client getClient(UUID id) {
        return clientGetPort.get(id);
    }

    @Override
    public List<Client> getAllClients() {
        return clientGetPort.getAll();
    }

    @Override
    public boolean updateClient(Client client) {
        try {
            clientUpdatePort.update(client);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }

    @Override
    public void add(Client client) {
        Jsonb jsonb = JsonbBuilder.create();
        ClientResponse clientResponse = new ClientResponse(client.getId(), true);
        try {
            clientAddPort.add(client);
            Sender.send(jsonb.toJson(clientResponse));
        } catch (RepositoryException e) {
            clientResponse.setStatus(false);
            Sender.send(jsonb.toJson(clientResponse));
        }
    }
}
