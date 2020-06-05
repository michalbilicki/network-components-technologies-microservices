package endpoints;

import ApplicationPorts.User.ClientUseCase;
import ApplicationPorts.User.ReservationUseCase;
import Model.ViewReservationRestConverter;
import exception.ReservationException;
import exceptions.RepositoryConverterException;
import exceptions.RepositoryException;
import exceptions.ReservationError;
import exceptions.SportsFacilityDoesNotExists;
import Model.dto.ReservationDetailsRestDTO;
import Model.dto.ReservationRestDTO;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("reservation")
public class ReservationEndpoint {

    @Inject
    private ClientUseCase clientUseCase;

    @Inject
    private ReservationUseCase reservationUseCase;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservation(@PathParam("id") String id) {
        try {
            ReservationRestDTO result = ViewReservationRestConverter.convertTo(reservationUseCase.getReservation(UUID.fromString(id)));
            return Response.ok().entity(result).build();
        } catch (RepositoryException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("reservations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReservations() {
        List<ReservationRestDTO> list = reservationUseCase.getAll()
                .stream()
                .map(ViewReservationRestConverter::convertTo)
                .collect(Collectors.toList());
        return Response.ok().entity(list).build();
    }

    @GET
    @Path("client/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientReservations(@PathParam("id") String id) {
        try {
            List<ReservationRestDTO> list = clientUseCase.getClientReservation(UUID.fromString(id)).stream()
                    .map(ViewReservationRestConverter::convertTo)
                    .collect(Collectors.toList());
            return Response.ok().entity(list).build();
        } catch (RepositoryException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response removeReservation(@PathParam("id") String id) {
        try {
            reservationUseCase.removeReservation(UUID.fromString(id));
            return Response.ok().build();
        } catch (RepositoryException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("{clientId}/{reservationId}")
    public Response cancelReservation(@PathParam("clientId") String clientId, @PathParam("reservationId") String reservationId) {
        try {
            reservationUseCase.cancelReservation(UUID.fromString(clientId), UUID.fromString(reservationId));
            return Response.ok().build();
        } catch (ReservationError | RepositoryException reservationError) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response makeReservation(@Valid ReservationDetailsRestDTO reservationDetailsDTO) {

        try {
            if (reservationDetailsDTO == null) {
                throw new ReservationException();
            }

            String clientId = reservationDetailsDTO.getClientId();
            String sportsFacilityId = reservationDetailsDTO.getSportsFacilityId();
            LocalDateTime startDate = reservationDetailsDTO.getStartDate();
            LocalDateTime endDate = reservationDetailsDTO.getEndDate();
            clientUseCase.createReservation(UUID.fromString(clientId), UUID.fromString(sportsFacilityId), startDate, endDate);
            return Response.ok().build();
        } catch (RepositoryException | RepositoryConverterException | SportsFacilityDoesNotExists | ReservationError e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
