package app.booking.db;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
public class BookingResultSet {
    private String resultCode;
    private ArrayList<Booking> bookingArrayList;
}