package exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class SportsFacilityException extends WebApplicationException {

    public SportsFacilityException(){
        super(Response.Status.BAD_REQUEST);
    }

    public SportsFacilityException(String message){
        super(Response.status((Response.Status.BAD_REQUEST)).entity(message).build());
    }

    public SportsFacilityException(Response.Status status, String message){
        super(Response.status(status).entity(message).build());
    }

}
