package pl.altkom.web.exception;

public class UsernameAlreadyTakenException extends AuthenticationException {
    public UsernameAlreadyTakenException() {
    }

    public UsernameAlreadyTakenException(String message) {
        super(message);
    }

    public UsernameAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }
}
