package app.booking.Server;

import app.booking.api.GetHandler.StaffsHandler;
import app.booking.api.Handler.CreateBookingHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        server.createContext("/api/booking/create", createBookingHandler::handle);

        StaffsHandler staffsHandler = new StaffsHandler(getObjectMapper(),getErrorHandler());
        server.createContext("/api/staff/view", staffsHandler::handle);

        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
