package app.booking.slotnew;

import app.booking.Constants;
import app.booking.db.Booking;
import app.booking.db.SQLStatement;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Objects;
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
    private String centerId;
    private String courtId;
    private ArrayList<Slot> courtSlots = new ArrayList<>(); // slots of specified day

    public CourtSlot(String courtId, Date date) throws SQLException {
        this.courtId = courtId;

        // get court bookings from sql
        ArrayList<Booking> bookingArrayList = SQLStatement.getCourtBookings(courtId, date);

        // calculate slots from bookings list
        setCourtSlots(bookingArrayList);
    }

    public CourtSlot(String courtId, Date date, Time open, Time close, Time min) throws SQLException {
        this.courtId = courtId;
        final ArrayList<Booking> courtBookings = SQLStatement.getCourtBookings(courtId, date);
        setCourtSlots(courtBookings, open, close, min);
    }

    public CourtSlot(String courtId, final ArrayList<Booking> courtBookings) {
        this.courtId = courtId;
        setCourtSlots(courtBookings);
    }

    public CourtSlot(String courtId, final ArrayList<Booking> courtBookings, Time open, Time close, Time min) {
        this.courtId = courtId;
        setCourtSlots(courtBookings, open, close, min);
    }

    private void setCourtSlots(final ArrayList<Booking> courtBookings) {
        Time open = Constants.DEFAULT_START_TIME; //        open : open time
        Time close = Constants.DEFAULT_END_TIME;//        close : close time
        Long min = Constants.DEFAULT_MIN_TIME;//        min : min length of slot

        Slot slot = new Slot(open, null);

        for (Booking booking : courtBookings) {
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

    private void setCourtSlots(final ArrayList<Booking> courtBookings, Time open, Time close, Time min) {
        Long MINIMUM = min.getTime() - Time.valueOf("00:00:00").getTime(); // convert to long

        Slot slot = new Slot(open, null);

        for (Booking booking : courtBookings) {
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

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getCenterId() {
        return centerId;
    }

    public String getCourtId() {
        return courtId;
    }

    public ArrayList<Slot> getCourtSlots() {
        return courtSlots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourtSlot courtSlot = (CourtSlot) o;
        return Objects.equals(centerId, courtSlot.centerId) &&
                courtId.equals(courtSlot.courtId) &&
                courtSlots.equals(courtSlot.courtSlots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerId, courtId, courtSlots);
    }
}