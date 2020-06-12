package Reservation;

import ApplicationPorts.User.ReservationServiceUseCase;
import Queue.Sender;
import com.rabbitmq.client.*;
import exceptions.RepositoryException;
import exceptions.ReservationError;
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
public class CancelReceiver implements Serializable {

    @Inject
    private ReservationServiceUseCase reservationServiceUseCase;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(Consts.HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(Consts.CANCEL_RESERVATION_QUEUE, false, false, false, null);
            channel.basicConsume(Consts.CANCEL_RESERVATION_QUEUE, new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) {
                    Jsonb jsonb = JsonbBuilder.create();
                    String message = new String(bytes, StandardCharsets.UTF_8);

                    String[] id = jsonb.fromJson(message, String[].class);
                    System.out.println("[ RECEIVE ] CLIENT - cancel reservation - client:" + id[0] + " | reservation: " + id[1]);

                    Sender sender = new Sender(Consts.CANCEL_RESERVATION_QUEUE);

                    try {
                        reservationServiceUseCase.cancelReservation(UUID.fromString(id[0]), UUID.fromString(id[1]));
                        sender.send(Boolean.toString(true), id[1]);
                    } catch (ReservationError | RepositoryException reservationError) {
                        sender.send(Boolean.toString(false), id[1]);
                    }
                }
            });
        } catch (TimeoutException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
