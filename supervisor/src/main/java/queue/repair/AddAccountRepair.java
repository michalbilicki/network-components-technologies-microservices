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
            factory.setHost("rabbitmq");
            final Connection connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            channel.queueDeclare(Consts.ADD_ACCOUNT_REPAIR, true, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            channel.basicQos(1);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

                System.out.println(" [x] Received '" + message + "'");
                try {
                    Sender<String> accountSender = new Sender<>(Consts.REMOVE_ACCOUNT_QUEUE);
                    accountSender.send(message, message);
                    Receiver accountReceiver = new Receiver(Consts.REMOVE_ACCOUNT_QUEUE);
                    if (!Boolean.parseBoolean(accountReceiver.receive(message))) {
                        Sender<String> repairSender = new Sender<>(Consts.ADD_ACCOUNT_REPAIR);
                        repairSender.asyncSend(message);
                    }
                } catch (SenderException e) {
                    System.out.println(Consts.ERROR);
                } finally {
                    System.out.println(" [x] Done");
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

//        try {
//            ConnectionFactory factory = new ConnectionFactory();
//            factory.setHost(Consts.HOST);
//            final Connection connection = factory.newConnection();
//            final Channel channel = connection.createChannel();
//
//            channel.queueDeclare(Consts.ADD_ACCOUNT_REPAIR, true, false, false, null);
//
//            channel.basicQos(1);

//            ConnectionFactory factory = new ConnectionFactory();
//            factory.setHost(Consts.HOST);
//            Connection connection = factory.newConnection();
//            Channel channel = connection.createChannel();

//            channel.queueDeclare(Consts.ADD_ACCOUNT_REPAIR, false, false, false, null);
//            channel.basicConsume(Consts.ADD_ACCOUNT_REPAIR, new DefaultConsumer(channel) {
//
//                @Override
//                public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
//                    Jsonb jsonb = JsonbBuilder.create();
//                    String message = new String(bytes, StandardCharsets.UTF_8);
//
//                    String id = jsonb.fromJson(message, String.class);
//                    System.out.println("[ RECEIVE ] CLIENT - remove client - " + id);
//
//                    try {
//                        Sender<String> accountSender = new Sender<>(Consts.REMOVE_ACCOUNT_QUEUE);
//                        accountSender.send(id, id);
//                        System.out.println("[ SEND REPAIR ] - remove account after add client failure");
//                        Receiver accountReceiver = new Receiver(Consts.REMOVE_ACCOUNT_QUEUE);
//                        if (!Boolean.parseBoolean(accountReceiver.receive(id))) {
//                            Sender<String> repairSender = new Sender<>(Consts.ADD_ACCOUNT_REPAIR);
//                            repairSender.asyncSend(id);
//                        }
//                    } catch (SenderException e) {
//                        System.out.println(Consts.ERROR);
//                        channel.basicAck(envelope.getDeliveryTag(), false);
//                    } finally {
//                        channel.basicAck(envelope.getDeliveryTag(), false);
//                    }
//
//                }
//            });

//            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
//                Jsonb jsonb = JsonbBuilder.create();
//                String id = jsonb.fromJson(message, String.class);
//
//                System.out.println("[ RECEIVE ] CLIENT - remove client - " + id);
//
//                try {
//                    Sender<String> accountSender = new Sender<>(Consts.REMOVE_ACCOUNT_QUEUE);
//                    accountSender.send(id, id);
//                    System.out.println("[ SEND REPAIR ] - remove account after add client failure");
//                    Receiver accountReceiver = new Receiver(Consts.REMOVE_ACCOUNT_QUEUE);
//                    if (!Boolean.parseBoolean(accountReceiver.receive(id))) {
//                        Sender<String> repairSender = new Sender<>(Consts.ADD_ACCOUNT_REPAIR);
//                        repairSender.asyncSend(id);
//                    }
//                } catch (SenderException e) {
//                    System.out.println(Consts.ERROR);
//                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
//                } finally {
//                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
//                }
//            };
//
//            channel.basicConsume(Consts.ADD_ACCOUNT_REPAIR, false, deliverCallback, consumerTag -> {
//            });
//        } catch (TimeoutException | IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }


//            ConnectionFactory factory = new ConnectionFactory();
//            factory.setHost(Consts.HOST);
//            final Connection connection = factory.newConnection();
//            final Channel channel = connection.createChannel();
//
//            channel.queueDeclare(Consts.ADD_ACCOUNT_REPAIR, true, false, false, null);
//
//            channel.basicQos(1);
//
//            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
//                Jsonb jsonb = JsonbBuilder.create();
//                String id = jsonb.fromJson(message, String.class);
//
//                try {
//                    Sender<String> accountSender = new Sender<>(Consts.REMOVE_ACCOUNT_QUEUE);
//                    accountSender.send(id, id);
//                    System.out.println("[ SEND REPAIR ] - remove account after add client failure");
//                    Receiver accountReceiver = new Receiver(Consts.REMOVE_ACCOUNT_QUEUE);
//                    if (!Boolean.parseBoolean(accountReceiver.receive(id))) {
//                        Sender<String> repairSender = new Sender<>(Consts.ADD_ACCOUNT_REPAIR);
//                        repairSender.asyncSend(id);
//                    }
//                } catch (SenderException e) {
//                    System.out.println(Consts.ERROR);
//                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
//                } finally {
//                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
//                }
//            };
//            channel.basicConsume(Consts.ADD_ACCOUNT_REPAIR, false, deliverCallback, consumerTag -> {
//            });
//        } catch (TimeoutException | IOException e) {
//            e.printStackTrace();
//        }


//        try {
//            ConnectionFactory factory = new ConnectionFactory();
//            factory.setHost(Consts.HOST);
//            Connection connection = factory.newConnection();
//            Channel channel = connection.createChannel();
//
//            channel.queueDeclare(Consts.REMOVE_CLIENT_QUEUE, false, false, false, null);
//            channel.basicConsume(Consts.REMOVE_CLIENT_QUEUE, new DefaultConsumer(channel) {
//
//                @Override
//                public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) {
//                    Jsonb jsonb = JsonbBuilder.create();
//                    String message = new String(bytes, StandardCharsets.UTF_8);
//
//                    String id = jsonb.fromJson(message, String.class);
//                    System.out.println("[ RECEIVE ] CLIENT - remove client - " + id);
//
//                    Sender sender = new Sender(Consts.REMOVE_CLIENT_QUEUE);
//                }
//            });
//        } catch (TimeoutException | IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//}
