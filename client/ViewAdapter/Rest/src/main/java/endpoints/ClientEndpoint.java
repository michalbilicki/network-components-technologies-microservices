package endpoints;

import conventers.rest.ViewClientRestConverter;
import rest.ClientRestDTO;
import exception.ClientException;
import exception.ClientNotFoundException;
import pl.lapciakbilicki.ApplicationPorts.Interfaces.User.client.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("client")
public class ClientEndpoint {

    @Inject
    private ViewClientRestConverter viewClientConverter;

    @Inject
    private @Named("ClientService")
    GetClientUseCase getClientUseCase;

    @Inject
    private @Named("ClientService")
    UpdateClientUseCase updateClientUseCase;

    @PUT
    @Path("update")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateClient(ClientRestDTO clientRestDTO) {
        if (updateClientUseCase.updateClient(viewClientConverter.convertFrom(clientRestDTO))) {
            return Response.ok().build();
        } else {
            throw new ClientException("Client is not updated!");
        }
    }

    @GET
    @Path("clients")
    @Produces({MediaType.APPLICATION_JSON})
    public List<ClientRestDTO> getClients() {
        return getClientUseCase.getAllClients()
                .stream()
                .map(viewClientConverter::convertTo)
                .collect(Collectors.toList());
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public ClientRestDTO getClient(@PathParam("id") String id) {
        try {
            ClientRestDTO ClientRestDTO = viewClientConverter.convertTo(getClientUseCase.getClient(UUID.fromString(id)));
            if (ClientRestDTO == null) {
                throw new ClientNotFoundException("Client with such id does not exist");
            } else {
                return ClientRestDTO;
            }
        } catch (UnsupportedOperationException | IllegalArgumentException e) {
            throw new ClientException("Wrong id");
        }
    }
}
