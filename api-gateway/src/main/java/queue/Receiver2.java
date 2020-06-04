package queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;

public class Receiver2<T> implements ServletContextListener {

    private String queueName;
    private ConnectionFactory factory;
    private Jsonb jsonb;
    private T object;

    public Receiver2(String queueName, String host, T arg) {
        this.queueName = queueName;
        this.factory = new ConnectionFactory();
        this.object = arg;
        factory.setHost(host);
        jsonb = JsonbBuilder.create();
    }

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(queueName, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                T receiverObject = jsonb.fromJson(message, this.object.getClass().getGenericSuperclass());
                System.out.println("[ --> ]    API_GATEWAY: " + this.object.getClass().getSimpleName() + " was received");
            };

            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        /* Do Shutdown stuff. */
    }

}
