package ApplicationServices;

import ApplicationPorts.AccountServicePort;
import ApplicationPorts.AccountServiceUseCase;
import DomainModel.Account;
import exceptions.RepositoryConverterException;
import exceptions.RepositoryException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@ApplicationScoped
public class AccountService implements AccountServiceUseCase {

    @Inject
    private AccountServicePort accountServicePort;

    public AccountService() {
    }

    @Override
    public Account getAccount(UUID id) throws RepositoryConverterException {
        return accountServicePort.getAccount(id);
    }

    @Override
    public List<Account> getAllAccount() throws RepositoryConverterException {
        return accountServicePort.getAllAccounts();
    }

    @Override
    public void removeAccount(Account account) throws RepositoryException {
        accountServicePort.removeAccount(account);
    }

    @Override
    public void removeAccount(UUID id) throws RepositoryException {
        accountServicePort.removeAccount(id);
    }

    @Override
    public void updateAccount(Account account) throws RepositoryException {
            accountServicePort.updateAccount(account);
    }

    public List<Account> filterAccount(Predicate<Account> predicate) throws RepositoryConverterException {
        return accountServicePort.getFilteredAccount(predicate);
    }

    @Override
    public void addAccount(Account account) throws RepositoryConverterException, RepositoryException {
            accountServicePort.addAccount(account);
    }

    @Override
    public void blockAccount(UUID id) throws RepositoryException, RepositoryConverterException {
        Account account = getAccount(id);
        if (account != null) {
            account.block();
            updateAccount(account);
        }
    }

    @Override
    public void unblockAccount(UUID accountId) throws RepositoryException, RepositoryConverterException {
        Account account = getAccount(accountId);
        if (account != null) {
            account.unblock();
            updateAccount(account);
        }
    }
}



































//    public void testMethod() {
//        try {
//            ConnectionFactory factory = new ConnectionFactory();
//            factory.setHost("rabbitmq");
//
//            Connection connection = factory.newConnection();
//            Channel channel = connection.createChannel();
//
//            String message = "teeeeeeeeeeeest message";
//            String requestQueueName = "rpc_queue";
//
//            final String corrId = UUID.randomUUID().toString();
//
////            String replyQueueName = channel.queueDeclare().getQueue();
////
////            String replyQueueName = "rpc_queue_response";
//            channel.queueDeclare(requestQueueName, false, false, false, null);
//
//
//            AMQP.BasicProperties props = new AMQP.BasicProperties
//                    .Builder()
//                    .correlationId(corrId)
//                    .replyTo(requestQueueName)
//                    .build();
//
//            channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));
//
//            System.out.println("SENT");
//        } catch (IOException | TimeoutException e) {
//            e.printStackTrace();
//        }

//            final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
//
//            String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
//                if (delivery.getProperties().getCorrelationId().equals(corrId)) {
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    response.offer(new String(delivery.getBody(), "UTF-8"));
//                    System.out.println("RECEIVED 1");
//                }
//            }, consumerTag -> {
//            });
//            System.out.println("RECEIVED 2");
//
//            String result = response.take();
//            System.out.println("RECEIVED 3");
//            channel.basicCancel(ctag);
//            System.out.println(result);
////            return result;
//        } catch (TimeoutException | IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }


//    public void testMethod() {
//        String QUEUE_NAME = "CLIENT_ACCOUNT_CREATE";
//        //String id, String login, String name, String surname, boolean active
//        DomainModel.Client account = new DomainModel.Client(UUID.randomUUID().toString(), "testlogin", "testname", "testsurname", true);
//        Jsonb jsonb = JsonbBuilder.create();
//        ApplicationServices.Sender.send(jsonb.toJson(account));
//        System.out.println("SENDED");
//
//        try {
//            ConnectionFactory factory = new ConnectionFactory();
//            factory.setHost("localhost");
//            Connection connection = factory.newConnection();
//            Channel channel = connection.createChannel();
//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//
//            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//                try {
//                    Thread.sleep(5000);
//                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
//                    ApplicationServices.ClientResponse clientResponse = jsonb.fromJson(message, ApplicationServices.ClientResponse.class);
//                    System.out.println(" [->] Received: " + clientResponse);
//                    channel.close();
//                } catch (TimeoutException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//            };
//            System.out.println("RECEIVED 1");
//            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
//            System.out.println("RECEIVED 2");
//        } catch (IOException | TimeoutException e) {
//            e.printStackTrace();
//        }
//    }
