import Model.AccountDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class AccountServiceTest extends AbstractContainerBase {

    @Test
    public void getAllAccountsTest() {
        Response response = client
                .target(BASE_URL)
                .path("account/accounts")
                .request(MediaType.APPLICATION_JSON)
                .get();


        Collection<AccountDto> accountDTOList = response
                .readEntity(new GenericType<Collection<AccountDto>>() {
                });

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(24, accountDTOList.size());
    }

    @Test
    public void getAccountTest() {
        Response response = client
                .target(BASE_URL)
                .path("account/1")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assertions.assertEquals(400, response.getStatus());

        response = client
                .target(BASE_URL)
                .path("account/accounts")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Collection<AccountDto> accountDTOList = response
                .readEntity(new GenericType<Collection<AccountDto>>() {
                });
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(24, accountDTOList.size());

        AccountDto accountDTO = accountDTOList.toArray(new AccountDto[0])[0];
        response = client
                .target(BASE_URL)
                .path("account/" + accountDTO.getId())
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assertions.assertEquals(200, response.getStatus());
        AccountDto getAccount = response.readEntity(AccountDto.class);
        Assertions.assertEquals(accountDTO, getAccount);
    }

    @Test
    public void blockAccountTest() {
        Response response = client
                .target(BASE_URL)
                .path("account/block")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity("1", MediaType.APPLICATION_JSON));
        Assertions.assertEquals(400, response.getStatus());

        response = client
                .target(BASE_URL)
                .path("account/accounts")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Collection<AccountDto> accountDTOList = response
                .readEntity(new GenericType<Collection<AccountDto>>() {
                });
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(24, accountDTOList.size());

        AccountDto accountDTO = accountDTOList.toArray(new AccountDto[0])[0];
        response = client
                .target(BASE_URL)
                .path("account/block")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(accountDTO.getId(), MediaType.APPLICATION_JSON));
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void unblockAccountTest() {
        Response response = client
                .target(BASE_URL)
                .path("account/block")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity("1", MediaType.APPLICATION_JSON));
        Assertions.assertEquals(400, response.getStatus());

        response = client
                .target(BASE_URL)
                .path("account/accounts")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Collection<AccountDto> accountDTOList = response
                .readEntity(new GenericType<Collection<AccountDto>>() {
                });
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(24, accountDTOList.size());

        AccountDto accountDTO = accountDTOList.toArray(new AccountDto[0])[0];
        response = client
                .target(BASE_URL)
                .path("account/block")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(accountDTO.getId(), MediaType.APPLICATION_JSON));
        Assertions.assertEquals(200, response.getStatus());

        response = client
                .target(BASE_URL)
                .path("account/unblock")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(accountDTO.getId(), MediaType.APPLICATION_JSON));
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void updateAccountTest() {
        Response response = client
                .target(BASE_URL)
                .path("account/accounts")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Collection<AccountDto> accountDTOList = response
                .readEntity(new GenericType<Collection<AccountDto>>() {
                });

        AccountDto accountDTO = accountDTOList.toArray(new AccountDto[0])[0];
        accountDTO.setPassword("test");

        response = client
                .target(BASE_URL)
                .path("account/update")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(jsonb.toJson(accountDTO), MediaType.APPLICATION_JSON));
        Assertions.assertEquals(200, response.getStatus());

        response = client
                .target(BASE_URL)
                .path("account/" + accountDTO.getId())
                .request(MediaType.APPLICATION_JSON)
                .get();
        AccountDto getAccount = response.readEntity(AccountDto.class);
        Assertions.assertEquals(accountDTO, getAccount);
    }

    @Test
    public void addAccountTest() {
        String id = UUID.randomUUID().toString();
        AccountDto toSendAccountDTO = new AccountDto(
                id,
                "testLogin",
                "testPass",
                "TestName TestSurname",
                true,
                new ArrayList<String>(),
                null
        );

        Response response = client
                .target(BASE_URL)
                .path("account/add")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(jsonb.toJson(toSendAccountDTO), MediaType.APPLICATION_JSON));
        Assertions.assertEquals(200, response.getStatus());

        response = client
                .target(BASE_URL)
                .path("account/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get();

        AccountDto resultAccountDTO = response.readEntity(AccountDto.class);
        Assertions.assertEquals(toSendAccountDTO, resultAccountDTO);
    }

    @Test
    public void deleteAccountTest() {
        String id = UUID.randomUUID().toString();
        AccountDto toSendAccountDTO = new AccountDto(
                id,
                "testLogin",
                "testPass",
                "TestName TestSurname",
                true,
                new ArrayList<String>(),
                null
        );

        Response response = client
                .target(BASE_URL)
                .path("account/add")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(jsonb.toJson(toSendAccountDTO), MediaType.APPLICATION_JSON));
        Assertions.assertEquals(200, response.getStatus());

        response = client
                .target(BASE_URL)
                .path("account/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get();

        AccountDto resultAccountDTO = response.readEntity(AccountDto.class);
        Assertions.assertEquals(toSendAccountDTO, resultAccountDTO);

        response = client
                .target(BASE_URL)
                .path("account/delete/" + id)
                .request(MediaType.APPLICATION_JSON)
                .delete();
        Assertions.assertEquals(200, response.getStatus());
    }
}