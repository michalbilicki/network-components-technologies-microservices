package Client;

import ApplicationPorts.User.ClientServiceUseCase;
import DomainModel.Client;
import Model.ViewClientConverter;
import Model.dto.ClientDto;
import Queue.Sender;
import com.rabbitmq.client.*;
import exceptions.RepositoryException;
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
public class AddReceiver implements Serializable {

    @Inject
    private ClientServiceUseCase clientServiceUseCase;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(Consts.HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(Consts.ADD_CLIENT_QUEUE, false, false, false, null);
            channel.basicConsume(Consts.ADD_CLIENT_QUEUE, new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) {
                    Jsonb jsonb = JsonbBuilder.create();
                    String message = new String(bytes, StandardCharsets.UTF_8);

                    ClientDto clientDto = jsonb.fromJson(message, ClientDto.class);
                    System.out.println("[ RECEIVE ] CLIENT - add client - " + clientDto.toString());

                    Sender sender = new Sender(Consts.ADD_CLIENT_QUEUE);

                    try {
                        Client client = ViewClientConverter.convertFrom(clientDto);
                        clientServiceUseCase.add(client);
                        sender.send(Boolean.toString(true), clientDto.getId());
                    } catch (RepositoryException e) {
                        sender.send(Boolean.toString(false), clientDto.getId());
                    }
                }
            });
        } catch (TimeoutException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
