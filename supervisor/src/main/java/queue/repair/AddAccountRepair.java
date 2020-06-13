package queue.repair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import queue.Receiver;
import queue.Sender;
import utils.Consts;
import utils.exception.SenderException;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Singleton
@Startup
public class AddAccountRepair implements Serializable {

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(Consts.HOST);
            final Connection connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            channel.queueDeclare(Consts.ADD_ACCOUNT_REPAIR, true, false, false, null);

            channel.basicQos(1);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                Jsonb jsonb = JsonbBuilder.create();
                String id = jsonb.fromJson(message, String.class);
                System.out.println("[ SUPERVISOR ] - remove account: " + message);
                try {
                    Sender<String> accountSender = new Sender<>(Consts.REMOVE_ACCOUNT_QUEUE);
                    accountSender.send(id, id);
                    Receiver accountReceiver = new Receiver(Consts.REMOVE_ACCOUNT_QUEUE);
                    if (!Boolean.parseBoolean(accountReceiver.receive(id))) {
                        Sender<String> repairSender = new Sender<>(Consts.ADD_ACCOUNT_REPAIR);
                        repairSender.asyncSend(id);
                    }
                } catch (SenderException e) {
                    System.out.println(Consts.ERROR);
                } finally {
                    System.out.println("[ SUPERVISOR ] - Done");
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            };
            channel.basicConsume(Consts.ADD_ACCOUNT_REPAIR, false, deliverCallback, consumerTag -> {
            });
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
