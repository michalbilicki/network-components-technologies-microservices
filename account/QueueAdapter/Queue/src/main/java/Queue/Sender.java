package Queue;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import utils.Consts;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Sender {

    private String queueName;
    private ConnectionFactory factory;

    public Sender(String queueName) {
        this.queueName = queueName + "_RESPONSE";
        this.factory = new ConnectionFactory();
        factory.setHost(Consts.HOST);
    }

    public void send(String message, String corrId) {
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(queueName, false, false, false, null);
            AMQP.BasicProperties props = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(corrId)
                    .build();

            channel.basicPublish("", queueName, props, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("[ SEND ] ACCOUNT: " + message);
        } catch (IOException | TimeoutException e) {
            System.out.println(e.getMessage());
        }
    }

}
