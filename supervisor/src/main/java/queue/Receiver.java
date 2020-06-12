package queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import utils.Consts;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class Receiver {

    private String queueName;
    private ConnectionFactory factory;

    public Receiver(String queueName) {
        this.queueName = queueName;
        this.factory = new ConnectionFactory();
        factory.setHost(Consts.HOST);
    }

    public String receive(String corrId) {
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
            channel.queueDeclare(queueName + "_RESPONSE", false, false, false, null);
            String ctag = channel.basicConsume(queueName + "_RESPONSE", true, (consumerTag, delivery) -> {
                if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                    response.offer(new String(delivery.getBody(), StandardCharsets.UTF_8));
                }
            }, consumerTag -> {
            });

            String result = response.take();

            channel.basicCancel(ctag);
            System.out.println("[ RECEIVE ] API_GATEWAY: " + result);
            return result;

        } catch (InterruptedException | IOException | TimeoutException e) {
            return Consts.ERROR;
        }
    }
}
