package app.booking.api.GetHandler;


import app.booking.api.ApiUtils;
import app.booking.errors.ApplicationExceptions;
import app.booking.errors.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import io.vavr.control.Try;

import java.io.InputStream;
import java.util.List;
import java.util.Map;


public abstract class GetHandler{
    private final ObjectMapper objectMapper;
    private final GlobalExceptionHandler exceptionHandler;

    private Map<String, List<String>> parameters;

    public Map<String, List<String>> getParameters() {
        return parameters;
    }

    public GetHandler(ObjectMapper objectMapper, GlobalExceptionHandler exceptionHandler){
        this.objectMapper = objectMapper;
        this.exceptionHandler = exceptionHandler;
    }

    protected <T> T readRequest(InputStream is, Class<T> type) {
        // if request has body of json, map it to object
        return Try.of(() -> objectMapper.readValue(is, type))
                .getOrElseThrow(ApplicationExceptions.invalidRequest());
    }

    public void handle(HttpExchange exchange) {
        // get and map URL parameters
        parameters = ApiUtils.splitQuery(exchange.getRequestURI().getRawQuery());

        Try.run(() -> execute(exchange))
                .onFailure(thr -> exceptionHandler.handle(thr, exchange));
    }

    protected abstract void execute(HttpExchange exchange) throws Exception;

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

