package tvz.hr.projekt.iznimke;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import static tvz.hr.projekt.entiteti.Flight.logger;

public class DeserijalizacijaEOFException extends Throwable {
    public DeserijalizacijaEOFException() {
    }

    public DeserijalizacijaEOFException(String message) {
        super(message);
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.show();
        logger.error(message);
    }

    public DeserijalizacijaEOFException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeserijalizacijaEOFException(Throwable cause) {
        super(cause);
    }

    public DeserijalizacijaEOFException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
