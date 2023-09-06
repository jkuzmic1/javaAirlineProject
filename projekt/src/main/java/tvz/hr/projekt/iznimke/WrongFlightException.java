package tvz.hr.projekt.iznimke;

public class WrongFlightException extends Exception{

    public WrongFlightException(){
        super("Flight initialized incorrectly!");

    }

    public WrongFlightException(String message) {
        super(message);
    }

    public WrongFlightException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongFlightException(Throwable cause) {
        super(cause);
    }

    public WrongFlightException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
