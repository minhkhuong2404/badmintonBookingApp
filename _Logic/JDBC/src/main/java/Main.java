import app.booking.slot.Booking;
import app.booking.slot.CourtSlot;
import app.booking.slot.Slot;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // create bookingLisst = [[8:00, 9:00], [9:45, 11:00], [11:15, 12:15], [19:45, 20:45]]
        ArrayList<Booking> bookingList = new ArrayList<Booking>();
        bookingList.add(new Booking("08:00", "09:00"));
        bookingList.add(new Booking("09:45", "11:00"));
        bookingList.add(new Booking("11:15", "12:15"));
        bookingList.add(new Booking("19:45", "20:45"));

        CourtSlot a = new CourtSlot();

        // get
        ArrayList<Slot> slotList = new ArrayList<Slot>(a.get(bookingList));
        System.out.println("Done.");
    }
}
