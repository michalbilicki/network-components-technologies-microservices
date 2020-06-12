package queue;

import com.rabbitmq.client.*;
import utils.Consts;
import utils.exception.SenderException;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Sender<T> {

    private String queueName;
    private ConnectionFactory factory;
    private Jsonb jsonb;

    public Sender(String queueName) {
        this.queueName = queueName;
        this.factory = new ConnectionFactory();
        factory.setHost(Consts.HOST);
        jsonb = JsonbBuilder.create();
    }

    public void send(T arg, String corrId) {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(queueName, false, false, false, null);

            AMQP.BasicProperties props = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(corrId)
                    .replyTo(queueName)
                    .build();

            channel.basicPublish("", queueName, props, jsonb.toJson(arg).getBytes(StandardCharsets.UTF_8));

            System.out.println("[ SEND ] API_GATEWAY: " + arg);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void asyncSend(T arg) throws SenderException {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(queueName, true, false, false, null);
            String message = jsonb.toJson(arg);

            channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("[ REPAIR SEND ]    API_GATEWAY: " + message);
        } catch (TimeoutException | IOException e) {
            throw new SenderException();
        }
    }

}
