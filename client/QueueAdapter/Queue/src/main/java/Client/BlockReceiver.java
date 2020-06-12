package Client;

import ApplicationPorts.User.ClientServiceUseCase;
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
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Singleton
@Startup
public class BlockReceiver implements Serializable {

    @Inject
    private ClientServiceUseCase clientServiceUseCase;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(Consts.HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(Consts.BLOCK_CLIENT_QUEUE, false, false, false, null);
            channel.basicConsume(Consts.BLOCK_CLIENT_QUEUE, new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) {
                    Jsonb jsonb = JsonbBuilder.create();
                    String message = new String(bytes, StandardCharsets.UTF_8);

                    String id = jsonb.fromJson(message, String.class);
                    System.out.println("[ RECEIVE ] CLIENT - block client - " + id);

                    Sender sender = new Sender(Consts.BLOCK_CLIENT_QUEUE);
                    try {
                        clientServiceUseCase.blockClient(UUID.fromString(id));
                        sender.send(Boolean.toString(true), id);
                    } catch (RepositoryException e) {
                        sender.send(Boolean.toString(false), id);
                    }
                }
            });
        } catch (TimeoutException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
