package app.booking.api.GetHandler;

import app.booking.api.Constants;
import app.booking.api.ResponseEntity;
import app.booking.api.StatusCode;
import app.booking.db.Booking;
import app.booking.db.SQLStatement;
import app.booking.errors.ApplicationExceptions;
import app.booking.errors.GlobalExceptionHandler;
import app.booking.slot.CitySlot;
import app.booking.slot.Slot;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CitySlotHandler extends GetHandler{
    public CitySlotHandler(GlobalExceptionHandler exceptionHandler) {
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
        String cityId = params.get("id").get(0);
        Date date = Date.valueOf(params.get("date").get(0));
        // TODO: handle the case of missing/incorrect params

        // Get bookings of city
        ArrayList<Booking> bookingArrayList = SQLStatement.getCityBookings(cityId, date);

        // Get city slot
        CitySlot citySlot = new CitySlot(cityId, bookingArrayList);
        HashMap<String, HashMap<String, ArrayList<Slot>>> slotMap = citySlot.getCitySlot();

        String rsp = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(slotMap);

        return new ResponseEntity<>(rsp, getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }
}