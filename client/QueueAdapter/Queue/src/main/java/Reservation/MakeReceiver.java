package Reservation;

import ApplicationPorts.User.ClientServiceUseCase;
import Model.dto.ReservationDetailsDto;
import Queue.Sender;
import com.rabbitmq.client.*;
import exceptions.*;
import utils.Consts;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Singleton
@Startup
public class MakeReceiver implements Serializable {

    @Inject
    private ClientServiceUseCase clientServiceUseCase;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(Consts.HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(Consts.MAKE_RESERVATION_QUEUE, false, false, false, null);
            channel.basicConsume(Consts.MAKE_RESERVATION_QUEUE, new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) {
                    Jsonb jsonb = JsonbBuilder.create();
                    String message = new String(bytes, StandardCharsets.UTF_8);

                    ReservationDetailsDto reservationDetailsDto = jsonb.fromJson(message, ReservationDetailsDto.class);
                    System.out.println("[ RECEIVE ] CLIENT - make reservation - " + reservationDetailsDto);

                    Sender sender = new Sender(Consts.MAKE_RESERVATION_QUEUE);

                    try {
                        clientServiceUseCase.createReservation(
                                UUID.fromString(reservationDetailsDto.getClientId()),
                                UUID.fromString(reservationDetailsDto.getSportsFacilityId()),
                                reservationDetailsDto.getStartDate(),
                                reservationDetailsDto.getEndDate()
                        );
                        sender.send(Boolean.toString(true), reservationDetailsDto.getClientId());
                    } catch (ReservationError | RepositoryConverterException | SportsFacilityDoesNotExists | RepositoryException | ClientDoesNotExistException e) {
                        e.printStackTrace();
                        sender.send(Boolean.toString(false), reservationDetailsDto.getClientId());
                    }
                }
            });
        } catch (TimeoutException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
