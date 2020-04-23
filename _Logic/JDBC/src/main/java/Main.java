import app.booking.db.Booking;
import app.booking.db.SQLStatement;
import app.booking.slot.CourtSlot;
import app.booking.slot.Slot;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
//        ArrayList<Booking> bookings = SQLStatement.getCityBookings("A");
//        System.out.println(bookings);
//
//        ArrayList<Booking> bookings3 = SQLStatement.getCenterBookings("A1");
//        System.out.println(bookings3);

        ArrayList<Booking> bookings1 = SQLStatement.getCourtBookings("A1C");
        System.out.println(bookings1);

        CourtSlot courtSlot = new CourtSlot();
        ArrayList<Slot> slotArrayList = courtSlot.getSlots(bookings1);
        System.out.println(slotArrayList);
    }
}
