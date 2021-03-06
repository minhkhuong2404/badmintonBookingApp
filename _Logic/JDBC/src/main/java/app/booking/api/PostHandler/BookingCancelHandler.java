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


public class BookingCancelHandler extends PostHandler {
    public BookingCancelHandler(ObjectMapper objectMapper, GlobalExceptionHandler exceptionHandler) {
        super(objectMapper, exceptionHandler);
    }

    @Override
    protected ResponseEntity<String> doPost(InputStream is) throws Exception {
        BookingCancelRequest cancelRequest = super.readRequest(is, BookingCancelRequest.class);

        String result_code = SQLStatement.cancelBooking(
                cancelRequest.getId(),
                cancelRequest.getPlayerid());
        System.out.println("CancelBooking() executed with result code: " + result_code);

        // preparing response to client
        PostResponse postResponse = new PostResponse(result_code);
        String rsp = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(postResponse);

        return new ResponseEntity<>(rsp, getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.CREATED);
    }
}