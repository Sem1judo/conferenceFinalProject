package ua.com.semkov.exceptions;

public class EntityNotFoundDAOException extends DAOException {

    public EntityNotFoundDAOException() {
    }

    public EntityNotFoundDAOException(String message) {
        super(message);
    }

    public EntityNotFoundDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundDAOException(Throwable cause) {
        super(cause);
    }
}
