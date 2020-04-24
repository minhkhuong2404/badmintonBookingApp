package app.booking.slot;

import app.booking.db.Booking;
import app.booking.db.Court;

import java.sql.Time;
import java.util.ArrayList;

public class CourtSlot {
    private Court court;
    private ArrayList<Booking> bookingArrayList;
    private ArrayList<Slot> courtSlot = new ArrayList<>();

    public CourtSlot(Court court, ArrayList<Booking> bookingArrayList) {
        // TODO: check court existence
        this.court = court;
        this.bookingArrayList = bookingArrayList;

        // add [7AM - 21PM] slot
        courtSlot.add(new Slot("07:00:00", "21:00:00"));

        for (Booking booking : bookingArrayList) {
            // split the slot
            splitSlot(courtSlot, booking);
        }
    }

    private void splitSlot(ArrayList<Slot> slotArrayList, Booking booking) {
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

    private int leftMinusRight(Time t1, Time t2) {
        SimpleTime st1 = new SimpleTime(t1.toString());
        SimpleTime st2 = new SimpleTime(t2.toString());

        return (st1.toMinute() - st2.toMinute());
    }

    public ArrayList<Slot> getCourtSlot() {
        return courtSlot;
    }
}
