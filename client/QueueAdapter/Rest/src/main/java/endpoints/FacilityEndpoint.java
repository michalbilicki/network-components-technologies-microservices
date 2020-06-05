package endpoints;

import ApplicationPorts.User.SportFacilityUseCase;
import Model.ViewFacilityRestConverter;
import DomainModel.SportsFacility;
import Model.dto.BasketballFacilityRestDTO;
import Model.dto.FootballFacilityRestDTO;
import Model.dto.SportsFacilityRestDTO;
import exceptions.RepositoryConverterException;
import exceptions.RepositoryException;
import exceptions.ViewConverterException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("facility")
public class FacilityEndpoint {

    @Inject
    private SportFacilityUseCase sportFacilityUseCase;

    public FacilityEndpoint() {

    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSportsFacility(@PathParam("id") String id) {
        try {
            SportsFacilityRestDTO sportsFacilityDTO = null;
            sportsFacilityDTO = ViewFacilityRestConverter.convertTo(sportFacilityUseCase.getSportsFacility(UUID.fromString(id)));
            return Response.ok().entity(sportsFacilityDTO).build();
        } catch (ViewConverterException | RepositoryConverterException | RepositoryException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("facilities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        try {
            List<SportsFacility> list = sportFacilityUseCase.getAllSportsFacilities();
            List<SportsFacilityRestDTO> result = new ArrayList<SportsFacilityRestDTO>();
            for (SportsFacility item : list) {
                result.add(ViewFacilityRestConverter.convertTo(item));
            }
            return Response.ok().entity(result).build();
        } catch (RepositoryConverterException | ViewConverterException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("add/football")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addFootballFacility(FootballFacilityRestDTO footballFacilityDTO) {
        try {
            sportFacilityUseCase.addSportsFacility(ViewFacilityRestConverter.convertFrom(footballFacilityDTO));
            return Response.ok().build();
        } catch (ViewConverterException | RepositoryConverterException | RepositoryException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("add/basketball")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBasketBallFacility(BasketballFacilityRestDTO basketballFacilityDTO) {
        try {
            sportFacilityUseCase.addSportsFacility(ViewFacilityRestConverter.convertFrom(basketballFacilityDTO));
            return Response.ok().build();
        } catch (ViewConverterException | RepositoryConverterException | RepositoryException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("delete/{id}")
    public Response removeSportsFacility(@PathParam("id") String id) {
        try {
            sportFacilityUseCase.removeSportsFacility(UUID.fromString(id));
            return Response.ok().build();
        } catch (RepositoryException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("update/football")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFootballFacility(FootballFacilityRestDTO footballFacilityDTO) {
        try {
            SportsFacility sportsFacility = ViewFacilityRestConverter.convertFrom(footballFacilityDTO);
            sportFacilityUseCase.updateSportsFacility(sportsFacility);
            return Response.ok().build();
        } catch (RepositoryConverterException | RepositoryException | ViewConverterException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("update/basketball")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBasketballFacility(BasketballFacilityRestDTO basketballFacilityDTO) {
        try {
            SportsFacility sportsFacility = ViewFacilityRestConverter.convertFrom(basketballFacilityDTO);
            sportFacilityUseCase.updateSportsFacility(sportsFacility);
            return Response.ok().build();
        } catch (RepositoryConverterException | RepositoryException | ViewConverterException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
