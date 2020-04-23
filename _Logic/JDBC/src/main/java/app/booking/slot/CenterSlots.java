package app.booking.slot;

import app.booking.db.Booking;
import app.booking.db.SQLStatement;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CenterSlots {
    public static Map<String, ArrayList<Slot>> getCenterSlots(String center_id, Date date) throws SQLException {
        // get court booking list
        ArrayList<Booking> centerBookingList = SQLStatement.getCenterBookings(center_id, date);
        // initialize map of court and slot list
        Map<String, ArrayList<Slot>> centerSlotLists = new HashMap<>();

        for (Booking booking : centerBookingList) {

            if (!centerSlotLists.containsKey(booking.getCourtId())) { // if court chua ton tai:
                ArrayList<Slot> slotArrayList = new ArrayList<Slot>();
                slotArrayList.add(new Slot("07:00:00", "21:00:00"));
                centerSlotLists.put(booking.getCourtId(), slotArrayList); // them vao map & initialize slot list
            }

            // chen booking nay vao slot list cua map [court_id, slot list]
            makeSlot(centerSlotLists.get(booking.getCourtId()), booking);
        }
        return centerSlotLists;
    }

    private static void makeSlot(ArrayList<Slot> slotArrayList, Booking booking) {
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

    private static int leftMinusRight(Time t1, Time t2) {
        SimpleTime st1 = new SimpleTime(t1.toString());
        SimpleTime st2 = new SimpleTime(t2.toString());

        return (st1.toMinute() - st2.toMinute());
    }
}
