package app.booking.api.GetHandler;

import app.booking.api.Constants;
import app.booking.api.ResponseEntity;
import app.booking.api.StatusCode;
import app.booking.db.Booking;
import app.booking.api.JsonConverter;
import app.booking.db.SQLStatement;
import app.booking.errors.ApplicationExceptions;
import app.booking.errors.GlobalExceptionHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerBookingHandler extends GetHandler {

    public PlayerBookingHandler(GlobalExceptionHandler exceptionHandler) {
        super(exceptionHandler);
    }

    @Override
    protected ResponseEntity doGet(InputStream is) throws Exception {

        Map<String, List<String>> params = this.getParameters();
        String playerid = params.get("id").get(0);
        String cityid = params.get("cityid").get(0);
        Date date = Date.valueOf(params.get("date").get(0));

        // TODO: handle the case of missing/incorrect params

        ArrayList<Booking> ls = SQLStatement.getPlayerBookings(playerid, cityid, date);

        String rsp = JsonConverter.toJson(ls);
        return new ResponseEntity<>(rsp, getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }
}
