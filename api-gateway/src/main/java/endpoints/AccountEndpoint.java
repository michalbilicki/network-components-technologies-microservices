package endpoints;

import dto.AccountDto;
import managers.AccountManager;
import utils.exception.ManagerException;
import utils.exception.SenderException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("account")
@RequestScoped
public class AccountEndpoint {

    @Inject
    private AccountManager accountManager;

    @POST
    @Path("add")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addAccount(AccountDto accountDto) {
        try {
            accountManager.addAccount(accountDto);
            return Response.ok().build();
        } catch (SenderException | ManagerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("delete/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response removeAccount(@PathParam("id") String id) {
        try {
            accountManager.deleteAccount(id);
            return Response.ok().build();
        } catch (ManagerException | SenderException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("update")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateAccount(AccountDto accountDto) {
        try {
            accountManager.updateAccount(accountDto);
            return Response.ok().build();
        } catch (SenderException | ManagerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("block/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response blockAccount(@PathParam("id") String id) {
        try {
            accountManager.blockAccount(id);
            return Response.ok().build();
        } catch (ManagerException | SenderException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("unblock/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response unblockAccount(@PathParam("id") String id) {
        try {
            accountManager.unblockAccount(id);
            return Response.ok().build();
        } catch (ManagerException | SenderException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAccount(@PathParam("id") String id) {
        try {
            return Response.ok().entity(accountManager.getAccount(id)).build();
        } catch (ManagerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("accounts")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAccounts() {
        try {
            return Response.ok().entity(accountManager.getAllAccounts()).build();
        } catch (ManagerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
