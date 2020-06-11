package endpoints;

import ApplicationPorts.User.ClientServiceUseCase;
import Model.ViewClientRestConverter;
import Model.dto.ClientDto;
import exceptions.RepositoryException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("client")
public class ClientEndpoint {

    @Inject
    private ClientServiceUseCase clientServiceUseCase;

    @PUT
    @Path("update")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateClient(ClientDto clientDto) {
        try {
            clientServiceUseCase.updateClient(ViewClientRestConverter.convertFrom(clientDto));
            return Response.ok().build();
        } catch (RepositoryException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("clients")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getClients() {
        List<ClientDto> list = clientServiceUseCase.getAllClients()
                .stream()
                .map(ViewClientRestConverter::convertTo)
                .collect(Collectors.toList());
        return Response.ok().entity(list).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getClient(@PathParam("id") String id) {
        try {
            ClientDto clientDto = ViewClientRestConverter.convertTo(clientServiceUseCase.getClient(UUID.fromString(id)));
            return Response.ok().entity(clientDto).build();
        } catch (RepositoryException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
