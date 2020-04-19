package app.booking.Server;

import app.booking.Handler.CreateBookingHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.OutputStream;

public class ApiServer {
    public static void main(String[] args) throws IOException {
        int serverPort = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        ObjectMapper ObjMapper = new ObjectMapper();
        CreateBookingHandler createBookingHandler = new CreateBookingHandler(ObjMapper);
        server.createContext("/api/booking/create", createBookingHandler::handle);
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
