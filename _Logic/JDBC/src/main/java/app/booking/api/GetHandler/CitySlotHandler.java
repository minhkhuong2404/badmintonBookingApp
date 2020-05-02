package app.booking.api.GetHandler;

import app.booking.api.Constants;
import app.booking.api.JsonConverter;
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

public class CitySlotHandler extends GetHandler {
    public CitySlotHandler(GlobalExceptionHandler exceptionHandler) {
        super(exceptionHandler);
    }

    @Override
    protected ResponseEntity doGet(InputStream is) throws Exception {
        Map<String, List<String>> params = this.getParameters();
        String cityId = params.get("id").get(0);
        Date date = Date.valueOf(params.get("date").get(0));
        // TODO: handle the case of missing/incorrect params

        // Get bookings of city
        ArrayList<Booking> cityBookings = SQLStatement.getCityBookings(cityId, date);
        // Get city slot
        CitySlot citySlot = new CitySlot(cityId, cityBookings);
        // Convert to json
        String response = JsonConverter.toJson(citySlot);

        return new ResponseEntity<>(response, getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }
}