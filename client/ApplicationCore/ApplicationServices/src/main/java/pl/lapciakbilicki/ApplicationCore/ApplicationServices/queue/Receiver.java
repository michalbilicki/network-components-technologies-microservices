package pl.lapciakbilicki.ApplicationCore.ApplicationServices.queue;

import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import pl.lapciakbilicki.ApplicationCore.ApplicationServices.services.ClientService;
import pl.lapciakbilicki.ApplicationCore.DomainModel.Client;

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
////                ClientResponse clientResponse = jsonb.fromJson(message, ClientResponse.class);
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



import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletContextListener;
        import javax.servlet.ServletContextEvent;

public class Receiver implements ServletContextListener{

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
//                ClientResponse clientResponse = jsonb.fromJson(message, ClientResponse.class);
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
//@Singleton
//public class StatusBean {
//    private String status;
//
//    @PostConstruct
//    void init {
//        status = "Ready";
//    }
//  ...
//}
