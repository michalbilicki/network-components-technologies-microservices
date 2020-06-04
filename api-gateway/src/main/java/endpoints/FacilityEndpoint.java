package endpoints;

import dto.BasketballFacilityDto;
import dto.FootballFacilityDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("facility")
public class FacilityEndpoint {

    public FacilityEndpoint() {

    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSportsFacility(@PathParam("id") String id) {
        //return SportsFacilityRestDTO
        return Response.ok().build();
    }

    @GET
    @Path("facilities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        //return List<SportsFacilityRestDTO>
        return Response.ok().build();
    }

    @POST
    @Path("football")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addFootballFacility(FootballFacilityDto footballFacilityDTO) {
        return Response.ok().build();
    }

    @POST
    @Path("basketball")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBasketBallFacility(BasketballFacilityDto basketballFacilityDTO) {
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public Response removeSportsFacility(@PathParam("id") String id) {
        return Response.ok().build();
    }

    @PUT
    @Path("football")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFootballFacility(FootballFacilityDto footballFacilityDTO) {
        return Response.ok().build();
    }

    @PUT
    @Path("basketball")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBasketballFacility(BasketballFacilityDto basketballFacilityDTO) {
        return Response.ok().build();
    }
}
