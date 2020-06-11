package Endpoints;


import Model.AccountDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("account")
public class AccountEndpoint {

    //    @Inject
//    private AccountServiceUseCase accountServiceUseCase;
//
    @POST
    @Path("add")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addAccount(AccountDto accountDto) {
        return Response.ok().build();
//        ViewAccountConverter viewAccountConverter = new ViewAccountConverter();
//        if (accountServiceUseCase.addAccount(viewAccountConverter.convertFrom(accountDto))) {
//            return Response.ok().build();
//        } else {
//            throw new AccountException("DomainModel.Account is not added!");
//        }
    }
//
//    @DELETE
//    @Path("delete/{id}")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response removeAccount(@PathParam("id") String id) {
//        try {
//            if (accountServiceUseCase.removeAccount(UUID.fromString(id))) {
//                return Response.ok().build();
//            } else {
//                throw new AccountException("DomainModel.Account is not removed!");
//            }
//        } catch (UnsupportedOperationException | IllegalArgumentException e) {
//            throw new AccountException("Wrong id");
//        }
//    }
//
//    @PUT
//    @Path("update")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response updateAccount(AccountDto accountDto) {
//        ViewAccountConverter viewAccountConverter = new ViewAccountConverter();
//        if (accountServiceUseCase.updateAccount(viewAccountConverter.convertFrom(accountDto))) {
//            return Response.ok().build();
//        } else {
//            throw new AccountException("DomainModel.Account is not updated!");
//        }
//    }
//
//    @PUT
//    @Path("block")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response blockAccount(String id) {
//        try {
//            if (accountServiceUseCase.blockUser(UUID.fromString(id))) {
//                return Response.ok().build();
//            } else {
//                throw new AccountException("DomainModel.Account is not blocked!");
//            }
//        } catch (UnsupportedOperationException | IllegalArgumentException e) {
//            throw new AccountException("Wrong id");
//        }
//    }
//
//    @PUT
//    @Path("unblock")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response unblockAccount(String id) {
//        try {
//            if (accountServiceUseCase.unblockUser(UUID.fromString(id))) {
//                return Response.ok().build();
//            } else {
//                throw new AccountException("DomainModel.Account is still blocked!");
//            }
//        } catch (UnsupportedOperationException | IllegalArgumentException e) {
//            throw new AccountException("Wrong id");
//        }
//    }
//
//    @GET
//    @Path("{id}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public AccountDto getAccount(@PathParam("id") String id) {
//        try {
//            ViewAccountConverter viewAccountConverter = new ViewAccountConverter();
//            AccountDto AccountDto = viewAccountConverter.convertTo(accountServiceUseCase.getAccount(UUID.fromString(id)));
//            if (AccountDto == null) {
//                throw new AccountNotFoundException("DomainModel.Account with such id does not exist");
//            } else {
//                return AccountDto;
//            }
//        } catch (UnsupportedOperationException | IllegalArgumentException e) {
//            throw new AccountException("Wrong id");
//        }
//    }
//
//    @GET
//    @Path("accounts")
//    @Produces({MediaType.APPLICATION_JSON})
//    public List<AccountDto> getAccounts() {
//        ViewAccountConverter viewAccountConverter = new ViewAccountConverter();
//        return accountServiceUseCase.getAllAccount()
//                .stream()
//                .map(viewAccountConverter::convertTo)
//                .collect(Collectors.toList());
//    }
}
