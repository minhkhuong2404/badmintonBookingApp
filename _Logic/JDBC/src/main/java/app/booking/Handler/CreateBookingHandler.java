package app.booking.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class CreateBookingHandler extends Handler {
    public CreateBookingHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    protected void execute(HttpExchange exchange) throws IOException{
        if ("POST".equals(exchange.getRequestMethod())) {
            doPost(exchange.getRequestBody());
            String respText = "Successful";
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        } else {
            String respText = "Something is wrong";
            exchange.sendResponseHeaders(405, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        exchange.close();
    }

    private void doPost(InputStream is) {
        CreateBookingRequest CBrequest = super.readRequest(is, CreateBookingRequest.class);
        String a = CBrequest.getCourtid();

    }
}
