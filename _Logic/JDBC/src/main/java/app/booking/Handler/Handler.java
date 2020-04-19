package app.booking.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import io.vavr.control.Try;

import java.io.InputStream;


public abstract class Handler {
    private final ObjectMapper objectMapper;

    public Handler(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    protected <T> T readRequest(InputStream is, Class<T> type) {
        return Try.of(() -> objectMapper.readValue(is, type)).getOrElseThrow(throwable -> {
            throw new RuntimeException(throwable);
        });
    }

    public void handle(HttpExchange exchange) {
        Try.run(() -> execute(exchange));
    }

    protected abstract void execute(HttpExchange exchange) throws Exception;
}
