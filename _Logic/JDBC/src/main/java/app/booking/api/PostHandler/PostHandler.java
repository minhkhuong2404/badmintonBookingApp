package app.booking.api.PostHandler;

import app.booking.api.ResponseEntity;
import app.booking.errors.ApplicationExceptions;
import app.booking.errors.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import io.vavr.control.Try;

import java.io.InputStream;
import java.io.OutputStream;


public abstract class PostHandler {
    private final ObjectMapper objectMapper;
    private final GlobalExceptionHandler exceptionHandler;

    public PostHandler(ObjectMapper objectMapper, GlobalExceptionHandler exceptionHandler){
        this.objectMapper = objectMapper;
        this.exceptionHandler = exceptionHandler;
    }

    protected <T> T readRequest(InputStream is, Class<T> type) {
        // InputStream has content of json
        // Map InputStream to object "type" using Jackson ObjectMapper
        return Try.of(() -> objectMapper.readValue(is, type))
                .getOrElseThrow(ApplicationExceptions.invalidRequest());
    }

    public void handle(HttpExchange exchange) {
        Try.run(() -> execute(exchange))
                .onFailure(thr -> exceptionHandler.handle(thr, exchange));
    }

    private void execute(HttpExchange exchange) throws Exception {
        String response ;
        if ("POST".equals(exchange.getRequestMethod())) {
            ResponseEntity e = doPost(exchange.getRequestBody());
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getStatusCode().getCode(), 0);
            response = (String) e.getBody();
        } else {
            throw ApplicationExceptions.methodNotAllowed(
                    "Method " + exchange.getRequestMethod() + " is not allowed for " + exchange.getRequestURI()).get();
        }

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    protected abstract ResponseEntity<String> doPost(InputStream is) throws Exception;

    protected <T> byte[] writeResponse(T response) {
        return Try.of(() -> objectMapper.writeValueAsBytes(response))
                .getOrElseThrow(ApplicationExceptions.invalidRequest());
    }

    protected static Headers getHeaders(String key, String value) {
        Headers headers = new Headers();
        headers.set(key, value);
        return headers;
    }
}
