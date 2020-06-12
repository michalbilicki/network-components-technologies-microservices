package managers;

import dto.ReservationDetailsDto;
import dto.ReservationDto;
import queue.Receiver;
import queue.Sender;
import utils.Consts;
import utils.exception.ManagerException;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ReservationManager {

    public ReservationDto getReservation(String id) throws ManagerException {
        try {
            String corrId = id;
            Jsonb jsonb = JsonbBuilder.create();
            Sender<String> sender = new Sender<>(Consts.GET_RESERVATION_QUEUE);
            sender.send(id, corrId);
            Receiver receiver = new Receiver(Consts.GET_RESERVATION_QUEUE);
            String json = receiver.receive(corrId);
            ReservationDto reservationDto = jsonb.fromJson(json, ReservationDto.class);
            if (reservationDto != null) {
                return reservationDto;
            } else {
                throw new ManagerException();
            }
        } catch (JsonbException e) {
            throw new ManagerException();
        }
    }

    public List<ReservationDto> getAllReservations() throws ManagerException {
        try {
            String corrId = UUID.randomUUID().toString();
            Jsonb jsonb = JsonbBuilder.create();

            Sender<String> sender = new Sender<>(Consts.GET_ALL_RESERVATIONS_QUEUE);
            sender.send(corrId, corrId);

            Receiver receiver = new Receiver(Consts.GET_ALL_RESERVATIONS_QUEUE);
            String json = receiver.receive(corrId);
            return Arrays.asList(jsonb.fromJson(json, ReservationDto[].class));
        } catch (JsonbException e) {
            throw new ManagerException();
        }
    }

    public List<ReservationDto> getClientReservations(String id) throws ManagerException {
        try {
            String corrId = id;
            Jsonb jsonb = JsonbBuilder.create();

            Sender<String> sender = new Sender<>(Consts.GET_CLIENT_RESERVATIONS_QUEUE);
            sender.send(corrId, corrId);

            Receiver receiver = new Receiver(Consts.GET_CLIENT_RESERVATIONS_QUEUE);
            String json = receiver.receive(corrId);
            return Arrays.asList(jsonb.fromJson(json, ReservationDto[].class));
        } catch (JsonbException e) {
            throw new ManagerException();
        }
    }

    public void removeReservation(String id) throws ManagerException {
        Sender<String> sender = new Sender<>(Consts.REMOVE_RESERVATION_QUEUE);
        sender.send(id, id);
        Receiver receiver = new Receiver(Consts.REMOVE_RESERVATION_QUEUE);
        if (!Boolean.parseBoolean(receiver.receive(id))) {
            throw new ManagerException();
        }
    }

    public void cancelReservation(String clientId, String reservationId) throws ManagerException {
        String corrId = reservationId;
        Sender<String[]> sender = new Sender<>(Consts.CANCEL_RESERVATION_QUEUE);
        String[] message = {clientId, reservationId};
        sender.send(message, corrId);
        Receiver receiver = new Receiver(Consts.CANCEL_RESERVATION_QUEUE);
        if (!Boolean.parseBoolean(receiver.receive(corrId))) {
            throw new ManagerException();
        }
    }

    public void makeReservation(ReservationDetailsDto reservationDetailsDto) throws ManagerException {
        String corrId = reservationDetailsDto.getClientId();
        Sender<ReservationDetailsDto> sender = new Sender<>(Consts.MAKE_RESERVATION_QUEUE);
        sender.send(reservationDetailsDto, corrId);
        Receiver receiver = new Receiver(Consts.MAKE_RESERVATION_QUEUE);
        if (!Boolean.parseBoolean(receiver.receive(corrId))) {
            throw new ManagerException();
        }
    }
}
