package app.booking.api.GetHandler;

import app.booking.api.Constants;
import app.booking.api.ResponseEntity;
import app.booking.api.StatusCode;
import app.booking.db.Booking;
import app.booking.db.JsonConverter;
import app.booking.db.SQLStatement;
import app.booking.errors.ApplicationExceptions;
import app.booking.errors.GlobalExceptionHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerBookingHandler extends GetHandler {

    public PlayerBookingHandler(GlobalExceptionHandler exceptionHandler) {
        super(exceptionHandler);
    }

    @Override
    protected void execute(HttpExchange exchange) throws Exception {
        String response;
        if ("GET".equals(exchange.getRequestMethod())) {
            ResponseEntity e = doGet(exchange.getRequestBody());
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

    private ResponseEntity doGet(InputStream is) throws Exception {

        Map<String, List<String>> params = this.getParameters();
        String playerid = params.get("playerid").get(0);

        // TODO: handle the case of missing/incorrect params

        ArrayList<Booking> ls = SQLStatement.getPlayerBookings(playerid);

        String rsp = JsonConverter.convert(ls);
        return new ResponseEntity<>(rsp, getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }
}