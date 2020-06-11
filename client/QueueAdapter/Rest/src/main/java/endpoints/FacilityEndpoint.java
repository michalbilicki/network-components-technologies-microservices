package endpoints;

import ApplicationPorts.User.SportFacilityServiceUseCase;
import Model.ViewFacilityConverter;
import DomainModel.SportsFacility;
import Model.dto.BasketballFacilityDto;
import Model.dto.FootballFacilityDto;
import Model.dto.SportsFacilityDto;
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
    private SportFacilityServiceUseCase sportFacilityServiceUseCase;

    public FacilityEndpoint() {

    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSportsFacility(@PathParam("id") String id) {
        try {
            SportsFacilityDto sportsFacilityDTO = null;
            sportsFacilityDTO = ViewFacilityConverter.convertToDto(sportFacilityServiceUseCase.getSportsFacility(UUID.fromString(id)));
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
            List<SportsFacility> list = sportFacilityServiceUseCase.getAllSportsFacilities();
            List<SportsFacilityDto> result = new ArrayList<SportsFacilityDto>();
            for (SportsFacility item : list) {
                result.add(ViewFacilityConverter.convertToDto(item));
            }
            return Response.ok().entity(result).build();
        } catch (RepositoryConverterException | ViewConverterException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("add/football")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addFootballFacility(FootballFacilityDto footballFacilityDTO) {
        try {
            sportFacilityServiceUseCase.addSportsFacility(ViewFacilityConverter.convertFrom(footballFacilityDTO));
            return Response.ok().build();
        } catch (ViewConverterException | RepositoryConverterException | RepositoryException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("add/basketball")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBasketBallFacility(BasketballFacilityDto basketballFacilityDTO) {
        try {
            sportFacilityServiceUseCase.addSportsFacility(ViewFacilityConverter.convertFrom(basketballFacilityDTO));
            return Response.ok().build();
        } catch (ViewConverterException | RepositoryConverterException | RepositoryException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("delete/{id}")
    public Response removeSportsFacility(@PathParam("id") String id) {
        try {
            sportFacilityServiceUseCase.removeSportsFacility(UUID.fromString(id));
            return Response.ok().build();
        } catch (RepositoryException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("update/football")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFootballFacility(FootballFacilityDto footballFacilityDTO) {
        try {
            SportsFacility sportsFacility = ViewFacilityConverter.convertFrom(footballFacilityDTO);
            sportFacilityServiceUseCase.updateSportsFacility(sportsFacility);
            return Response.ok().build();
        } catch (RepositoryConverterException | RepositoryException | ViewConverterException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("update/basketball")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBasketballFacility(BasketballFacilityDto basketballFacilityDTO) {
        try {
            SportsFacility sportsFacility = ViewFacilityConverter.convertFrom(basketballFacilityDTO);
            sportFacilityServiceUseCase.updateSportsFacility(sportsFacility);
            return Response.ok().build();
        } catch (RepositoryConverterException | RepositoryException | ViewConverterException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
