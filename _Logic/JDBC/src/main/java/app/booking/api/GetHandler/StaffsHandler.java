package app.booking.api.GetHandler;

import app.booking.api.Constants;
import app.booking.api.Handler.Handler;
import app.booking.api.ResponseEntity;
import app.booking.api.StatusCode;
import app.booking.db.CityCenterStaff;

import app.booking.db.JsonConverter;
import app.booking.db.SQLStatement;
import app.booking.errors.ApplicationExceptions;
import app.booking.errors.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;
import com.sun.net.httpserver.HttpExchange;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class StaffsHandler extends Handler {

    public StaffsHandler(ObjectMapper objectMapper, GlobalExceptionHandler exceptionHandler) {
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
        StaffRequest rq = super.readRequest(is, StaffRequest.class);
        ArrayList<CityCenterStaff> ls = SQLStatement.getCityCenterStaffs(rq.getCityid(),rq.getCenterid());
        String a = JsonConverter.convert(ls);
        return new ResponseEntity<>(a,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }


}
