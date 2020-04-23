package app.booking.slot;

import app.booking.db.Booking;
import app.booking.db.SQLStatement;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CitySlots {
    public static Map<String, Map<String, ArrayList<Slot>>> getCitySlots(String city_id, Date date) throws SQLException {
        // get court booking list
        ArrayList<Booking> cityBookingList = SQLStatement.getCityBookings(city_id, date);
        // initialize map of center, court and slot list
        Map<String, Map<String, ArrayList<Slot>>> citySlotLists = new HashMap<>();

        for (Booking booking : cityBookingList) {
            String centerId = booking.getCenterId();
            String courtId = booking.getCourtId();

            // add centerId to Map citySlotList
            if (!citySlotLists.containsKey(centerId)) {
                Map<String, ArrayList<Slot>> centerSlotLists = new HashMap<>();
                citySlotLists.put(centerId, centerSlotLists);
            }
            // add courtId to Map of (Map centerId, slotArrayList)
            if (!citySlotLists.get(centerId).containsKey(courtId)){
                ArrayList<Slot> slotArrayList = new ArrayList<Slot>();
                slotArrayList.add(new Slot("07:00:00", "21:00:00"));
                citySlotLists.get(centerId).put(courtId, slotArrayList);
            }
            // chen booking nay vao slot list cua map [court_id, slot list]
            makeSlot(citySlotLists.get(centerId).get(courtId), booking);
        }
        return citySlotLists;
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
