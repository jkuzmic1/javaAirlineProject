package tvz.hr.projekt.iznimke;

public class NotEnoughTickets extends Exception{
    public NotEnoughTickets() {
    }

    public NotEnoughTickets(String message) {
        super(message);
    }

    public NotEnoughTickets(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughTickets(Throwable cause) {
        super(cause);
    }

    public NotEnoughTickets(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
