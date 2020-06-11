package endpoints;

import dto.BasketballFacilityDto;
import dto.FootballFacilityDto;
import dto.SportsFacilityDto;
import queue.Receiver;
import queue.Sender;
import utils.Consts;
import utils.exception.EndpointException;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Path("facility")
public class FacilityEndpoint {

    @POST
    @Path("add/football")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addFootballFacility(FootballFacilityDto footballFacilityDto) {
        try {
            String corrId = footballFacilityDto.getId();

            Sender<FootballFacilityDto> accountSender = new Sender<>(Consts.ADD_FOOTBALL_QUEUE);
            accountSender.send(footballFacilityDto, corrId);

            Receiver accountReceiver = new Receiver(Consts.ADD_FOOTBALL_QUEUE);
            if (Boolean.parseBoolean(accountReceiver.receive(corrId))) {
                return Response.ok().build();
            } else {
                throw new EndpointException();
            }
        } catch (EndpointException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("add/basketball")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBasketBallFacility(BasketballFacilityDto basketballFacilityDto) {
        try {
            String corrId = basketballFacilityDto.getId();

            Sender<BasketballFacilityDto> accountSender = new Sender<>(Consts.ADD_BASKETBALL_QUEUE);
            accountSender.send(basketballFacilityDto, corrId);

            Receiver accountReceiver = new Receiver(Consts.ADD_BASKETBALL_QUEUE);
            if (Boolean.parseBoolean(accountReceiver.receive(corrId))) {
                return Response.ok().build();
            } else {
                throw new EndpointException();
            }
        } catch (EndpointException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSportsFacility(@PathParam("id") String id) {
        try {
            Jsonb jsonb = JsonbBuilder.create();
            Sender<String> sender = new Sender<String>(Consts.GET_FACILITY_QUEUE);
            sender.send(id, id);

            Receiver receiver = new Receiver(Consts.GET_FACILITY_QUEUE);
            String json = receiver.receive(id);
            SportsFacilityDto sportsFacilityDto = jsonb.fromJson(json, SportsFacilityDto.class);

            return Response.ok().entity(sportsFacilityDto).build();
        } catch (JsonbException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("facilities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        try {
            String corrId = UUID.randomUUID().toString();

            Jsonb jsonb = JsonbBuilder.create();
            Sender<String> sender = new Sender<>(Consts.GET_ALL_FACILITY_QUEUE);
            sender.send(corrId, corrId);

            Receiver receiver = new Receiver(Consts.GET_ALL_FACILITY_QUEUE);
            String json = receiver.receive(corrId);
            List<SportsFacilityDto> list = Arrays.asList(jsonb.fromJson(json, SportsFacilityDto[].class));

            if (list.size() != 0) {
                return Response.ok().entity(list).build();
            } else {
                throw new EndpointException();
            }
        } catch (EndpointException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("delete/{id}")
    public Response removeSportsFacility(@PathParam("id") String id) {
        try {
            Sender<String> sender = new Sender<>(Consts.REMOVE_FACILITY_QUEUE);
            sender.send(id, id);

            Receiver receiver = new Receiver(Consts.REMOVE_FACILITY_QUEUE);

            if (Boolean.parseBoolean(receiver.receive(id))) {
                return Response.ok().build();
            } else {
                throw new EndpointException();
            }
        } catch (EndpointException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("update/football")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFootballFacility(FootballFacilityDto footballFacilityDto) {
        try {
            String corrId = footballFacilityDto.getId();

            Sender<FootballFacilityDto> sender = new Sender<>(Consts.UPDATE_FOOTBALL_FACILITY_QUEUE);
            sender.send(footballFacilityDto, corrId);

            Receiver receiver = new Receiver(Consts.UPDATE_FOOTBALL_FACILITY_QUEUE);

            if (Boolean.parseBoolean(receiver.receive(corrId))) {
                return Response.ok().build();
            } else {
                throw new EndpointException();
            }
        } catch (EndpointException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("update/basketball")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBasketballFacility(BasketballFacilityDto basketballFacilityDto) {
        try {
            String corrId = basketballFacilityDto.getId();

            Sender<BasketballFacilityDto> sender = new Sender<>(Consts.UPDATE_BASKETBALL_FACILITY_QUEUE);
            sender.send(basketballFacilityDto, corrId);

            Receiver receiver = new Receiver(Consts.UPDATE_BASKETBALL_FACILITY_QUEUE);

            if (Boolean.parseBoolean(receiver.receive(corrId))) {
                return Response.ok().build();
            } else {
                throw new EndpointException();
            }
        } catch (EndpointException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
