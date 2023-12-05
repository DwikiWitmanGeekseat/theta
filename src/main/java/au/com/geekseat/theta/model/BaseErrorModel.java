package au.com.geekseat.theta.model;

import java.time.ZonedDateTime;

public class BaseErrorModel {
    private ZonedDateTime timestamp = ZonedDateTime.now();
    private Integer status;
    private Object error;
    private String path;

    public BaseErrorModel(Integer status, Object error, String path) {
        this.status = status;
        this.error = error;
        this.path = path;
    }
}
