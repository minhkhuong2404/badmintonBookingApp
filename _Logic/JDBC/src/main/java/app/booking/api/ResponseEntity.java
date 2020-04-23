package app.booking.api;

import com.sun.net.httpserver.Headers;
import lombok.AllArgsConstructor;
import lombok.Value;
@AllArgsConstructor
@Value
public class ResponseEntity<T> {

    private final T body;
    private final Headers headers;
    private final StatusCode statusCode;
}