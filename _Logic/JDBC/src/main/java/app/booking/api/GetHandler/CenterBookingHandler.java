package app.booking.api.GetHandler;

import app.booking.api.Constants;
import app.booking.api.PostHandler.Handler;
import app.booking.api.ResponseEntity;
import app.booking.api.StatusCode;
import app.booking.db.Booking;
import app.booking.db.CityCenterCourt;
import app.booking.db.JsonConverter;
import app.booking.db.SQLStatement;
import app.booking.errors.ApplicationExceptions;
import app.booking.errors.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CenterBookingHandler extends GetHandler {

    public CenterBookingHandler(ObjectMapper objectMapper, GlobalExceptionHandler exceptionHandler) {
        super(objectMapper, exceptionHandler);
    }

    @Override
    protected void execute(HttpExchange exchange) throws Exception {
        byte [] response;
        if ("GET".equals(exchange.getRequestMethod())) {
            ResponseEntity e = doGet(exchange.getRequestBody());
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getStatusCode().getCode(), 0);
            response = super.writeResponse(e.getBody());
        } else {
            throw ApplicationExceptions.methodNotAllowed(
                    "Method " + exchange.getRequestMethod() + " is not allowed for " + exchange.getRequestURI()).get();
        }

        OutputStream os = exchange.getResponseBody();
        os.write(response);
        os.close();
    }

    private ResponseEntity doGet(InputStream is) throws Exception {
//        CenterBookingRequest rqs = super.readRequest(is, CenterBookingRequest.class);
//        ArrayList<Booking> ls = SQLStatement.getCenterBookings(rqs.getCenterid());
        Map<String, List<String>> params = this.getParameters();
        String centerid = params.get("centerid").get(0);
        ArrayList<Booking> ls = SQLStatement.getCenterBookings(centerid);
        String rsp = JsonConverter.convert(ls);
        return new ResponseEntity<>(rsp,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }
}
