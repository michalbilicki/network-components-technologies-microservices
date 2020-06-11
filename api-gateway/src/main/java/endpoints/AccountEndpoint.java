package endpoints;

import dto.AccountDto;
import dto.ClientDto;
import queue.Receiver;
import queue.Sender;
import utils.Consts;
import utils.exception.EndpointException;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Path("account")
public class AccountEndpoint {

    @POST
    @Path("add")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addAccount(AccountDto accountDto) {
        try {
            boolean result;
            String corrId = UUID.randomUUID().toString();
            accountDto.setCorrId(corrId);

            Sender<AccountDto> accountSender = new Sender<AccountDto>(Consts.ADD_ACCOUNT_QUEUE);
            accountSender.send(accountDto, corrId);

            Receiver accountReceiver = new Receiver(Consts.ADD_ACCOUNT_QUEUE);
            result = Boolean.parseBoolean(accountReceiver.receive(corrId));

            if (result && !accountDto.getRoles().contains("Client")) {
                return Response.ok().build();
            } else if (result && accountDto.getRoles().contains("Client")) {
                //TODO check!!!
//                ClientDto clientDto = ClientDto.convertFrom(accountDto);
//
//                Sender<ClientDto> clientSender = new Sender<ClientDto>(Consts.ADD_CLIENT_QUEUE);
//                clientSender.send(clientDto, corrId);
//
//                Receiver clientReceiver = new Receiver(Consts.ADD_CLIENT_QUEUE);
//
//                if (Boolean.parseBoolean(clientReceiver.receive(corrId))) {
                    return Response.ok().build();
//                } else {
//                    throw new EndpointException();
//                }
            } else {
                throw new EndpointException();
            }
        } catch (EndpointException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("delete/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response removeAccount(@PathParam("id") String id) {
        Sender<String> sender = new Sender<String>(Consts.REMOVE_ACCOUNT_QUEUE);
        sender.send(id, id);

        Receiver receiver = new Receiver(Consts.REMOVE_ACCOUNT_QUEUE);

        if (Boolean.parseBoolean(receiver.receive(id))) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("update")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateAccount(AccountDto accountDto) {
        String corrId = UUID.randomUUID().toString();
        accountDto.setCorrId(corrId);

        Sender<AccountDto> sender = new Sender<AccountDto>(Consts.UPDATE_ACCOUNT_QUEUE);
        sender.send(accountDto, corrId);

        Receiver receiver = new Receiver(Consts.UPDATE_ACCOUNT_QUEUE);

        if (Boolean.parseBoolean(receiver.receive(corrId))) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("block/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response blockAccount(@PathParam("id") String id) {
        Sender<String> sender = new Sender<String>(Consts.BLOCK_ACCOUNT_QUEUE);
        sender.send(id, id);

        Receiver receiver = new Receiver(Consts.BLOCK_ACCOUNT_QUEUE);

        if (Boolean.parseBoolean(receiver.receive(id))) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("unblock/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response unblockAccount(@PathParam("id") String id) {
        Sender<String> sender = new Sender<String>(Consts.UNBLOCK_ACCOUNT_QUEUE);
        sender.send(id, id);

        Receiver receiver = new Receiver(Consts.UNBLOCK_ACCOUNT_QUEUE);

        if (Boolean.parseBoolean(receiver.receive(id))) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAccount(@PathParam("id") String id) {
        Jsonb jsonb = JsonbBuilder.create();
        Sender<String> sender = new Sender<String>(Consts.GET_ACCOUNT_QUEUE);
        sender.send(id, id);

        Receiver receiver = new Receiver(Consts.GET_ACCOUNT_QUEUE);
        String json = receiver.receive(id);
        AccountDto accountDto = jsonb.fromJson(json, AccountDto.class);

        if (accountDto != null) {
            return Response.ok().entity(accountDto).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("accounts")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAccounts() {
        String corrId = UUID.randomUUID().toString();

        Jsonb jsonb = JsonbBuilder.create();
        Sender<String> sender = new Sender<String>(Consts.GET_ALL_ACCOUNT_QUEUE);
        sender.send(corrId, corrId);

        Receiver receiver = new Receiver(Consts.GET_ALL_ACCOUNT_QUEUE);
        String json = receiver.receive(corrId);
        List<AccountDto> list = Arrays.asList(jsonb.fromJson(json, AccountDto[].class));

        if (list.size() != 0) {
            return Response.ok().entity(list).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
