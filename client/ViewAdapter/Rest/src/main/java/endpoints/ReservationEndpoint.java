package endpoints;

import conventers.rest.ViewReservationRestConverter;
import exception.ReservationException;
import exception.ReservationNotFoundException;
import pl.lapciakbilicki.ApplicationCore.DomainModel.exceptions.ReservationError;
import pl.lapciakbilicki.ApplicationPorts.Interfaces.User.reservation.*;
import rest.ReservationDetailsRestDTO;
import rest.ReservationRestDTO;

import javax.inject.Inject;
import javax.inject.Named;
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
    private @Named("ClientService")
    MakeReservationUseCase makeReservationUseCase;

    @Inject
    private @Named("ClientService")
    GetClientReservationUseCase getClientReservationUseCase;

    @Inject
    private @Named("ReservationService")
    GetReservationUseCase getReservationUseCase;

    @Inject
    private @Named("ReservationService")
    GetAllReservationUseCase getAllReservationUseCase;


    @Inject
    private @Named("ReservationService")
    CancelReservationUseCase cancelReservationUseCase;

    @Inject
    private @Named("ReservationService")
    RemoveReservationUseCase removeReservationUseCase;

    @Inject
    ViewReservationRestConverter viewReservationConverter;


    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ReservationRestDTO getReservation(@PathParam("id") String id){
        try {
            return viewReservationConverter.convertTo(getReservationUseCase.getReservation(UUID.fromString(id)));
        } catch(IllegalArgumentException | UnsupportedOperationException e){
            throw new ReservationException("Wrong ID");
        }
    }

    @GET
    @Path("reservations")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReservationRestDTO> getAllReservations(){
        return  getAllReservationUseCase.getAll()
                .stream()
                .map(viewReservationConverter::convertTo)
                .collect(Collectors.toList());
    }

    @GET
    @Path("client/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReservationRestDTO> getClientReservations(@PathParam("id") String id){
        try {
            return getClientReservationUseCase.getClientReservation(UUID.fromString(id)).stream()
                    .map(viewReservationConverter::convertTo)
                    .collect(Collectors.toList());
        }catch(IllegalArgumentException | UnsupportedOperationException e){
            throw new ReservationException("Wrong client id");
        }
    }

    @DELETE
    @Path("{id}")
    public Response removeReservation(@PathParam("id") String id){
        try{
            if(!removeReservationUseCase.removeReservation(UUID.fromString(id)))
                throw new ReservationNotFoundException("Reservation doesn't exist");
            return Response.ok().build();
        }catch (UnsupportedOperationException | IllegalArgumentException e){
            throw new ReservationException("Wrong id");
        }
    }

    @PUT
    @Path("{clientId}/{reservationId}")
    public Response cancelReservation(@PathParam("clientId") String clientId, @PathParam("reservationId") String reservationId){
        try {
            cancelReservationUseCase.cancelReservation(UUID.fromString(clientId), UUID.fromString(reservationId));
            return Response.ok().build();
        } catch (ReservationError reservationError) {
            throw new ReservationException(reservationError.getMessage());
        }catch(IllegalArgumentException | UnsupportedOperationException e){
            throw new ReservationException("Wrong id");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response makeReservation(@Valid ReservationDetailsRestDTO reservationDetailsDTO){
        try {
            if (reservationDetailsDTO == null) {
                throw new ReservationException("Insert data");
            }

            String clientId = reservationDetailsDTO.getClientId();
            String sportsFacilityId = reservationDetailsDTO.getSportsFacilityId();
            LocalDateTime startDate = reservationDetailsDTO.getStartDate();
            LocalDateTime endDate = reservationDetailsDTO.getEndDate();

            if (!makeReservationUseCase.createReservation(UUID.fromString(clientId), UUID.fromString(sportsFacilityId), startDate, endDate)) {
                throw new ReservationException("Error during create reservation");
            }
            return Response.ok().build();
        }catch(IllegalArgumentException | UnsupportedOperationException e){
            throw new ReservationException("Wrong id format");
        }
    }
}
