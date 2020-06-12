package endpoints;

import dto.BasketballFacilityDto;
import dto.FootballFacilityDto;
import managers.FacilityManager;
import utils.exception.ManagerException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("facility")
@RequestScoped
public class FacilityEndpoint {

    @Inject
    private FacilityManager facilityManager;

    @POST
    @Path("add/football")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addFootballFacility(FootballFacilityDto footballFacilityDto) {
        try {
            facilityManager.addFootballFacility(footballFacilityDto);
            return Response.ok().build();
        } catch (ManagerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("add/basketball")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBasketBallFacility(BasketballFacilityDto basketballFacilityDto) {
        try {
            facilityManager.addBasketBallFacility(basketballFacilityDto);
            return Response.ok().build();
        } catch (ManagerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSportsFacility(@PathParam("id") String id) {
        try {
            return Response.ok().entity(facilityManager.getSportsFacility(id)).build();
        } catch (ManagerException | JsonbException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("facilities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        try {
            return Response.ok().entity(facilityManager.getAllSportsFacilities()).build();
        } catch (ManagerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("delete/{id}")
    public Response removeSportsFacility(@PathParam("id") String id) {
        try {
            facilityManager.removeFacility(id);
            return Response.ok().build();
        } catch (ManagerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("update/football")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFootballFacility(FootballFacilityDto footballFacilityDto) {
        try {
            facilityManager.updateFootballFacility(footballFacilityDto);
            return Response.ok().build();
        } catch (ManagerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("update/basketball")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBasketballFacility(BasketballFacilityDto basketballFacilityDto) {
        try {
            facilityManager.updateBasketballFacility(basketballFacilityDto);
            return Response.ok().build();
        } catch (ManagerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
