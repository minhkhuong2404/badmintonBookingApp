package app.booking.slot;

import app.booking.db.Booking;
import app.booking.db.SQLStatement;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class CourtSlots {
    public static ArrayList<Slot> getCourtSlots (String court_id, Date date) throws SQLException {
        // get court booking list
        ArrayList<Booking> courtBookingList = SQLStatement.getCourtBookings(court_id, date);
        // initialize slot list
        ArrayList<Slot> slotArrayList = new ArrayList<>();
        slotArrayList.add(new Slot("07:00:00", "21:00:00"));

        for (Booking booking : courtBookingList) {
            Slot slot = slotArrayList.get(slotArrayList.size() - 1);

            if (leftMinusRight(slot.getEnd(), booking.getEnd()) >= 45) {
                slotArrayList.add(new Slot(booking.getEnd(), slot.getEnd()));
            }

            if (leftMinusRight(booking.getStart(), slot.getStart()) >= 45) {
                slot.setEnd(booking.getStart());
            } else {
                slotArrayList.remove(slot);
            }
        }
        return slotArrayList;
    }

    private static int leftMinusRight(Time t1, Time t2) {
        SimpleTime st1 = new SimpleTime(t1.toString());
        SimpleTime st2 = new SimpleTime(t2.toString());

        return (st1.toMinute() - st2.toMinute());
    }
}
