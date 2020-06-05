package endpoints;

import ApplicationPorts.User.ClientUseCase;
import Model.ViewClientRestConverter;
import Model.dto.ClientRestDTO;
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
    private ClientUseCase clientUseCase;

    @PUT
    @Path("update")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateClient(ClientRestDTO clientRestDTO) {
        try {
            clientUseCase.updateClient(ViewClientRestConverter.convertFrom(clientRestDTO));
            return Response.ok().build();
        } catch (RepositoryException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("clients")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getClients() {
        List<ClientRestDTO> list = clientUseCase.getAllClients()
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
            ClientRestDTO clientRestDTO = ViewClientRestConverter.convertTo(clientUseCase.getClient(UUID.fromString(id)));
            return Response.ok().entity(clientRestDTO).build();
        } catch (RepositoryException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
