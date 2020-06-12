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
