package app.booking.Server;

import app.booking.api.GetHandler.*;
import app.booking.api.PostHandler.CreateBookingPostHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;


import java.net.InetSocketAddress;
import java.io.IOException;

import static app.booking.Server.Configuration.getErrorHandler;
import static app.booking.Server.Configuration.getObjectMapper;

public class ApiServer {
    public static void main(String[] args) throws IOException {
        int serverPort = 8003;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

        CreateBookingPostHandler createBookingHandler = new CreateBookingPostHandler(getObjectMapper(), getErrorHandler());
        server.createContext("/api/booking/create/", createBookingHandler::handle);

        //Get context

        UnusedCityCenterStaffsHandler unusedCityCenterStaffsHandler = new UnusedCityCenterStaffsHandler(getErrorHandler());
        HttpContext context1 =server.createContext("/api/staff/view", unusedCityCenterStaffsHandler::handle);

        UnusedStaffHandler unusedStaffHandler = new UnusedStaffHandler(getErrorHandler());
        HttpContext context2 =server.createContext("/api/staff/view2", unusedStaffHandler::handle);

        UnusedCityCenterHandler unusedCityCenterHandler = new UnusedCityCenterHandler(getErrorHandler());
        HttpContext context3 = server.createContext("/api/city/view", unusedCityCenterHandler::handle);

        CitiesHandler citiesHandler = new CitiesHandler(getErrorHandler());
        HttpContext context4 = server.createContext("/api/city/view2",citiesHandler::handle);

        UnusedCityCenterCourtsHandler unusedCityCenterCourtsHandler = new UnusedCityCenterCourtsHandler(getErrorHandler());
        HttpContext context5 = server.createContext("/api/city/view3", unusedCityCenterCourtsHandler::handle);

        UnusedCenterBookingHandler unusedCenterBookingHandler = new UnusedCenterBookingHandler(getErrorHandler());
        HttpContext context6 = server.createContext("/api/booking/view", unusedCenterBookingHandler::handle);

        PlayerBookingHandler playerBookingHandler = new PlayerBookingHandler(getErrorHandler());
        HttpContext context7 = server.createContext("/api/booking/view2",playerBookingHandler::handle);

        CitySlotHandler citySlotHanler = new CitySlotHandler(getErrorHandler());
        HttpContext context8 = server.createContext("/api/slot/city",citySlotHanler::handle);


        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
