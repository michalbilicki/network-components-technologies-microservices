package exceptions;

import javax.ws.rs.core.Response;

public class AccountNotFoundException extends AccountException {

    public AccountNotFoundException(String message) {
        super(Response.Status.NOT_FOUND, message);
    }
}
