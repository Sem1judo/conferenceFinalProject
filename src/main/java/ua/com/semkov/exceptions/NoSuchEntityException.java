package ua.com.semkov.exceptions;

public class NoSuchEntityException extends ServiceException {
    public NoSuchEntityException() {
    }

    public NoSuchEntityException(String message) {
        super(message);
    }

    public NoSuchEntityException(Exception ex) {
        super(ex);
    }

    public NoSuchEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}

