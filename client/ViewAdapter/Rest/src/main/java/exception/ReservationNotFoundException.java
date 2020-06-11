package exception;

import javax.ws.rs.core.Response;

public class ReservationNotFoundException extends ReservationException {

    public ReservationNotFoundException(String message){
        super(Response.Status.NOT_FOUND, message);
    }
}
