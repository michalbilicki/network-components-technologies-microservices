package queue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.*;
import utils.Consts;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

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
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(queueName, false, false, false, null);

            AMQP.BasicProperties props = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(corrId)
                    .replyTo(queueName)
                    .build();

            channel.basicPublish("", queueName, props, jsonb.toJson(arg).getBytes(StandardCharsets.UTF_8));

            System.out.println("[ SEND ]    API_GATEWAY: " + arg);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

}
