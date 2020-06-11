package exception;

import javax.ws.rs.core.Response;

public class SportsFacilityNotFoundException extends SportsFacilityException {

    public SportsFacilityNotFoundException(String message) {
        super(Response.Status.NOT_FOUND, message);
    }
}
