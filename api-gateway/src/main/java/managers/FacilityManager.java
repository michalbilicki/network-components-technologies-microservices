package managers;

import dto.BasketballFacilityDto;
import dto.FootballFacilityDto;
import dto.SportsFacilityDto;
import queue.Receiver;
import queue.Sender;
import utils.Consts;
import utils.exception.ManagerException;
import utils.exception.SenderException;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FacilityManager {

//    public void addFootballFacility(FootballFacilityDto footballFacilityDto) throws ManagerException {
//        String corrId = footballFacilityDto.getId();
//        Sender<FootballFacilityDto> sender = new Sender<>(Consts.ADD_FOOTBALL_QUEUE);
//        sender.send(footballFacilityDto, corrId);
//        Receiver receiver = new Receiver(Consts.ADD_FOOTBALL_QUEUE);
//        if (!Boolean.parseBoolean(receiver.receive(corrId))) {
//            throw new ManagerException();
//        }
//    }

    public void addFootballFacility(FootballFacilityDto footballFacilityDto) throws SenderException {
        String corrId = footballFacilityDto.getId();
        Sender<FootballFacilityDto> sender = new Sender<>(Consts.ADD_FOOTBALL_QUEUE);
        sender.asyncSend(footballFacilityDto);
    }

//    public void addBasketBallFacility(BasketballFacilityDto basketballFacilityDto) throws ManagerException {
//        String corrId = basketballFacilityDto.getId();
//        Sender<BasketballFacilityDto> sender = new Sender<>(Consts.ADD_BASKETBALL_QUEUE);
//        sender.send(basketballFacilityDto, corrId);
//        Receiver receiver = new Receiver(Consts.ADD_BASKETBALL_QUEUE);
//        if (!Boolean.parseBoolean(receiver.receive(corrId))) {
//            throw new ManagerException();
//        }
//    }

    public void addBasketBallFacility(BasketballFacilityDto basketballFacilityDto) throws SenderException {
        String corrId = basketballFacilityDto.getId();
        Sender<BasketballFacilityDto> sender = new Sender<>(Consts.ADD_BASKETBALL_QUEUE);
        sender.asyncSend(basketballFacilityDto);
    }

    public SportsFacilityDto getSportsFacility(String id) throws ManagerException {
        try {
            Jsonb jsonb = JsonbBuilder.create();
            Sender<String> sender = new Sender<>(Consts.GET_FACILITY_QUEUE);
            sender.send(id, id);
            Receiver receiver = new Receiver(Consts.GET_FACILITY_QUEUE);
            String json = receiver.receive(id);
            SportsFacilityDto sportsFacilityDto = jsonb.fromJson(json, SportsFacilityDto.class);
            if (sportsFacilityDto != null) {
                return sportsFacilityDto;
            } else {
                throw new ManagerException();
            }
        } catch (JsonbException e) {
            throw new ManagerException();
        }
    }

    public List<SportsFacilityDto> getAllSportsFacilities() throws ManagerException {
        try {
            String corrId = UUID.randomUUID().toString();
            Jsonb jsonb = JsonbBuilder.create();
            Sender<String> sender = new Sender<>(Consts.GET_ALL_FACILITY_QUEUE);
            sender.send(corrId, corrId);
            Receiver receiver = new Receiver(Consts.GET_ALL_FACILITY_QUEUE);
            String json = receiver.receive(corrId);
            return Arrays.asList(jsonb.fromJson(json, SportsFacilityDto[].class));
        } catch (JsonbException e) {
            throw new ManagerException();
        }
    }

//    public void removeFacility(String id) throws ManagerException {
//        Sender<String> sender = new Sender<>(Consts.REMOVE_FACILITY_QUEUE);
//        sender.send(id, id);
//        Receiver receiver = new Receiver(Consts.REMOVE_FACILITY_QUEUE);
//        if (!Boolean.parseBoolean(receiver.receive(id))) {
//            throw new ManagerException();
//        }
//    }

    public void removeFacility(String id) throws SenderException {
        Sender<String> sender = new Sender<>(Consts.REMOVE_FACILITY_QUEUE);
        sender.asyncSend(id);
    }

//    public void updateFootballFacility(FootballFacilityDto footballFacilityDto) throws ManagerException {
//        String corrId = footballFacilityDto.getId();
//        Sender<FootballFacilityDto> sender = new Sender<>(Consts.UPDATE_FOOTBALL_FACILITY_QUEUE);
//        sender.send(footballFacilityDto, corrId);
//        Receiver receiver = new Receiver(Consts.UPDATE_FOOTBALL_FACILITY_QUEUE);
//        if (!Boolean.parseBoolean(receiver.receive(corrId))) {
//            throw new ManagerException();
//        }
//    }

    public void updateFootballFacility(FootballFacilityDto footballFacilityDto) throws SenderException {
        String corrId = footballFacilityDto.getId();
        Sender<FootballFacilityDto> sender = new Sender<>(Consts.UPDATE_FOOTBALL_FACILITY_QUEUE);
        sender.asyncSend(footballFacilityDto);
    }

//    public void updateBasketballFacility(BasketballFacilityDto basketballFacilityDto) throws ManagerException {
//        String corrId = basketballFacilityDto.getId();
//        Sender<BasketballFacilityDto> sender = new Sender<>(Consts.UPDATE_BASKETBALL_FACILITY_QUEUE);
//        sender.send(basketballFacilityDto, corrId);
//        Receiver receiver = new Receiver(Consts.UPDATE_BASKETBALL_FACILITY_QUEUE);
//        if (!Boolean.parseBoolean(receiver.receive(corrId))) {
//            throw new ManagerException();
//        }
//    }

    public void updateBasketballFacility(BasketballFacilityDto basketballFacilityDto) throws SenderException {
        String corrId = basketballFacilityDto.getId();
        Sender<BasketballFacilityDto> sender = new Sender<>(Consts.UPDATE_BASKETBALL_FACILITY_QUEUE);
        sender.asyncSend(basketballFacilityDto);
    }
}
