package app.booking.slotnew;

import app.booking.db.Booking;
import app.booking.db.Court;
import app.booking.db.SQLStatement;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

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
    private String city_id;
    private String center_id;
    private ArrayList<CourtSlot> centerSlots; // store all courts, each court has its own slots

    public CenterSlot(String center_id, Date date) throws SQLException {
        ArrayList<Booking> centerBookings = SQLStatement.getCenterBookings(center_id, date);

        this.center_id = center_id;
        setCenterSlots(centerBookings);
    }

    public CenterSlot(String center_id, Date date, Time open, Time close, Time min) throws SQLException {
        ArrayList<Booking> centerBookings = SQLStatement.getCenterBookings(center_id, date);

        this.center_id = center_id;
        setCenterSlots(centerBookings, open, close, min);
    }

    public CenterSlot(String center_id, ArrayList<Booking> centerBookings) throws SQLException {
        this.center_id = center_id;
        setCenterSlots(centerBookings);
    }

    public CenterSlot(String center_id, ArrayList<Booking> centerBookings, Time open, Time close, Time min) throws SQLException {
        this.center_id = center_id;
        setCenterSlots(centerBookings, open, close, min);
    }

    private void setCenterSlots(ArrayList<Booking> bookings) throws SQLException {
        ArrayList<Court> courts = SQLStatement.getCenterCourts(center_id);
        this.centerSlots = new ArrayList<>();

        Integer j = 0;
        for (int i = 0; i < courts.size(); i++) {
            String court_id = courts.get(i).getCourtId();
            // new court bookings arraylist
            ArrayList<Booking> courtBookings = new ArrayList<>();
            // collect bookings of this court
            while (j < bookings.size()) {
                if (bookings.get(j).getCourtId().equals(courts.get(i).getCourtId())) {
                    courtBookings.add(bookings.get(j));
                } else break;
                j++;
            }
            // create new court with collected booking
            CourtSlot courtSlot = new CourtSlot(court_id, courtBookings);
            courtSlot.setCenter_id(this.center_id);
            this.centerSlots.add(courtSlot);
        }
    }

    private void setCenterSlots(ArrayList<Booking> bookings, Time open, Time close, Time min) throws SQLException {
        ArrayList<Court> courts = SQLStatement.getCenterCourts(center_id);
        this.centerSlots = new ArrayList<>();

        Integer j = 0;
        for (int i = 0; i < courts.size(); i++) {
            String court_id = courts.get(i).getCourtId();
            // new court bookings arraylist
            ArrayList<Booking> courtBookings = new ArrayList<>();
            // collect bookings of this court
            while (j < bookings.size()) {
                if (bookings.get(j).getCourtId().equals(courts.get(i).getCourtId())) {
                    courtBookings.add(bookings.get(j));
                } else break;
                j++;
            }
            // create new court with collected booking
            CourtSlot courtSlot = new CourtSlot(court_id, courtBookings, open, close, min);
            courtSlot.setCenter_id(this.center_id);
            this.centerSlots.add(courtSlot);
        }
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public String getCenter_id() {
        return center_id;
    }

    public ArrayList<CourtSlot> getCenterSlots() {
        return centerSlots;
    }
}