package app.booking;

import java.sql.Time;

public class Constants {
    // PORT
    public static final int PORT_NUMBER = 8003;

    // URL GET
    public static final String URL_GET_CITY_ALL = "/api/city/all";
    public static final String URL_GET_BOOKING_BY_PLAYER = "/api/booking/player";
    public static final String URL_GET_CITY_SLOT = "/api/city/slot";

    // URL POST
    public static final String URL_BOOKING_CREATE = "/api/booking/create";
    public static final String URL_BOOKING_CANCEL = "/api/booking/cancel";

    // DEFAULT ACTIVE HOUR
    public static final Time DEFAULT_START_TIME = Time.valueOf("07:00:00");
    public static final Time DEFAULT_END_TIME = Time.valueOf("21:00:00");
    public static final Long DEFAULT_MIN_TIME = Time.valueOf("00:45:00").getTime() - Time.valueOf("00:00:00").getTime();

}
