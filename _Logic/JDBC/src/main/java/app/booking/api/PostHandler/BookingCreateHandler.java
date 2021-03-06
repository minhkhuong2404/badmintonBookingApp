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


public class BookingCreateHandler extends PostHandler {
    public BookingCreateHandler(ObjectMapper objectMapper, GlobalExceptionHandler exceptionHandler) {
        super(objectMapper, exceptionHandler);
    }

    @Override
    protected ResponseEntity<String> doPost(InputStream is) throws Exception {
        BookingCreateRequest CBrequest = super.readRequest(is, BookingCreateRequest.class);

        String result_code = SQLStatement.createBooking(
                Date.valueOf(CBrequest.getPdate()),
                Time.valueOf(CBrequest.getPstarttime()),
                Time.valueOf(CBrequest.getPendtime()),
                CBrequest.getPcityid(),
                CBrequest.getPcenterid(),
                CBrequest.getPcourtid(),
                CBrequest.getPplayerid(),
                CBrequest.getCardid());

        System.out.println("Create Booking executed with result code: " + result_code);

        // preparing response to client
        PostResponse postResponse = new PostResponse(result_code);
        String rsp = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(postResponse);

        return new ResponseEntity<>(rsp, getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }
}