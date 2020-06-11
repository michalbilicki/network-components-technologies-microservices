import org.junit.jupiter.api.*;
import rest.BasketballFacilityRestDTO;
import rest.FieldRestDTO;
import rest.FootballFacilityRestDTO;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SportsFacilityTest extends AbstractContainerBase {

    private static UUID id = UUID.fromString("922bac94-bf39-4a2a-8bdc-2c4b7b2c8693");

    @Test
    @Order(1)
    public void getAllSportsFacilityTest() {
        Response response = client
                .target(BASE_URL)
                .path("facility/facilities")
                .request(MediaType.APPLICATION_JSON)
                .get();

        Collection<String> sportsFacilityDTOList = response.readEntity(new GenericType<Collection<String>>() {
        });
        Assertions.assertEquals(20, sportsFacilityDTOList.size());
        Assertions.assertEquals(200, response.getStatus());
    }


    @Test
    @Order(2)
    public void addBasketballFacilityTest() {
        BasketballFacilityRestDTO basketballFacilityDTO = new BasketballFacilityRestDTO();
        FieldRestDTO fieldDTO = new FieldRestDTO();
        fieldDTO.setTypeOfGround("test");
        fieldDTO.setSurfaceArea(10);
        fieldDTO.setMaxAmountOfPeople(10);
        basketballFacilityDTO.setId(id.toString());
        basketballFacilityDTO.setNumberOfBasket(10);
        basketballFacilityDTO.setMinHeightOfBasket(3);
        basketballFacilityDTO.setMaxHeightOfBasket(5);
        basketballFacilityDTO.setAccess(true);
        basketballFacilityDTO.setField(fieldDTO);
        basketballFacilityDTO.setName("test");
        basketballFacilityDTO.setPricePerHours(10);

        Response response = client
                .target(BASE_URL)
                .path("facility/basketball")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(jsonb.toJson(basketballFacilityDTO), MediaType.APPLICATION_JSON));
        Assertions.assertEquals(200, response.getStatus());

        response = client
                .target(BASE_URL)
                .path("facility/facilities")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Collection<String> sportsFacilityDTOList = response.readEntity(new GenericType<Collection<String>>() {
        });
        Assertions.assertEquals(21, sportsFacilityDTOList.size());
        Assertions.assertEquals(200, response.getStatus());
    }


    @Test
    @Order(3)
    public void getFacilityTest() {
        Response response = client
                .target(BASE_URL)
                .path("facility/" + id.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assertions.assertEquals(200, response.getStatus());
        BasketballFacilityRestDTO basketballFacilityDTO = response.readEntity(BasketballFacilityRestDTO.class);
        Assertions.assertEquals("test", basketballFacilityDTO.getName());
    }


    @Test
    @Order(4)
    public void getFacilityWrongIdTest() {
        Response response = client
                .target(BASE_URL)
                .path("facility/1")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assertions.assertEquals(400, response.getStatus());
    }


    @Test
    @Order(5)
    public void getBasketballFacilityNotFoundTest() {
        Response response = client
                .target(BASE_URL)
                .path("facility/basketball/" + UUID.randomUUID().toString())
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assertions.assertEquals(404, response.getStatus());
    }


    @Test
    @Order(6)
    public void removeSportsFacilityTest() {
        Response response = client
                .target(BASE_URL)
                .path("facility/" + id.toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();
        Assertions.assertEquals(200, response.getStatus());


    }


    @Test
    @Order(6)
    public void removeSportsFacilityNotFoundTest() {
        Response response = client
                .target(BASE_URL)
                .path("facility/" + UUID.randomUUID().toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();
        Assertions.assertEquals(404, response.getStatus());
    }


    @Test
    @Order(7)
    public void removeSportsFacilityWrongIdTest() {
        Response response = client
                .target(BASE_URL)
                .path("facility/1")
                .request(MediaType.APPLICATION_JSON)
                .delete();
        Assertions.assertEquals(400, response.getStatus());
        response = client
                .target(BASE_URL)
                .path("facility/facilities")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Collection<String> sportsFacilityDTOList = response.readEntity(new GenericType<Collection<String>>() {
        });
        Assertions.assertEquals(20, sportsFacilityDTOList.size());
        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    @Order(8)
    public void addFootballFacilityTest() {
        FootballFacilityRestDTO footballFacilityDTO = new FootballFacilityRestDTO();
        FieldRestDTO fieldDTO = new FieldRestDTO();
        fieldDTO.setTypeOfGround("test");
        fieldDTO.setSurfaceArea(10);
        fieldDTO.setMaxAmountOfPeople(10);
        footballFacilityDTO.setId(id.toString());
        footballFacilityDTO.setFullSize(true);
        footballFacilityDTO.setHeightOfGoal(3);
        footballFacilityDTO.setWidthOfGoal(5);
        footballFacilityDTO.setAccess(true);
        footballFacilityDTO.setField(fieldDTO);
        footballFacilityDTO.setName("test");
        footballFacilityDTO.setPricePerHours(10);

        Response response = client
                .target(BASE_URL)
                .path("facility/football")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(jsonb.toJson(footballFacilityDTO), MediaType.APPLICATION_JSON));
        Assertions.assertEquals(200, response.getStatus());

        response = client
                .target(BASE_URL)
                .path("facility/facilities")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Collection<String> sportsFacilityDTOList = response.readEntity(new GenericType<Collection<String>>() {
        });
        Assertions.assertEquals(21, sportsFacilityDTOList.size());
        Assertions.assertEquals(200, response.getStatus());

        response = client
                .target(BASE_URL)
                .path("facility/" + id.toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();
        Assertions.assertEquals(200, response.getStatus());

        response = client
                .target(BASE_URL)
                .path("facility/facilities")
                .request(MediaType.APPLICATION_JSON)
                .get();
        sportsFacilityDTOList = response.readEntity(new GenericType<Collection<String>>() {
        });
        Assertions.assertEquals(20, sportsFacilityDTOList.size());
        Assertions.assertEquals(200, response.getStatus());
    }

}
