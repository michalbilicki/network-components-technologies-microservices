import Model.AccountDto;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class AddAccountReceiver implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        try {
            Jsonb jsonb = JsonbBuilder.create();
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("rabbitmq");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare("rpc_queue", false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                AccountDto clientResponse = jsonb.fromJson(message, AccountDto.class);
                System.out.println(" [->] Received: " + message);

//                try {
//                    channel.queueDeclare("rpc_queue_response", false, false, false, null);
//                    channel.basicPublish("", "rpc_queue_response", null, message.getBytes(StandardCharsets.UTF_8));
//                    System.out.println(" [<-] Sent: " + message);
//                } catch (IOException e) {
//                    System.out.println(e.getMessage());
//                }
            };

            channel.basicConsume("rpc_queue", true, deliverCallback, consumerTag -> {
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

//@Startup
//@ApplicationScoped
//public class Receiver {
//
//    @Inject
//    private ClientService clientService;
//
//    private final static String QUEUE_NAME = "CLIENT_ACCOUNT_CREATE";
//
//    public void receive() {
//        try {
//            Jsonb jsonb = JsonbBuilder.create();
//            ConnectionFactory factory = new ConnectionFactory();
//            factory.setHost("rabbitmq");
//            Connection connection = factory.newConnection();
//            Channel channel = connection.createChannel();
//            channel.queueDeclare("rpc_queue", false, false, false, null);
//
//            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
////                ApplicationServices.ClientResponse clientResponse = jsonb.fromJson(message, ApplicationServices.ClientResponse.class);
//                System.out.println(" [->] Received: " +  message);
//
////                try {
////                    channel.queueDeclare("rpc_queue_response", false, false, false, null);
////                    channel.basicPublish("", "rpc_queue_response", null, message.getBytes(StandardCharsets.UTF_8));
////                    System.out.println(" [<-] Sent: " + message);
////                } catch (IOException e) {
////                    System.out.println(e.getMessage());
////                }
//            };
//
//            channel.basicConsume("rpc_queue", true, deliverCallback, consumerTag -> {
//            });
//        } catch (IOException | TimeoutException e) {
//            e.printStackTrace();
//        }
//    }
//}




//public class Receiver {
//
//    private final static String QUEUE_NAME = "CLIENT_ACCOUNT_CREATE_RESPONSE";
//
//    public void receive() {
//        try {
//            Jsonb jsonb = JsonbBuilder.create();
//            ConnectionFactory factory = new ConnectionFactory();
//            factory.setHost("localhost");
//            Connection connection = factory.newConnection();
//            Channel channel = null;
//            channel = connection.createChannel();
//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//
//            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
//                ApplicationServices.ClientResponse clientResponse = jsonb.fromJson(message, ApplicationServices.ClientResponse.class);
//                System.out.println(" [->] Received: " + clientResponse);
//            };
//
//            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
//        } catch (IOException | TimeoutException e) {
//            e.printStackTrace();
//        }
//    }
//}