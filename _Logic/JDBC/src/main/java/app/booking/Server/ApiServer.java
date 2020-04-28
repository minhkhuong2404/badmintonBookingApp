package app.booking.Server;

import app.booking.api.GetHandler.*;
import app.booking.api.PostHandler.BookingCancelHandler;
import app.booking.api.PostHandler.BookingCreateHandler;
import app.booking.db.Center;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;


import java.net.InetSocketAddress;
import java.io.IOException;

import static app.booking.Server.Configuration.getErrorHandler;
import static app.booking.Server.Configuration.getObjectMapper;

public class ApiServer {
    public static void main(String[] args) throws IOException {

        // Create http server
        HttpServer server = HttpServer.create(new InetSocketAddress(Constants.PORT_NUMBER), 0);


        // GET Context

        // Get all cities
        CitiesHandler citiesHandler = new CitiesHandler(getErrorHandler());
        HttpContext getCities = server.createContext(Constants.URL_GET_CITY_ALL, citiesHandler::handle);

        // Get player's bookings
        PlayerBookingHandler playerBookingHandler = new PlayerBookingHandler(getErrorHandler());
        HttpContext getPlayerBookings = server.createContext(Constants.URL_GET_BOOKING_BY_PLAYER,playerBookingHandler::handle);

        // Get city slots
        CitySlotHandler citySlotHanler = new CitySlotHandler(getErrorHandler());
        HttpContext getCitySlots = server.createContext(Constants.URL_GET_CITY_SLOT,citySlotHanler::handle);


        // POST Context

        // Create booking
        BookingCreateHandler bookingCreateHandler = new BookingCreateHandler(getObjectMapper(), getErrorHandler());
        server.createContext(Constants.URL_BOOKING_CREATE, bookingCreateHandler::handle);

        // TODO: Cancel Booking
        BookingCancelHandler bookingCancelHandler = new BookingCancelHandler(getObjectMapper(), getErrorHandler());
        server.createContext(Constants.URL_BOOKING_CANCEL, bookingCancelHandler::handle);

        CenterActiveHourHandler centerActiveHourHandler = new CenterActiveHourHandler(getErrorHandler());
        HttpContext getCenterActiveHour = server.createContext(Constants.URL_GET_CENTER_ACTIVE_HOUR, centerActiveHourHandler::handle);

        CenterHolidayHandler centerHolidayHandler = new CenterHolidayHandler(getErrorHandler());
        HttpContext getCenterHoliday = server.createContext(Constants.URL_GET_CENTER_HOLIDAY, centerHolidayHandler::handle);


//        // POST Context
//        CreateBookingPostHandler createBookingHandler = new CreateBookingPostHandler(getObjectMapper(), getErrorHandler());
//        server.createContext("/api/booking/create/", createBookingHandler::handle);
//
//        // GET context
//
//        UnusedCityCenterStaffsHandler unusedCityCenterStaffsHandler = new UnusedCityCenterStaffsHandler(getErrorHandler());
//        HttpContext context1 =server.createContext("/api/staff/view", unusedCityCenterStaffsHandler::handle);
//
//        UnusedStaffHandler unusedStaffHandler = new UnusedStaffHandler(getErrorHandler());
//        HttpContext context2 =server.createContext("/api/staff/view2", unusedStaffHandler::handle);
//
//        UnusedCityCenterHandler unusedCityCenterHandler = new UnusedCityCenterHandler(getErrorHandler());
//        HttpContext context3 = server.createContext("/api/city/view", unusedCityCenterHandler::handle);
//
//        CitiesHandler citiesHandler = new CitiesHandler(getErrorHandler());
//        HttpContext context4 = server.createContext("/api/city/view2",citiesHandler::handle);
//
//        UnusedCityCenterCourtsHandler unusedCityCenterCourtsHandler = new UnusedCityCenterCourtsHandler(getErrorHandler());
//        HttpContext context5 = server.createContext("/api/city/view3", unusedCityCenterCourtsHandler::handle);
//
//        UnusedCenterBookingHandler unusedCenterBookingHandler = new UnusedCenterBookingHandler(getErrorHandler());
//        HttpContext context6 = server.createContext("/api/booking/view", unusedCenterBookingHandler::handle);
//
//        PlayerBookingHandler playerBookingHandler = new PlayerBookingHandler(getErrorHandler());
//        HttpContext context7 = server.createContext("/api/booking/view2",playerBookingHandler::handle);
//
//        CitySlotHandler citySlotHanler = new CitySlotHandler(getErrorHandler());
//        HttpContext context8 = server.createContext("/api/slot/city",citySlotHanler::handle);


        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
