package tvz.hr.projekt.iznimke;

import static tvz.hr.projekt.entiteti.Flight.logger;

public class NedozvoljenaPromjenaException extends Exception{
    public NedozvoljenaPromjenaException() {
        logger.error("Nedozvoljena promjena exception");
    }

    public NedozvoljenaPromjenaException(String message) {
        super(message);
        logger.error("Nedozvoljena promjena exception");
    }

    public NedozvoljenaPromjenaException(String message, Throwable cause) {
        super(message, cause);
        logger.error("Nedozvoljena promjena exception");
    }

    public NedozvoljenaPromjenaException(Throwable cause) {
        super(cause);
        logger.error("Nedozvoljena promjena exception");
    }

    public NedozvoljenaPromjenaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error("Nedozvoljena promjena exception");
    }
}
