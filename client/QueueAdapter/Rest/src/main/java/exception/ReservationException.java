package exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ReservationException extends WebApplicationException {

    public ReservationException() {
        super(Response.Status.BAD_REQUEST);
    }

    public ReservationException(String message) {
        super(Response.status(Response.Status.BAD_REQUEST).entity(message).build());
    }

    public ReservationException(Response.Status status, String message) {
        super(Response.status(status).entity(message).build());
    }
}
