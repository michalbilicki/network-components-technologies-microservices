package exception;

import javax.ws.rs.core.Response;

public class ClientNotFoundException extends ClientException {

    public ClientNotFoundException(String message) {
        super(Response.Status.NOT_FOUND, message);
    }
}
