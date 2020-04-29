package app.booking.slotnew;

import app.booking.db.Booking;
import app.booking.db.Center;
import app.booking.db.Court;
import app.booking.db.SQLStatement;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

//////////////////////////////////////////////////////////////////////////////////////////////////////////
// This class stores all slots of a center in a specific date                                           //
//                                                                                                      //
// CitySlot(String city_id, Date date):                                                                 //
//    + Constructor takes Date type as input to retrieve bookings list on that date from database       //
//    + Use default open, close time and min length of slot                                             //
//                                                                                                      //
// CitySlot(String city_id, Date date, Time open, Time close, Time min):                                //
//    + Constructor takes Date type as input to retrieve bookings list on that date from database       //
//    + Use arbitrary open, close time and min length of slot                                           //
//                                                                                                      //
// CitySlot(String city_id, ArrayList<Booking> bookings):                                               //
//    + Constructor takes given bookings list as input (they must be on the same day)                   //
//    + Use default open, close time and min length of slot                                             //
//                                                                                                      //
// CitySlot(String city_id, ArrayList<Booking> bookings,Time open, Time close, Time min):               //
//    + Constructor takes given bookings list as input (they must be on the same day)                   //
//    + Use arbitrary open, close time and min length of slot                                           //
//////////////////////////////////////////////////////////////////////////////////////////////////////////

public class CitySlot {
    private String cityId;
    private ArrayList<CenterSlot> citySlots;

    public CitySlot(String cityId, Date date) throws SQLException {
        final ArrayList<Booking> cityBookings = SQLStatement.getCityBookings(cityId, date);

        this.cityId = cityId;
        setCitySlots(cityBookings);
    }

    public CitySlot(String cityId, Date date, Time open, Time close, Time min) throws SQLException {
        final ArrayList<Booking> cityBookings = SQLStatement.getCityBookings(cityId, date);

        this.cityId = cityId;
        setCitySlots(cityBookings, open, close, min);
    }

    public CitySlot(String cityId, final ArrayList<Booking> cityBookings) throws SQLException {
        this.cityId = cityId;
        setCitySlots(cityBookings);
    }

    public CitySlot(String cityId, final ArrayList<Booking> cityBookings, Time open, Time close, Time min) throws SQLException {
        this.cityId = cityId;
        setCitySlots(cityBookings, open, close, min);
    }

    private void setCitySlots(final ArrayList<Booking> cityBookings) throws SQLException {
        ArrayList<Center> centers = SQLStatement.getCityCenters(cityId);
        this.citySlots = new ArrayList<>();

        Integer j = 0;
        for (int i = 0; i < centers.size(); i++) {
            String center_id = centers.get(i).getCenterId();
            // new court bookings arraylist
            ArrayList<Booking> centerBookings = new ArrayList<>();
            // collect bookings of this center
            while (j < cityBookings.size()) {
                if (cityBookings.get(j).getCenterId().equals(centers.get(i).getCenterId())) {
                    centerBookings.add(cityBookings.get(j));
                } else break;
                j++;
            }
            // create new center with collected booking
            CenterSlot centerSlot = new CenterSlot(center_id, centerBookings);
            centerSlot.setCityId(this.cityId);
            this.citySlots.add(centerSlot);
        }
    }

    private void setCitySlots(final ArrayList<Booking> cityBookings, Time open, Time close, Time min) throws SQLException {
        ArrayList<Center> centers = SQLStatement.getCityCenters(cityId);
        this.citySlots = new ArrayList<>();

        Integer j = 0;
        for (int i = 0; i < centers.size(); i++) {
            String center_id = centers.get(i).getCenterId();
            // new court bookings arraylist
            ArrayList<Booking> centerBookings = new ArrayList<>();
            // collect bookings of this center
            while (j < cityBookings.size()) {
                if (cityBookings.get(j).getCenterId().equals(centers.get(i).getCenterId())) {
                    centerBookings.add(cityBookings.get(j));
                } else break;
                j++;
            }
            // create new center with collected booking
            CenterSlot centerSlot = new CenterSlot(center_id, centerBookings, open, close, min);
            centerSlot.setCityId(this.cityId);
            this.citySlots.add(centerSlot);
        }
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityId() {
        return cityId;
    }

    public ArrayList<CenterSlot> getCitySlots() {
        return citySlots;
    }
}
