package tvz.hr.projekt.iznimke;

import static tvz.hr.projekt.entiteti.Flight.logger;

public class DataSourceException extends Exception{

    public DataSourceException() {
        logger.error("Data source exception ocurred!");
    }

    public DataSourceException(String message) {
        super(message);
    }

    public DataSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSourceException(Throwable cause) {
        super(cause);
    }

    public DataSourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
