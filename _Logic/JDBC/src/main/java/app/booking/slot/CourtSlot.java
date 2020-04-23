package app.booking.slot;

import app.booking.db.Booking;

import java.util.ArrayList;

public class CourtSlot {
    public ArrayList<Slot> getSlots(ArrayList<Booking> bookingList) {
        // convert booking list to simplified booking list
        ArrayList<SimplifiedBooking> simplifiedBookingList = new ArrayList<>();
        for (Booking booking : bookingList) {
            simplifiedBookingList.add(new SimplifiedBooking(booking));
        }
        // create list of slots
        ArrayList<Slot> slotList = new ArrayList<Slot>();
        // initial slot: [7:00 21:00]
        slotList.add(new Slot("07:00", "21:00"));

        for (SimplifiedBooking simplifiedBooking : simplifiedBookingList) {

            Slot slot = slotList.get(slotList.size() - 1);

            if (slot.getEnd().toMinute() - simplifiedBooking.getEnd().toMinute() >= 45) {
                slotList.add(new Slot(simplifiedBooking.getEnd(), slot.getEnd()));
            }

            if (simplifiedBooking.getStart().toMinute() - slot.getStart().toMinute() >= 45) {
                slot.setEnd(simplifiedBooking.getStart());
            } else {
                slotList.remove(slot);
            }
        }

        return slotList;
    }
}
