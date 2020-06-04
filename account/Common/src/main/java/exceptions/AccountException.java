package exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class AccountException extends WebApplicationException {

    public AccountException(){
        super(Response.Status.BAD_REQUEST);
    }

    public AccountException(String message){
        super(Response.status((Response.Status.BAD_REQUEST)).entity(message).build());
    }

    public AccountException(Response.Status status, String message){
        super(Response.status(status).entity(message).build());
    }
}
