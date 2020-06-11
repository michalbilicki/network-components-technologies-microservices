package SportsFacility;

import ApplicationPorts.User.SportFacilityServiceUseCase;
import DomainModel.SportsFacility;
import Model.ViewFacilityConverter;
import Model.dto.FootballFacilityDto;
import Queue.Sender;
import com.rabbitmq.client.*;
import exceptions.RepositoryConverterException;
import exceptions.RepositoryException;
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
import java.util.concurrent.TimeoutException;

@Singleton
@Startup
public class UpdateFootballReceiver implements Serializable {

    @Inject
    private SportFacilityServiceUseCase sportFacilityServiceUseCase;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(Consts.HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(Consts.UPDATE_FOOTBALL_FACILITY_QUEUE, false, false, false, null);
            channel.basicConsume(Consts.UPDATE_FOOTBALL_FACILITY_QUEUE, new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) {
                    Jsonb jsonb = JsonbBuilder.create();
                    String message = new String(bytes, StandardCharsets.UTF_8);

                    FootballFacilityDto footballFacilityDto = jsonb.fromJson(message, FootballFacilityDto.class);
                    System.out.println("[ RECEIVE ] CLIENT - update football facility - " + footballFacilityDto.toString());

                    Sender sender = new Sender(Consts.UPDATE_FOOTBALL_FACILITY_QUEUE);

                    try {
                        SportsFacility sportsFacility = ViewFacilityConverter.convertFrom(footballFacilityDto);
                        sportFacilityServiceUseCase.updateSportsFacility(sportsFacility);
                        sender.send(Boolean.toString(true), footballFacilityDto.getId());
                    } catch (RepositoryException | ViewConverterException | RepositoryConverterException e) {
                        sender.send(Boolean.toString(false), footballFacilityDto.getId());
                    }

                }
            });
        } catch (TimeoutException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
