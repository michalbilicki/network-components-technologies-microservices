package managers;

import dto.ClientDto;
import queue.Receiver;
import queue.Sender;
import utils.Consts;
import utils.exception.ManagerException;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ClientManager {

    public ClientDto getClient(String id) throws ManagerException {
        try {
            String corrId = id;
            Jsonb jsonb = JsonbBuilder.create();
            Sender<String> sender = new Sender<String>(Consts.GET_CLIENT_QUEUE);
            sender.send(id, corrId);
            Receiver receiver = new Receiver(Consts.GET_CLIENT_QUEUE);
            String json = receiver.receive(corrId);
            ClientDto clientDto = jsonb.fromJson(json, ClientDto.class);
            if (clientDto != null) {
                return clientDto;
            } else {
                throw new ManagerException();
            }
        } catch (JsonbException e) {
            throw new ManagerException();
        }
    }

    public List<ClientDto> getAllClients() throws ManagerException {
        try {
            String corrId = UUID.randomUUID().toString();
            Jsonb jsonb = JsonbBuilder.create();
            Sender<String> sender = new Sender<String>(Consts.GET_ALL_CLIENT_QUEUE);
            sender.send(corrId, corrId);
            Receiver receiver = new Receiver(Consts.GET_ALL_CLIENT_QUEUE);
            String json = receiver.receive(corrId);
            return Arrays.asList(jsonb.fromJson(json, ClientDto[].class));
        } catch (JsonbException e) {
            throw new ManagerException();
        }
    }
}
