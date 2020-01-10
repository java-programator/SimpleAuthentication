package pl.altkom.web.exception;

public class UnknownUserException extends AuthenticationException {
    public UnknownUserException() {
    }

    public UnknownUserException(String message) {
        super(message);
    }

    public UnknownUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
