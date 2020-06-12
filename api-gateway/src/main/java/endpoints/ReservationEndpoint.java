package endpoints;

import dto.ReservationDetailsDto;
import managers.ReservationManager;
import utils.exception.ManagerException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("reservation")
@RequestScoped
public class ReservationEndpoint {

    @Inject
    private ReservationManager reservationManager;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservation(@PathParam("id") String id) {
        try {
            return Response.ok().entity(reservationManager.getReservation(id)).build();
        } catch (ManagerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("reservations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReservations() {
        try {
            return Response.ok().entity(reservationManager.getAllReservations()).build();
        } catch (ManagerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("client/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientReservations(@PathParam("id") String id) {
        try {
            return Response.ok().entity(reservationManager.getClientReservations(id)).build();
        } catch (ManagerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response removeReservation(@PathParam("id") String id) {
        try {
            reservationManager.removeReservation(id);
            return Response.ok().build();
        } catch (ManagerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("{clientId}/{reservationId}")
    public Response cancelReservation(@PathParam("clientId") String clientId,
                                      @PathParam("reservationId") String reservationId) {
        try {
            reservationManager.cancelReservation(clientId, reservationId);
            return Response.ok().build();
        } catch (ManagerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response makeReservation(@Valid ReservationDetailsDto reservationDetailsDto) {
        try {
            reservationManager.makeReservation(reservationDetailsDto);
            return Response.ok().build();
        } catch (ManagerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
