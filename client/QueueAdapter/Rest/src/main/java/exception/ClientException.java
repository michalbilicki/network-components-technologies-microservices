package exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ClientException extends WebApplicationException {

    public ClientException() {
        super(Response.Status.BAD_REQUEST);
    }

    public ClientException(String message) {
        super(Response.status((Response.Status.BAD_REQUEST)).entity(message).build());
    }

    public ClientException(Response.Status status, String message) {
        super(Response.status(status).entity(message).build());
    }
}
