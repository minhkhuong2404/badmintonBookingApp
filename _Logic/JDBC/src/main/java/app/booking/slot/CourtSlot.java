package app.booking.slot;

import app.booking.db.Booking;

import java.sql.Time;
import java.util.ArrayList;

public abstract class CourtSlot {
    private String id;
    private ArrayList<Booking> bookingArrayList;

    public CourtSlot(String courtId, ArrayList<Booking> bookingArrayList) {
        this.id = courtId;
        this.bookingArrayList = bookingArrayList;
    }

    public ArrayList<Slot> getCourtSlot() {
        // TODO: check court existence

        // Initialize slot arraylist, add [7AM - 21PM] slot
        ArrayList<Slot> courtSlot = new ArrayList<>();
        courtSlot.add(new Slot("07:00:00", "21:00:00"));

        for (Booking booking : bookingArrayList) {
            // split the slot
            //splitSlot(courtSlot, booking);
        }
        return courtSlot;
    }

    protected void splitSlot(Integer minLength, ArrayList<Slot> slotArrayList, Booking booking) {
        Slot slot = slotArrayList.get(slotArrayList.size() - 1);

        if (leftMinusRight(slot.getEnd(), booking.getEnd()) >= minLength) {
            slotArrayList.add(new Slot(booking.getEnd(), slot.getEnd()));
        }

        if (leftMinusRight(booking.getStart(), slot.getStart()) >= minLength) {
            slot.setEnd(booking.getStart());
        } else {
            slotArrayList.remove(slot);
        }
    }

    protected int leftMinusRight(Time t1, Time t2) {
        SimpleTime st1 = new SimpleTime(t1.toString());
        SimpleTime st2 = new SimpleTime(t2.toString());

        return (st1.toMinute() - st2.toMinute());
    }

    public String getId() {
        return id;
    }

    public ArrayList<Booking> getBookingArrayList() {
        return bookingArrayList;
    }
}
