package app.booking.slot;

import app.booking.db.Booking;

public class SimplifiedBooking {
    private SimpleTime start;
    private SimpleTime end;

    // construct from string
    public SimplifiedBooking(Booking booking) {
        this.start = SimpleTime.valueOf(booking.getStart());
        this.end = SimpleTime.valueOf(booking.getEnd());
    }

    public SimpleTime getStart() {
        return start;
    }

    public SimpleTime getEnd() {
        return end;
    }
}
