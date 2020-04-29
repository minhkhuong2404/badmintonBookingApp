package app.booking.Server;

import app.booking.Constants;
import app.booking.api.GetHandler.*;
import app.booking.api.PostHandler.BookingCancelHandler;
import app.booking.api.PostHandler.BookingCreateHandler;
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

        // Cancel Booking
        BookingCancelHandler bookingCancelHandler = new BookingCancelHandler(getObjectMapper(), getErrorHandler());
        server.createContext(Constants.URL_BOOKING_CANCEL, bookingCancelHandler::handle);

        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
