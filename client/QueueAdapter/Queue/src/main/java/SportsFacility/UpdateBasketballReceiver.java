package SportsFacility;

import ApplicationPorts.User.SportFacilityServiceUseCase;
import DomainModel.SportsFacility;
import Model.ViewFacilityConverter;
import Model.dto.BasketballFacilityDto;
import Queue.Sender;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
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
public class UpdateBasketballReceiver implements Serializable {

    @Inject
    private SportFacilityServiceUseCase sportFacilityServiceUseCase;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

//        try {
//            ConnectionFactory factory = new ConnectionFactory();
//            factory.setHost(Consts.HOST);
//            Connection connection = factory.newConnection();
//            Channel channel = connection.createChannel();
//
//            channel.queueDeclare(Consts.UPDATE_BASKETBALL_FACILITY_QUEUE, false, false, false, null);
//            channel.basicConsume(Consts.UPDATE_BASKETBALL_FACILITY_QUEUE, new DefaultConsumer(channel) {
//
//                @Override
//                public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) {
//                    Jsonb jsonb = JsonbBuilder.create();
//                    String message = new String(bytes, StandardCharsets.UTF_8);
//
//                    BasketballFacilityDto basketballFacilityDto = jsonb.fromJson(message, BasketballFacilityDto.class);
//                    System.out.println("[ RECEIVE ] CLIENT - update basketball facility - " + basketballFacilityDto.toString());
//
//                    Sender sender = new Sender(Consts.UPDATE_BASKETBALL_FACILITY_QUEUE);
//
//                    try {
//                        SportsFacility sportsFacility = ViewFacilityConverter.convertFrom(basketballFacilityDto);
//                        sportFacilityServiceUseCase.updateSportsFacility(sportsFacility);
//                        sender.send(Boolean.toString(true), basketballFacilityDto.getId());
//                    } catch (RepositoryException | ViewConverterException | RepositoryConverterException e) {
//                        sender.send(Boolean.toString(false), basketballFacilityDto.getId());
//                    }
//
//                }
//            });
//        } catch (TimeoutException | IOException e) {
//            System.out.println(e.getMessage());
//        }

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(Consts.HOST);
            final Connection connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            channel.queueDeclare(Consts.UPDATE_BASKETBALL_FACILITY_QUEUE, true, false, false, null);

            channel.basicQos(1);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                Jsonb jsonb = JsonbBuilder.create();
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

                BasketballFacilityDto basketballFacilityDto = jsonb.fromJson(message, BasketballFacilityDto.class);
                System.out.println("[ RECEIVE ] CLIENT - update basketball facility - " + basketballFacilityDto.toString());

                try {
                    SportsFacility sportsFacility = ViewFacilityConverter.convertFrom(basketballFacilityDto);
                    sportFacilityServiceUseCase.updateSportsFacility(sportsFacility);
                    System.out.println("[ SUCCESS ] CLIENT - success");
                } catch (RepositoryException | ViewConverterException | RepositoryConverterException e) {
                    System.out.println("[ ERROR ] CLIENT - failed to update basketball facility");
                } finally {
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            };
            channel.basicConsume(Consts.UPDATE_BASKETBALL_FACILITY_QUEUE, false, deliverCallback, consumerTag -> {
            });
        } catch (TimeoutException | IOException e) {
            System.out.println("[ ERROR ] CLIENT - internal problem");
        }
    }
}