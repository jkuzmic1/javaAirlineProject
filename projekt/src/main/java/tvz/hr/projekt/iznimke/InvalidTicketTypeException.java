package tvz.hr.projekt.iznimke;

public class InvalidTicketTypeException extends Exception {
    public InvalidTicketTypeException(String message) {
        super(message);
    }
}
