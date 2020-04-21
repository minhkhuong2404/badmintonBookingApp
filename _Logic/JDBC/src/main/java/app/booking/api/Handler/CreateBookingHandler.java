package app.booking.api.Handler;

import app.booking.api.Constants;
import app.booking.api.ResponseEntity;
import app.booking.api.StatusCode;
import app.booking.database.MySQLAccess;
import app.booking.errors.ApplicationExceptions;
import app.booking.errors.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class CreateBookingHandler extends Handler {
    public CreateBookingHandler(ObjectMapper objectMapper, GlobalExceptionHandler exceptionHandler) {
        super(objectMapper, exceptionHandler);
    }

    @Override
    protected void execute(HttpExchange exchange) throws IOException {
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

    private ResponseEntity<Response> doPost(InputStream is) {
        CreateBookingRequest CBrequest = super.readRequest(is, CreateBookingRequest.class);

        MySQLAccess.create_booking(CBrequest.getPbookingid(),CBrequest.getPtimestamp(), CBrequest.getPdate(), CBrequest.getPendtime(),
                CBrequest.getPendtime(),CBrequest.getPcityid(),CBrequest.getPcenterid(),CBrequest.getPcourtid(), CBrequest.getPplayerid());

        Response response = new Response("1");

        return new ResponseEntity<>(response,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }


}
