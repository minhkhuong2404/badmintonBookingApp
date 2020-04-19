package app.booking.Handler;

import lombok.Value;

@Value
public class CreateBookingRequest {
    String userid;
    String courtid;
}
