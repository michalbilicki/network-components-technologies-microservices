package ApplicationServices;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Sender {

    private final static String QUEUE_NAME = "CLIENT_ACCOUNT_CREATE";
    private static ConnectionFactory factory = new ConnectionFactory();

    public static void send(String message) {
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [<-] Sent: " + message);
        } catch (TimeoutException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
