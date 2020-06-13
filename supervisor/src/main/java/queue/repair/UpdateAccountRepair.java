package queue.repair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import dto.AccountDto;
import queue.Receiver;
import queue.Sender;
import utils.Consts;
import utils.exception.SenderException;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Singleton
@Startup
public class UpdateAccountRepair implements Serializable {

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(Consts.HOST);
            final Connection connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            channel.queueDeclare(Consts.UPDATE_ACCOUNT_REPAIR, true, false, false, null);

            channel.basicQos(1);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                Jsonb jsonb = JsonbBuilder.create();
                AccountDto accountDto = jsonb.fromJson(message, AccountDto.class);
                System.out.println("[ SUPERVISOR ] - update account: " + accountDto);
                try {
                    Sender<AccountDto> accountSender = new Sender<>(Consts.UPDATE_ACCOUNT_QUEUE);
                    accountSender.send(accountDto, accountDto.getId());
                    Receiver accountReceiver = new Receiver(Consts.UPDATE_ACCOUNT_QUEUE);
                    if (!Boolean.parseBoolean(accountReceiver.receive(accountDto.getId()))) {
                        Sender<AccountDto> repairSender = new Sender<>(Consts.UPDATE_ACCOUNT_REPAIR);
                        repairSender.asyncSend(accountDto);
                    }
                } catch (SenderException e) {
                    System.out.println(Consts.ERROR);
                } finally {
                    System.out.println("[ SUPERVISOR ] - Done");
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
