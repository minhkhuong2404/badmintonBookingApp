package app.booking.api.PostHandler;

import app.booking.api.Constants;
import app.booking.api.ResponseEntity;
import app.booking.api.StatusCode;
import app.booking.db.SQLStatement;
import app.booking.errors.ApplicationExceptions;
import app.booking.errors.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import io.vavr.control.Try;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.Time;


public class CreateBookingPostHandler extends PostHandler {
    public CreateBookingPostHandler(ObjectMapper objectMapper, GlobalExceptionHandler exceptionHandler) {
        super(objectMapper, exceptionHandler);
    }

    @Override
    protected void execute(HttpExchange exchange) throws Exception {
        byte[] response ;
        if ("POST".equals(exchange.getRequestMethod())) {
            ResponseEntity e = doPost(exchange.getRequestBody());
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

    private ResponseEntity<String> doPost(InputStream is) throws Exception {
        CreateBookingRequest CBrequest = super.readRequest(is, CreateBookingRequest.class);

        String result_code = SQLStatement.createBooking(
                Date.valueOf(CBrequest.getPdate()),
                Time.valueOf(CBrequest.getPstarttime()),
                Time.valueOf(CBrequest.getPendtime()),
                CBrequest.getPcityid(),
                CBrequest.getPcenterid(),
                CBrequest.getPcourtid(),
                CBrequest.getPplayerid());
        String response;
        if (result_code == "200") {
            response = "Created. Booking info: " + CBrequest.toString();
        } else response = "Create Failed. Error code: " + result_code;

        return new ResponseEntity<>(response, getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }
}