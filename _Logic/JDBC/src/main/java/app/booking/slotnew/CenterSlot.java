package app.booking.slotnew;

import app.booking.db.Booking;
import app.booking.db.Court;
import app.booking.db.SQLStatement;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Objects;

//////////////////////////////////////////////////////////////////////////////////////////////////////////
// This class stores all slots of a center in a specific date                                           //
//                                                                                                      //
// CenterSlot(String center_id, Date date):                                                             //
//    + Constructor takes Date type as input to retrieve bookings list on that date from database       //
//    + Use default open, close time and min length of slot                                             //
//                                                                                                      //
// CenterSlot(String center_id, Date date, Time open, Time close, Time min):                            //
//    + Constructor takes Date type as input to retrieve bookings list on that date from database       //
//    + Use arbitrary open, close time and min length of slot                                           //
//                                                                                                      //
// CenterSlot(String center_id, ArrayList<Booking> centerBookings):                                     //
//    + Constructor takes given bookings list as input (they must be on the same day)                   //
//    + Use default open, close time and min length of slot                                             //
//                                                                                                      //
// CenterSlot(String center_id, ArrayList<Booking> centerBookings, Time open, Time close, Time min):    //
//    + Constructor takes given bookings list as input (they must be on the same day)                   //
//    + Use arbitrary open, close time and min length of slot                                           //
//////////////////////////////////////////////////////////////////////////////////////////////////////////

public class CenterSlot {
    private String cityId;
    private String centerId;
    private ArrayList<CourtSlot> centerSlots; // store all courts, each court has its own slots

    public CenterSlot(String centerId, Date date) throws SQLException {
        final ArrayList<Booking> centerBookings = SQLStatement.getCenterBookings(centerId, date);

        this.centerId = centerId;
        setCenterSlots(centerBookings);
    }

    public CenterSlot(String centerId, Date date, Time open, Time close, Time min) throws SQLException {
        final ArrayList<Booking> centerBookings = SQLStatement.getCenterBookings(centerId, date);

        this.centerId = centerId;
        setCenterSlots(centerBookings, open, close, min);
    }

    public CenterSlot(String centerId, final ArrayList<Booking> centerBookings) throws SQLException {
        this.centerId = centerId;
        setCenterSlots(centerBookings);
    }

    public CenterSlot(String centerId, final ArrayList<Booking> centerBookings, Time open, Time close, Time min) throws SQLException {
        this.centerId = centerId;
        setCenterSlots(centerBookings, open, close, min);
    }

    private void setCenterSlots(final ArrayList<Booking> centerBookings) throws SQLException {
        ArrayList<Court> courts = SQLStatement.getCenterCourts(centerId);
        this.centerSlots = new ArrayList<>();

        Integer j = 0;
        for (int i = 0; i < courts.size(); i++) {
            String court_id = courts.get(i).getCourtId();
            // new court centerBookings arraylist
            ArrayList<Booking> courtBookings = new ArrayList<>();
            // collect centerBookings of this court
            while (j < centerBookings.size()) {
                if (centerBookings.get(j).getCourtId().equals(courts.get(i).getCourtId())) {
                    courtBookings.add(centerBookings.get(j));
                } else break;
                j++;
            }
            // create new court with collected booking
            CourtSlot courtSlot = new CourtSlot(court_id, courtBookings);
            courtSlot.setCenterId(this.centerId);
            this.centerSlots.add(courtSlot);
        }
    }

    private void setCenterSlots(final ArrayList<Booking> centerBookings, Time open, Time close, Time min) throws SQLException {
        ArrayList<Court> courts = SQLStatement.getCenterCourts(centerId);
        this.centerSlots = new ArrayList<>();

        Integer j = 0;
        for (int i = 0; i < courts.size(); i++) {
            String court_id = courts.get(i).getCourtId();
            // new court centerBookings arraylist
            ArrayList<Booking> courtBookings = new ArrayList<>();
            // collect centerBookings of this court
            while (j < centerBookings.size()) {
                if (centerBookings.get(j).getCourtId().equals(courts.get(i).getCourtId())) {
                    courtBookings.add(centerBookings.get(j));
                } else break;
                j++;
            }
            // create new court with collected booking
            CourtSlot courtSlot = new CourtSlot(court_id, courtBookings, open, close, min);
            courtSlot.setCenterId(this.centerId);
            this.centerSlots.add(courtSlot);
        }
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityId() {
        return cityId;
    }

    public String getCenterId() {
        return centerId;
    }

    public ArrayList<CourtSlot> getCenterSlots() {
        return centerSlots;
    }

    public boolean equals(CenterSlot o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CenterSlot that = (CenterSlot) o;
        return Objects.equals(cityId, that.cityId) &&
                centerId.equals(that.centerId) &&
                centerSlots.equals(that.centerSlots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityId, centerId, centerSlots);
    }
}