package app.booking.slot;
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
        // create bookings from 7:00 to 21:00
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
        // assert statements: if the booking list is full
        // then it should have no available slot
        assertEquals(0, slotList.size(), "Pass.");
    }

    @Test
    public void getAvailableSlot_IfThereIsBookingAt7AM() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();
        // create booking from 7:00 to 07:45
        booking.add(new Booking("07:00", "07:45"));
        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements: if there are bookings from 7:00 to 7:45
        // then it should have 1 available slot from 7:45 to 21:00
        assertEquals(1, slotList.size(), "Pass.");
    }

    @Test
    public void getAvailableSlot_IfThereIsBookingAt20_15() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();
        // create booking from 20:15 to 21:00
        booking.add(new Booking("20:15", "21:00"));

        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements: if there are bookings from 20:15 to 21:00
        // then it should have 1 available slot from 7:00 to 20:15
        assertEquals(1, slotList.size(),"Pass");
    }

    @Test
    public void getAvailableSlot_IfThereIsBookingFrom7_30to21_00() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();
        //create booking from 7:30 to 21:00
        booking.add(new Booking("07:30", "09:00"));
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
        // assert statements: if there are bookings from 7:30 to 21:00
        // then iit should have no available slot
        assertEquals(0, slotList.size(), "Pass.");
    }

    @Test
    public void getAvailableSlot_IfThereIsBookingFrom7to20_30() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();
        // create booking from 7:00 to 20:30
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
        booking.add(new Booking("19:00", "20:30"));

        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements: if there are bookings from 7:00 to 20:30
        // then it should have no available slot
        assertEquals(0, slotList.size(), "Pass.");
    }

    @Test
    public void getAvailableSlot_IfThereIsBookingFrom7_30AMto20_30PM() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();
        // create booking from 7:30AM to 20:30PM
        booking.add(new Booking("07:30", "09:00"));

        booking.add(new Booking("19:00", "20:30"));

        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements: if there are bookings from 7:30 to 9:00
        // and from 19:00 to 20:30
        // then it should have available slot from 9:00 to 19:00
        assertEquals(1, slotList.size(), "Pass.");
    }

    @Test
    public void getAvailableSlot_IfThereIsContinuouslyBooking() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();
        //create an continuously booking from 8:00 to 8:45
        //and a continuous booking from 8:45 to 9:15
        booking.add(new Booking("08:00", "08:45"));
        booking.add(new Booking("08:45", "09:15"));

        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements: if there is a continuous booking from 8:00 to 09:15
        // then it should have a slot from 7:00 to 8:00
        // and from 9:15 to 21:00
        assertEquals(2, slotList.size(), "Pass.");
    }
    @Test
    public void getAvailableSlot_IfThereAreSlotsAndTheIntervalIsLargerThan45min() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();
        //create an continuously booking from 7 to 8:15
        booking.add(new Booking("07:00", "08:00"));

        booking.add(new Booking("09:00", "10:00"));

        booking.add(new Booking("11:00", "12:00"));

        booking.add(new Booking("13:00", "14:00"));

        booking.add(new Booking("15:00", "16:00"));

        booking.add(new Booking("17:00", "18:00"));

        booking.add(new Booking("19:00", "20:00"));


        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements: if there are 7 bookings
        // then it should have available slots between bookings
        assertEquals(7, slotList.size(), "Pass.");
    }

    @Test
    public void getAvailableSlot_IfThereAreSlotsButTheIntervalIsSmallerThan45min() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();
        //create an continuously booking from 7 to 8:15
        booking.add(new Booking("07:00", "08:30"));

        booking.add(new Booking("09:00", "10:30"));

        booking.add(new Booking("11:00", "12:30"));

        booking.add(new Booking("13:00", "14:30"));

        booking.add(new Booking("15:00", "16:30"));

        booking.add(new Booking("17:00", "18:30"));

        booking.add(new Booking("19:00", "20:30"));


        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements: when the free time between 2 booking < 45min
        // then it should have no available slot
        assertEquals(0, slotList.size(), "Pass.");
    }

    @Test
    public void getAvailableSlot_IfThereAreSlotsAndTheIntervalIsEqualTo45min() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();
        //create an continuously booking from 7 to 8:15
        booking.add(new Booking("07:00", "08:00"));

        booking.add(new Booking("08:45", "09:30"));

        booking.add(new Booking("10:15", "11:00"));

        booking.add(new Booking("11:45", "12:30"));

        booking.add(new Booking("13:15", "14:00"));

        booking.add(new Booking("14:45", "15:30"));

        booking.add(new Booking("16:15", "17:00"));

        booking.add(new Booking("17:45", "18:30"));

        booking.add(new Booking("19:15", "20:00"));
        booking.add(new Booking("20:00", "21:00"));


        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements: when the free time between 2 booking = 45min
        // then it should have available slots between 2 booking when interval = 45
        assertEquals(8, slotList.size(), "Pass.");
    }

    @Test
    public void getAvailableSlot_IfThereIsBookingAt7AndOnesAt20_00() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();
        // create bookings from 7:00 to 8:00
        // and ones from 20:00 to 21:00
        booking.add(new Booking("07:00", "08:00"));
        
        booking.add(new Booking("20:00", "21:00"));

        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements: if there is bookings from 7:00 to 8:00
        // and ones from 20:00 to 21:00
        // then it should have 1 available slot from 8:00 to 20:00
        assertEquals(1, slotList.size(), "Pass.");
    }
    
    @Test
    public void getAvailableSlot_IfThereIsBookingFrom7_45To20_15() {
        // create empty booking
        ArrayList<Booking> booking = new ArrayList<Booking>();
        // create bookings from 7:45 to 20:15

        booking.add(new Booking("07:45", "09:00"));
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
        booking.add(new Booking("19:00", "20:15"));


        CourtSlot courtSlot = new CourtSlot();

        ArrayList<Slot> slotList = courtSlot.get(booking);
        // assert statements: if there is bookings from 7:45 to 20:15
        // then it should have 2 available slot from 7:00 to 7:45 and from 20:15 to 21:00
        assertEquals(2, slotList.size(), "Pass.");
    }
}
