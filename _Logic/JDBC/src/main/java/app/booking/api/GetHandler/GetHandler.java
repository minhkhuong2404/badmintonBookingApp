package app.booking.api.GetHandler;

import app.booking.api.ApiUtils;
import app.booking.errors.GlobalExceptionHandler;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import io.vavr.control.Try;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public abstract class GetHandler {
    private final GlobalExceptionHandler exceptionHandler;
    private Map<String, List<String>> parameters;

    protected abstract void execute(HttpExchange exchange) throws Exception;

    public Map<String, List<String>> getParameters() {
        return parameters;
    }

    public GetHandler(GlobalExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public void handle(HttpExchange exchange) {
        // get and map URL parameters
        parameters = ApiUtils.splitQuery(exchange.getRequestURI().getRawQuery());

        Try.run(() -> execute(exchange))
                .onFailure(thr -> exceptionHandler.handle(thr, exchange));
    }

    protected static Headers getHeaders(String key, String value) {
        Headers headers = new Headers();
        headers.set(key, value);
        return headers;
    }
}

