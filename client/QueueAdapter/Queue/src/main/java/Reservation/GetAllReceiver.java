package Reservation;

import ApplicationPorts.User.ReservationServiceUseCase;
import DomainModel.Reservation;
import Model.ViewReservationRestConverter;
import Model.dto.ReservationDto;
import Queue.Sender;
import com.rabbitmq.client.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Singleton
@Startup
public class GetAllReceiver implements Serializable {

    @Inject
    private ReservationServiceUseCase reservationServiceUseCase;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(Consts.HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(Consts.GET_ALL_RESERVATIONS_QUEUE, false, false, false, null);
            channel.basicConsume(Consts.GET_ALL_RESERVATIONS_QUEUE, new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) {
                    Jsonb jsonb = JsonbBuilder.create();
                    String message = new String(bytes, StandardCharsets.UTF_8);

                    String id = jsonb.fromJson(message, String.class);
                    System.out.println("[ RECEIVE ] CLIENT - get all reservations");

                    Sender sender = new Sender(Consts.GET_ALL_RESERVATIONS_QUEUE);

                    List<Reservation> reservations = reservationServiceUseCase.getAll();
                    List<ReservationDto> list = new ArrayList<>();

                    for (Reservation item : reservations) {
                        list.add(ViewReservationRestConverter.convertTo(item));
                    }
                    sender.send(jsonb.toJson(list), id);
                }
            });
        } catch (TimeoutException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
