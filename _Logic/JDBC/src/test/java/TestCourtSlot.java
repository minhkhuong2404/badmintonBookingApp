import static org.junit.jupiter.api.Assertions.assertEquals;

import app.booking.slot.Booking;
import app.booking.slot.CourtSlot;
import app.booking.slot.Slot;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestCourtSlot {

    @Test
    public void bookingListEmpty() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();

        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements
        assertEquals(1, slotList.size(), "Pass.");
    }
    @Test
    public void slotListEmpty() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();
        booking.add(new Booking("07:00", "21:00"));

        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements
        assertEquals(0, slotList.size(), "Pass.");
    }
}
