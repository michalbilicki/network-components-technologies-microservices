package SportsFacility;

import ApplicationPorts.User.SportFacilityServiceUseCase;
import DomainModel.SportsFacility;
import Model.ViewFacilityConverter;
import Model.dto.SportsFacilityDto;
import Queue.Sender;
import com.rabbitmq.client.*;
import exceptions.RepositoryConverterException;
import exceptions.ViewConverterException;
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
    private SportFacilityServiceUseCase sportFacilityServiceUseCase;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(Consts.HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(Consts.GET_ALL_FACILITY_QUEUE, false, false, false, null);
            channel.basicConsume(Consts.GET_ALL_FACILITY_QUEUE, new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) {
                    Jsonb jsonb = JsonbBuilder.create();
                    String message = new String(bytes, StandardCharsets.UTF_8);

                    String id = jsonb.fromJson(message, String.class);
                    System.out.println("[ RECEIVE ] CLIENT - get all facilities");

                    Sender sender = new Sender(Consts.GET_ALL_FACILITY_QUEUE);

                    try {
                        List<SportsFacility> facilities = sportFacilityServiceUseCase.getAllSportsFacilities();

                        List<SportsFacilityDto> list = new ArrayList<>();
                        for (SportsFacility item : facilities) {
                            list.add(ViewFacilityConverter.convertToDto(item));
                        }

                        sender.send(jsonb.toJson(list), id);
                    } catch (RepositoryConverterException | ViewConverterException e) {
                        sender.send(Boolean.toString(false), id);
                    }

                }
            });
        } catch (TimeoutException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
