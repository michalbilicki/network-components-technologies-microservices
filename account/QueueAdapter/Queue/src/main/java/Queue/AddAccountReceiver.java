package Queue;

import ApplicationPorts.AccountServiceUseCase;
import DomainModel.Account;
import Model.AccountDto;
import Model.ViewAccountConverter;
import com.rabbitmq.client.*;
import exceptions.RepositoryConverterException;
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
import java.util.concurrent.TimeoutException;

@Singleton
@Startup
public class AddAccountReceiver implements Serializable {

    @Inject
    private AccountServiceUseCase accountServiceUseCase;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(Consts.HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(Consts.ADD_ACCOUNT_QUEUE, false, false, false, null);
            channel.basicConsume(Consts.ADD_ACCOUNT_QUEUE, new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) {
                    Jsonb jsonb = JsonbBuilder.create();
                    String message = new String(bytes, StandardCharsets.UTF_8);

                    AccountDto accountDto = jsonb.fromJson(message, AccountDto.class);
                    System.out.println("[ RECEIVE ] ACCOUNT - add account - " + accountDto.toString());

                    ViewAccountConverter viewAccountConverter = new ViewAccountConverter();
                    Account account = viewAccountConverter.convertFrom(accountDto);

                    Sender sender = new Sender(Consts.ADD_ACCOUNT_QUEUE);
                    try {
                        accountServiceUseCase.addAccount(account);
                        sender.send(Boolean.toString(true), accountDto.getId());
                    } catch (RepositoryConverterException | RepositoryException e) {
                        sender.send(Boolean.toString(false), accountDto.getId());
                    }

                }
            });
        } catch (TimeoutException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}


//                    try {
//
//                        channel.queueDeclare("ADD_ACCOUNT_QUEUE_RESPONSE", false, false, false, null);
//                        AMQP.BasicProperties props = new AMQP.BasicProperties
//                                .Builder()
//                                .correlationId(accountDto.getCorrId())
//                                .build();
//
//                        System.out.println("--- send debug ---");
//                        channel.basicPublish("", "ADD_ACCOUNT_QUEUE_RESPONSE", props, Boolean.toString(response).getBytes(StandardCharsets.UTF_8));
//                        System.out.println("[ SEND ]     ACCOUNT: " + response);
//                    } catch (IOException e) {
//                        System.out.println(e.getMessage());
//                    } catch (RepositoryConverterException e) {
//                        e.printStackTrace();
//                    } catch (RepositoryException e) {
//                        e.printStackTrace();
//                    }

//            channel.basicConsume("ADD_ACCOUNT_QUEUE", true, new DefaultConsumer(channel) {
//                @Override
//                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                    super.handleDelivery(consumerTag, envelope, properties, body);
//                }
//            });
//                    new DeliverCallback() {
//                @Override
//                public void handle(String s, Delivery delivery) throws IOException {
//                    Jsonb jsonb = JsonbBuilder.create();
//                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
//                    AccountRestDTO accountRestDTO = jsonb.fromJson(message, AccountRestDTO.class);
//                    System.out.println("[ RECEIVE ]     ACCOUNT: " + accountRestDTO.toString());
//
//                    System.out.println("--- debug ---");
//                    ViewAccountRestConverter viewAccountRestConverter = new ViewAccountRestConverter();
//                    DomainModel.Account account = viewAccountRestConverter.convertFrom(accountRestDTO);
//                    System.out.println("--- debug ---");
//                    boolean response = addAccountUseCase.addAccount(account);
//                    System.out.println("--- debug ---");
//                }
//            }, consumerTag -> { });


//        try {
//            System.out.println("--- debug ---");
//            Jsonb jsonb = JsonbBuilder.create();
//            ConnectionFactory factory = new ConnectionFactory();
//            factory.setHost("rabbitmq");
//            Connection connection = factory.newConnection();
//            Channel channel = connection.createChannel();
//            channel.queueDeclare("ADD_ACCOUNT_QUEUE", false, false, false, null);
//
//            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
//                AccountRestDTO accountRestDTO = jsonb.fromJson(message, AccountRestDTO.class);
//                System.out.println("[ RECEIVE ]     ACCOUNT: " + accountRestDTO.toString());
//
//                System.out.println("--- debug ---");
//                DomainModel.Account account = viewAccountRestConverter.convertFrom(accountRestDTO);
//                System.out.println("--- debug ---");
//                boolean response = addAccountUseCase.addAccount(account);
//                System.out.println("--- debug ---");
////                boolean response = true;
//
//                try {
//                    channel.queueDeclare("ADD_ACCOUNT_QUEUE_RESPONSE", false, false, false, null);
//
//                    AMQP.BasicProperties props = new AMQP.BasicProperties
//                            .Builder()
//                            .correlationId(accountRestDTO.getCorrId())
//                            .build();
//
//                    System.out.println("--- debug ---");
//                    channel.basicPublish("", "ADD_ACCOUNT_QUEUE_RESPONSE", props, Boolean.toString(response).getBytes(StandardCharsets.UTF_8));
//                    System.out.println("[ SEND ]     ACCOUNT: " + Boolean.toString(response));
//                } catch (IOException e) {
//                    System.out.println(e.getMessage());
//                }
//            };
//
//            channel.basicConsume("ADD_ACCOUNT_QUEUE", true, deliverCallback, consumerTag -> {
//            });
//        } catch (IOException | TimeoutException e) {
//            e.printStackTrace();
//        }

////@WebListener
//public class AddAccountReceiver implements ServletContextListener {
//
////    @Inject
////    private AddAccountUseCase addAccountUseCase;
////
////    @Inject
////    private ViewAccountRestConverter viewAccountRestConverter;
//
//    @Override
//    public void contextInitialized(ServletContextEvent contextEvent) {
//        try {
//            Jsonb jsonb = JsonbBuilder.create();
//            ConnectionFactory factory = new ConnectionFactory();
//            factory.setHost("rabbitmq");
//            Connection connection = factory.newConnection();
//            Channel channel = connection.createChannel();
//            channel.queueDeclare("ADD_ACCOUNT_QUEUE", false, false, false, null);
//
//            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
//                AccountRestDTO accountRestDTO = jsonb.fromJson(message, AccountRestDTO.class);
//                System.out.println("[ RECEIVE ]     ACCOUNT: " + accountRestDTO.toString());
//
////                DomainModel.Account account = viewAccountRestConverter.convertFrom(accountRestDTO);
////                boolean response = addAccountUseCase.addAccount(account);
//                boolean response = true;
//
//                try {
//                    channel.queueDeclare("ADD_ACCOUNT_QUEUE_RESPONSE", false, false, false, null);
//
//                    AMQP.BasicProperties props = new AMQP.BasicProperties
//                            .Builder()
//                            .correlationId(accountRestDTO.getCorrId())
//                            .build();
//
//                    channel.basicPublish("", "ADD_ACCOUNT_QUEUE_RESPONSE", props, Boolean.toString(response).getBytes(StandardCharsets.UTF_8));
//                    System.out.println("[ SEND ]     ACCOUNT: " + Boolean.toString(response) + " was sent");
//                } catch (IOException e) {
//                    System.out.println(e.getMessage());
//                }
//            };
//
//            channel.basicConsume("ADD_ACCOUNT_QUEUE", true, deliverCallback, consumerTag -> {
//            });
//        } catch (IOException | TimeoutException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent contextEvent) {
//        System.out.println("DESTROYED");
//    }

