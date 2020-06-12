package Client;

import ApplicationPorts.User.ClientServiceUseCase;
import DomainModel.Client;
import Model.ViewClientConverter;
import Model.dto.ClientDto;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Singleton
@Startup
public class GetAllReceiver {

    @Inject
    private ClientServiceUseCase clientServiceUseCase;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(Consts.HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(Consts.GET_ALL_CLIENT_QUEUE, false, false, false, null);
            channel.basicConsume(Consts.GET_ALL_CLIENT_QUEUE, new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) {
                    Jsonb jsonb = JsonbBuilder.create();
                    String message = new String(bytes, StandardCharsets.UTF_8);

                    String id = jsonb.fromJson(message, String.class);
                    System.out.println("[ RECEIVE ] CLIENT - get all clients");

                    Sender sender = new Sender(Consts.GET_ALL_CLIENT_QUEUE);
                    List<Client> clients = clientServiceUseCase.getAllClients();

                    List<ClientDto> list = new ArrayList<>();
                    for (Client item : clients) {
                        list.add(ViewClientConverter.convertTo(item));
                    }
                    sender.send(jsonb.toJson(list), id);
                }
            });
        } catch (TimeoutException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
