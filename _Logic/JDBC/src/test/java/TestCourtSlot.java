import static org.junit.jupiter.api.Assertions.assertEquals;

import app.booking.slot.Booking;
import app.booking.slot.CourtSlot;
import app.booking.slot.Slot;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestCourtSlot {

    @Test
    public void getAvailableSlot_IfbookingListEmpty() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();

        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements: if there is no booking
        // then it should have at least 1 available slot
        assertEquals(1, slotList.size(), "Pass.");
    }

    @Test
    public void getAvailableSlot_IfbookingListFull() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();
        // create booking from 7AM to 21AM
        booking.add(new Booking("07:00", "21:00"));

        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements: if the booking list is full
        // then it should have no available slot
        assertEquals(0, slotList.size(), "Pass.");
    }

    @Test
    public void getAvailableSlot_IfThereIsSlotAt7AM() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();
        // create booking from 7:45 to 21:00
        booking.add(new Booking("07:45", "21:00"));
        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements: if there is booking from 7:45 to 21:00
        // then it should have 1 available slot from 7:00 to 7:45
        assertEquals(1, slotList.size(), "Pass.");
    }

    @Test
    public void getAvailableSlot_IfThereIsSlotAt20_15PM() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();
        // create booking from 7:00AM to 21:15PM
        booking.add(new Booking("07:00", "20:15"));

        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements: if there is booking from 7:00 to 20:15
        // then it should have 1 available slot from 20:15 to 21:00
        assertEquals(1, slotList.size(),"Pass");
    }

    @Test
    public void getAvailableSlot_IfThereIsBookingFrom7_30AMto21_00PM() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();
        //create booking from 7:30AM to 21:AM
        booking.add(new Booking("07:30", "21:00"));

        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements: if there is booking from 7:30 to 21:00
        // then iit should have no available slot
        assertEquals(0, slotList.size(), "Pass.");
    }

    @Test
    public void getAvailableSlot_IfThereIsBookingFrom7AMto20_30PM() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();
        // create booking from 7AM to 20:30PM
        booking.add(new Booking("07:00", "20:30"));

        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements: if there is booking from 7:00 to 20:30
        // then it should have no available slot
        assertEquals(0, slotList.size(), "Pass.");
    }

    @Test
    public void getAvailableSlot_IfThereIsBookingFrom7_30AMto20_30PM() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();
        // create booking from 7:30AM to 20:30PM
        booking.add(new Booking("07:30", "20:30"));

        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements: if there is booking from 7:30 to 20:30
        // then it should have no available slot
        assertEquals(0, slotList.size(), "Pass.");
    }

    @Test
    public void getAvailableSlot_IfThereIsContinuouslyBooking() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();
        //create an continuously booking from 7 to 8:15
        booking.add(new Booking("07:00", "08:15"));

        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements: if there is a continuous booking from 7 to 08:15
        assertEquals(1, slotList.size(), "Pass.");
    }
    @Test
    public void getAvailableSlot_14Bookings_14Hours() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();
        //create an continuously booking from 7 to 8:15
        booking.add(new Booking("07:00", "08:00"));
        booking.add(new Booking("08:00", "09:00"));
        booking.add(new Booking("09:00", "10:00"));
        booking.add(new Booking("10:00", "11:00"));
        booking.add(new Booking("11:00", "12:00"));
        booking.add(new Booking("12:00", "13:00"));
        booking.add(new Booking("13:00", "14:00"));
        booking.add(new Booking("14:00", "15:00"));
        booking.add(new Booking("15:00", "16:00"));
        booking.add(new Booking("16:00", "17:00"));
        booking.add(new Booking("17:00", "18:00"));
        booking.add(new Booking("18:00", "19:00"));
        booking.add(new Booking("19:00", "20:00"));
        booking.add(new Booking("20:00", "21:00"));

        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements: if there is a continuous booking from 7 to 08:15
        assertEquals(0, slotList.size(), "Pass.");
    }
}
