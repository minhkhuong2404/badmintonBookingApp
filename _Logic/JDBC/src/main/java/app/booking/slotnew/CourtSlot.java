package app.booking.slotnew;

import app.booking.Constants;
import app.booking.db.Booking;
import app.booking.db.SQLStatement;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
//////////////////////////////////////////////////////////////////////////////////////////////////////////
// This class stores all slots of a court in a specific date                                            //
//                                                                                                      //
// CourtSlot(String courtId, Date date):                                                                //
//    + Constructor takes Date type as input to retrieve bookings list on that date from database       //
//    + Use default open, close time and min length of slot                                             //
//                                                                                                      //
// CourtSlot(String courtId, Date date, Time open, Time close, Time min):                               //
//    + Constructor takes Date type as input to retrieve bookings list on that date from database       //
//    + Use arbitrary open, close time and min length of slot                                           //
//                                                                                                      //
// CourtSlot(String courtId, ArrayList<Booking> bookingArrayList):                                      //
//    + Constructor takes given bookings list as input (they must be on the same day)                   //
//    + Use default open, close time and min length of slot                                             //
//                                                                                                      //
// CourtSlot(String courtId, ArrayList<Booking> bookingArrayList, Time open, Time close, Time min):     //
//    + Constructor takes given bookings list as input (they must be on the same day)                   //
//    + Use arbitrary open, close time and min length of slot                                           //
//////////////////////////////////////////////////////////////////////////////////////////////////////////

public class CourtSlot {
    private String center_id;
    private String court_id;
    private ArrayList<Slot> courtSlots = new ArrayList<>(); // slots of specified day

    public CourtSlot(String courtId, Date date) throws SQLException {
        this.court_id = courtId;

        // get court bookings from sql
        ArrayList<Booking> bookingArrayList = SQLStatement.getCourtBookings(courtId, date);

        // calculate slots from bookings list
        setCourtSlots(bookingArrayList);
    }

    public CourtSlot(String courtId, Date date, Time open, Time close, Time min) throws SQLException {
        this.court_id = courtId;

        // get court bookings from sql
        ArrayList<Booking> bookingArrayList = SQLStatement.getCourtBookings(courtId, date);

        // calculate slots from bookings list
        setSlots(bookingArrayList, open, close, min);
    }

    public CourtSlot(String courtId, ArrayList<Booking> bookingArrayList) {
        this.court_id = courtId;
        // calculate slots from bookings list
        setCourtSlots(bookingArrayList);
    }

    public CourtSlot(String courtId, ArrayList<Booking> bookingArrayList, Time open, Time close, Time min) {
        this.court_id = courtId;
        // calculate slots from bookings list
        setSlots(bookingArrayList, open, close, min);
    }

    private void setCourtSlots(ArrayList<Booking> bookingArrayList) {
        Time open = Constants.DEFAULT_START_TIME; //        open : open time
        Time close = Constants.DEFAULT_END_TIME;//        close : close time
        Long min = Constants.DEFAULT_MIN_TIME;//        min : min length of slot

        Slot slot = new Slot(open, null);

        for (Booking booking : bookingArrayList) {
            if (booking.getStart().getTime() - slot.getStart().getTime() >= min) {
                slot.setEnd(booking.getStart());
                this.courtSlots.add(new Slot(slot));
            }
            slot.setStart(booking.getEnd());
        }

        if (close.getTime() - slot.getStart().getTime() >= min) {
            slot.setEnd(close);
            this.courtSlots.add(new Slot(slot));
        }
    }

    private void setSlots(ArrayList<Booking> bookingArrayList, Time open, Time close, Time min) {
        Long MINIMUM = min.getTime() - Time.valueOf("00:00:00").getTime(); // convert to long

        Slot slot = new Slot(open, null);

        for (Booking booking : bookingArrayList) {
            if (booking.getStart().getTime() - slot.getStart().getTime() >= MINIMUM) {
                slot.setEnd(booking.getStart());
                this.courtSlots.add(new Slot(slot));
            }
            slot.setStart(booking.getEnd());
        }

        if (close.getTime() - slot.getStart().getTime() >= MINIMUM) {
            slot.setEnd(close);
            this.courtSlots.add(new Slot(slot));
        }
    }

    public void setCenter_id(String center_id) {
        this.center_id = center_id;
    }

    public String getCenter_id() {
        return center_id;
    }

    public String getCourt_id() {
        return court_id;
    }

    public ArrayList<Slot> getCourtSlots() {
        return courtSlots;
    }
}