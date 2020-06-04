package endpoints;

import conventers.exceptions.ViewConverterException;
import conventers.rest.ViewFacilityRestConverter;
import exception.SportsFacilityException;
import exception.SportsFacilityNotFoundException;
import pl.lapciakbilicki.ApplicationCore.DomainModel.SportsFacility;
import pl.lapciakbilicki.ApplicationPorts.Interfaces.User.sportsfacility.*;
import pl.lapciakbilicki.ApplicationPorts.Interfaces.User.sportsfacility.AddSportsFacilityUseCase;
import pl.lapciakbilicki.ApplicationPorts.Interfaces.User.sportsfacility.GetSportsFacilityUseCase;
import pl.lapciakbilicki.ApplicationPorts.Interfaces.User.sportsfacility.RemoveSportsFacilityUseCase;
import rest.BasketballFacilityRestDTO;
import rest.FootballFacilityRestDTO;
import rest.SportsFacilityRestDTO;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("facility")
public class FacilityEndpoint {

    @Inject
    private ViewFacilityRestConverter viewFacilityConverter;

    @Inject
    private @Named("FacilityService")
    AddSportsFacilityUseCase addSportsFacilityUseCase;

    @Inject
    private @Named("FacilityService")
    GetSportsFacilityUseCase getSportsFacilityUseCase;

    @Inject
    private @Named("FacilityService")
    RemoveSportsFacilityUseCase removeSportsFacilityUseCase;

    @Inject
    private @Named("FacilityService")
    UpdateSportsFacilityUseCase updateSportsFacilityUseCase;

    public FacilityEndpoint() {

    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public SportsFacilityRestDTO getSportsFacility(@PathParam("id") String id) {
        try {
            SportsFacilityRestDTO sportsFacilityDTO = viewFacilityConverter.convertTo(getSportsFacilityUseCase.getSportsFacility(UUID.fromString(id)));
            if (sportsFacilityDTO == null) {
                throw new SportsFacilityNotFoundException("Sports facility with this id doesn't exist");
            }
            return sportsFacilityDTO;
        } catch (ViewConverterException e) {
            throw new SportsFacilityException(e.getMessage());
        } catch (UnsupportedOperationException | IllegalArgumentException e) {
            throw new SportsFacilityException("Wrong id");
        }
    }

    @GET
    @Path("facilities")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SportsFacilityRestDTO> getAll() {
        return getSportsFacilityUseCase.getAllSportsFacilities().stream().map(s -> {
            try {
                return viewFacilityConverter.convertTo(s);
            } catch (ViewConverterException e) {
                throw new SportsFacilityException(e.getMessage());
            }
        }).collect(Collectors.toList());
    }

    @POST
    @Path("football")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addFootballFacility(FootballFacilityRestDTO footballFacilityDTO) {
        try {
            addSportsFacilityUseCase.addSportsFacility(viewFacilityConverter.convertFrom(footballFacilityDTO));
            return Response.ok().build();
        } catch (ViewConverterException e) {
            throw new SportsFacilityException(e.getMessage());
        }
    }

    @POST
    @Path("basketball")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBasketBallFacility(BasketballFacilityRestDTO basketballFacilityDTO) {
        try {
            addSportsFacilityUseCase.addSportsFacility(viewFacilityConverter.convertFrom(basketballFacilityDTO));
            return Response.ok().build();
        } catch (ViewConverterException e) {
            throw new SportsFacilityException(e.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    public Response removeSportsFacility(@PathParam("id") String id) {
        try {
            if (!removeSportsFacilityUseCase.removeSportsFacility(UUID.fromString(id))) {
                throw new SportsFacilityNotFoundException("Facility doesn't exist");
            }
            return Response.ok().build();
        } catch (IllegalArgumentException | UnsupportedOperationException e) {
            throw new SportsFacilityException("Wrong ID");
        }
    }

    @PUT
    @Path("football")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFootballFacility(FootballFacilityRestDTO footballFacilityDTO) {
        try {
            SportsFacility sportsFacility = viewFacilityConverter.convertFrom(footballFacilityDTO);
            if(!updateSportsFacilityUseCase.updateSportsFacility(sportsFacility))
                throw new SportsFacilityNotFoundException("SportsFacility not found");
            return Response.ok().build();
        } catch (ViewConverterException e) {
            throw new SportsFacilityException("Wrong Data");
        }
    }

    @PUT
    @Path("basketball")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBasketballFacility(BasketballFacilityRestDTO basketballFacilityDTO){
        try{
            SportsFacility sportsFacility = viewFacilityConverter.convertFrom(basketballFacilityDTO);
            if(!updateSportsFacilityUseCase.updateSportsFacility(sportsFacility))
                throw new SportsFacilityNotFoundException("SportsFacility not found");
            return Response.ok().build();
        }catch (ViewConverterException e){
            throw new SportsFacilityException("Wrong Data");
        }
    }
}
