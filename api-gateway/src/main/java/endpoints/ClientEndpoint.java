package endpoints;

import managers.ClientManager;
import utils.exception.ManagerException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("client")
@RequestScoped
public class ClientEndpoint {

    @Inject
    private ClientManager clientManager;

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getClient(@PathParam("id") String id) {
        try {
            return Response.ok().entity(clientManager.getClient(id)).build();
        } catch (ManagerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("clients")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getClients() {
        try {
            return Response.ok().entity(clientManager.getAllClients()).build();
        } catch (ManagerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
