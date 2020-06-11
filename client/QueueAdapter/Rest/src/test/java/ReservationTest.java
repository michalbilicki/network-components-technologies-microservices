import Model.dto.*;
import org.junit.jupiter.api.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationTest extends AbstractContainerBase {

    static String facilityId = "3c37ca72-7289-49ec-8d85-13f294787201";
    static String clientId = null;
    static String reservationId = "";

    @BeforeAll
    static void initParameters() {
        Response response = client
                .target(BASE_URL)
                .path("client/clients")
                .request(MediaType.APPLICATION_JSON)
                .get();

        List<ClientDto> clientDTOList = response
                .readEntity(new GenericType<ArrayList<ClientDto>>() {
                });

        clientId = clientDTOList.get(0).getId();

        BasketballFacilityDto basketballFacilityDto = new BasketballFacilityDto();
        FieldDto FieldDto = new FieldDto();
        FieldDto.setTypeOfGround("test");
        FieldDto.setSurfaceArea(10);
        FieldDto.setMaxAmountOfPeople(10);
        basketballFacilityDto.setId(facilityId);
        basketballFacilityDto.setNumberOfBasket(10);
        basketballFacilityDto.setMinHeightOfBasket(3);
        basketballFacilityDto.setMaxHeightOfBasket(5);
        basketballFacilityDto.setAccess(true);
        basketballFacilityDto.setField(FieldDto);
        basketballFacilityDto.setName("test");
        basketballFacilityDto.setPricePerHours(10);
        response = client.target(BASE_URL)
                .path("facility/basketball")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(jsonb.toJson(basketballFacilityDto), MediaType.APPLICATION_JSON));
        int i = response.getStatus();
    }

    @Test
    @Order(1)
    public void createReservationTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ReservationDetailsDto reservationDetailsDTO = new ReservationDetailsDto();
        reservationDetailsDTO.setClientId(clientId);
        reservationDetailsDTO.setSportsFacilityId(facilityId);
        String date = "2020-07-01 11:30";
        reservationDetailsDTO.setStartDate(LocalDateTime.parse(date, formatter));
        date = "2020-07-01 12:30";
        reservationDetailsDTO.setEndDate(LocalDateTime.parse(date, formatter));
        Response response = client
                .target(BASE_URL)
                .path("reservation")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(jsonb.toJson(reservationDetailsDTO), MediaType.APPLICATION_JSON));
        Assertions.assertEquals(200, response.getStatus());

        response = client
                .target(BASE_URL)
                .path("reservation")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(jsonb.toJson(reservationDetailsDTO), MediaType.APPLICATION_JSON));
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @Order(2)
    public void createReservationWrongIdTest() {
        String date = "2020-07-01 11:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ReservationDetailsDto reservationDetailsDTO = new ReservationDetailsDto();
        reservationDetailsDTO.setClientId("12");
        reservationDetailsDTO.setSportsFacilityId("13");
        reservationDetailsDTO.setStartDate(LocalDateTime.parse(date, formatter));
        date = "2020-07-01 12:30";
        reservationDetailsDTO.setEndDate(LocalDateTime.parse(date, formatter));
        Response response = client
                .target(BASE_URL)
                .path("reservation")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(jsonb.toJson(reservationDetailsDTO), MediaType.APPLICATION_JSON));
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @Order(3)
    public void getAllReservationTest() {
        Response response = client
                .target(BASE_URL)
                .path("reservation/reservations")
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(200, response.getStatus());
        List<ReservationDto> reservationDTOList = response.readEntity(new GenericType<List<ReservationDto>>() {
        });
        Assertions.assertEquals(1, reservationDTOList.size());
        reservationId = reservationDTOList.get(0).getId();
    }

    @Test
    @Order(4)
    public void getReservationTest() {
        Response response = client
                .target(BASE_URL)
                .path("reservation/" + reservationId)
                .request(MediaType.APPLICATION_JSON)
                .get();
        ReservationDto reservationDTO = response.readEntity(ReservationDto.class);
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(reservationId, reservationDTO.getId());
    }

    @Test
    @Order(5)
    public void getReservationWrongIdTest() {
        Response response = client
                .target(BASE_URL)
                .path("reservation/12")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @Order(6)
    public void getClientReservationsTest() {
        Response response = client
                .target(BASE_URL)
                .path("reservation/client/" + clientId)
                .request(MediaType.APPLICATION_JSON)
                .get();
        List<ReservationDto> reservationDTOS = response.readEntity(new GenericType<List<ReservationDto>>() {
        });
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(1, reservationDTOS.size());
    }

    @Test
    @Order(7)
    public void cancelReservationTest() {
        Response response = client
                .target(BASE_URL)
                .path("reservation/" + clientId + "/" + reservationId)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity("", MediaType.APPLICATION_JSON));
        Assertions.assertEquals(200, response.getStatus());
        response = client
                .target(BASE_URL)
                .path("reservation/" + reservationId)
                .request(MediaType.APPLICATION_JSON)
                .get();
        ReservationDto reservationDTO = response.readEntity(ReservationDto.class);
        Assertions.assertFalse(reservationDTO.isActive());
    }

    @Test
    @Order(8)
    public void removeReservationTest() {
        Response response = client
                .target(BASE_URL)
                .path("reservation/" + reservationId)
                .request(MediaType.APPLICATION_JSON)
                .delete();
        Assertions.assertEquals(200, response.getStatus());
        response = client
                .target(BASE_URL)
                .path("reservation/reservations")
                .request(MediaType.APPLICATION_JSON)
                .get();

        List<ReservationDto> reservationDTOS = response.readEntity(new GenericType<List<ReservationDto>>() {
        });
        Assertions.assertEquals(0, reservationDTOS.size());
    }
}
