package exceptions;

public class ViewConverterException extends Exception {
    public ViewConverterException(String msg){
        super("CONVERSION ERROR: " + msg);
    }
}
