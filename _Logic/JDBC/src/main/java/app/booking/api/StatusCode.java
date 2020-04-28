package app.booking.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {
    OK(200),
    CREATED(201),
    ACCEPTED(202),
    NOT_FOUND(404),
    BAD_REQUEST(400),
    METHOD_NOT_ALLOWED(405);

    private int code;
}