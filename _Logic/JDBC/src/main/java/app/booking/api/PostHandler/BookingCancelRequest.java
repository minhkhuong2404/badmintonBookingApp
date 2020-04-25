package app.booking.api.PostHandler;

import lombok.Value;

@Value
public class BookingCancelRequest {
    int id;
    String playerid;
}