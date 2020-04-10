package app.booking.slot;

import java.util.ArrayList;

public class CourtSlot {
    public ArrayList<Slot> get(ArrayList<Booking> bookingList) {

        // create list of slots
        ArrayList<Slot> slotList = new ArrayList<Slot>();
        // initial slot: [7:00 21:00]
        slotList.add(new Slot("07:00", "21:00"));

        for (Booking booking : bookingList) {

            Slot slot = slotList.get(slotList.size() - 1);

            if (slot.getEnd().toMinute() - booking.getEnd().toMinute() >= 45) {
                slotList.add(new Slot(booking.getEnd(), slot.getEnd()));
            }

            if (booking.getStart().toMinute() - slot.getStart().toMinute() >= 45) {
                slot.setEnd(booking.getStart());
            } else {
                slotList.remove(slot);
            }
        }

        return slotList;
    }
}
