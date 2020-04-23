package app.booking.Server;

import app.booking.api.GetHandler.*;
import app.booking.api.PostHandler.CreateBookingHandler;
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

        CreateBookingHandler createBookingHandler = new CreateBookingHandler(getObjectMapper(), getErrorHandler());
        server.createContext("/api/booking/create/", createBookingHandler::handle);

        //Get context

        CityCenterStaffsHandler cityCenterStaffsHandler = new CityCenterStaffsHandler(getObjectMapper(),getErrorHandler());
        HttpContext context1 =server.createContext("/api/staff/view", cityCenterStaffsHandler::handle);

        StaffHandler staffHandler = new StaffHandler(getObjectMapper(),getErrorHandler());
        HttpContext context2 =server.createContext("/api/staff/view2", staffHandler::handle);

        CityCenterHandler cityCenterHandler = new CityCenterHandler(getObjectMapper(),getErrorHandler());
        HttpContext context3 = server.createContext("/api/city/view",cityCenterHandler::handle);

        CitiesHandler citiesHandler = new CitiesHandler(getObjectMapper(),getErrorHandler());
        HttpContext context4 = server.createContext("/api/city/view2",citiesHandler::handle);

        CityCenterCourtsHandler cityCenterCourtsHandler = new CityCenterCourtsHandler(getObjectMapper(),getErrorHandler());
        HttpContext context5 = server.createContext("/api/city/view3",cityCenterCourtsHandler::handle);

        CenterBookingHandler centerBookingHandler = new CenterBookingHandler(getObjectMapper(),getErrorHandler());
        HttpContext context6 = server.createContext("/api/booking/view",centerBookingHandler::handle);

        PlayerBookingHandler playerBookingHandler = new PlayerBookingHandler(getObjectMapper(),getErrorHandler());
        HttpContext context7 = server.createContext("/api/booking/view2",playerBookingHandler::handle);


        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
