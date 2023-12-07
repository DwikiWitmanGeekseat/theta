package co.flexidev.theta.error;

public class ForbiddenRequestException extends RuntimeException {
    public ForbiddenRequestException(String message) {
        super(message);
    }
}
