package endpoints;

import dto.ReservationDetailsDto;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("reservation")
public class ReservationEndpoint {

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservation(@PathParam("id") String id) {
        //return ReservationRestDto
        return Response.ok().build();
    }

    @GET
    @Path("reservations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReservations() {
        //return List<ReservationRestDto>
        return Response.ok().build();
    }

    @GET
    @Path("client/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientReservations(@PathParam("id") String id) {
        //return List<ReservationRestDto>
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public Response removeReservation(@PathParam("id") String id) {
        return Response.ok().build();
    }

    @PUT
    @Path("{clientId}/{reservationId}")
    public Response cancelReservation(@PathParam("clientId") String clientId, @PathParam("reservationId") String reservationId) {
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response makeReservation(@Valid ReservationDetailsDto reservationDetailsDto) {
        return Response.ok().build();
    }
}
