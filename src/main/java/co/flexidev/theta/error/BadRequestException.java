package co.flexidev.theta.error;

import java.util.Collections;

public class BadRequestException extends RuntimeException {
    private Object value;

    public BadRequestException(String message) {
        super(message);
        value = Collections.singletonList(message);
    }

    public BadRequestException(Object value) {
        super();
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
