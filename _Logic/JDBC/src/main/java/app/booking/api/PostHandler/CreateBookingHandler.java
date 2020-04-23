package app.booking.api.PostHandler;

import app.booking.api.Constants;
import app.booking.api.ResponseEntity;
import app.booking.api.StatusCode;
import app.booking.db.SQLStatement;
import app.booking.errors.ApplicationExceptions;
import app.booking.errors.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.Time;


public class CreateBookingHandler extends Handler {
    public CreateBookingHandler(ObjectMapper objectMapper, GlobalExceptionHandler exceptionHandler) {
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

    private ResponseEntity<Response> doPost(InputStream is) throws Exception {
        CreateBookingRequest CBrequest = super.readRequest(is, CreateBookingRequest.class);

        System.out.println(CBrequest);

        String result_code = SQLStatement.createBooking(CBrequest.getPbookingid(),
                Date.valueOf(CBrequest.getPdate()),
                Time.valueOf(CBrequest.getPstarttime()),
                Time.valueOf(CBrequest.getPendtime()),
                CBrequest.getPcityid(),
                CBrequest.getPcenterid(),
                CBrequest.getPcourtid(),
                CBrequest.getPplayerid());

        System.out.println("Database execution with result code: " + result_code);

        Response response = new Response("1");

        return new ResponseEntity<>(response,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }
}