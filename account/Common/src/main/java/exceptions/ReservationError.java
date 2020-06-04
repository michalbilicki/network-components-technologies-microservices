package exceptions;

public class ReservationError extends Exception {

    private String message;

    public ReservationError(String message){
        super(message);
    }
}
