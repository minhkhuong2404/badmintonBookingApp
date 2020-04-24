package app.booking.slot;

import app.booking.db.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CitySlot extends CourtSlot {

    public CitySlot(String cityId, ArrayList<Booking> bookingArrayList) {
        // TODO: check city existence
        super(cityId, bookingArrayList);
    }

    public HashMap<String, HashMap<String, ArrayList<Slot>>> getCitySlot() throws SQLException {
        HashMap<String, HashMap<String, ArrayList<Slot>>> citySlot = new HashMap<>();

        // Add slot [ 7AM - 21PM ] to each courts of city
        initializeCitySlot(citySlot);

        // For each booking, split corresponding slot
        for (Booking booking : this.getBookingArrayList()) {
            String centerId = booking.getCenterId();
            String courtId = booking.getCourtId();
            splitSlot(citySlot.get(centerId).get(courtId), booking);
        }
        return citySlot;
    }

    private void initializeCitySlot(HashMap<String, HashMap<String, ArrayList<Slot>>> citySlot) throws SQLException {
        // Get city's centers
        ArrayList<Center> centerArrayList = SQLStatement.getCityCenters(this.getId());

        // For each center // TODO: in case of no center?
        for (Center center : centerArrayList) {
            // Add center's empty slot list
            String centerId = center.getCenterId();
            citySlot.put(centerId, new HashMap<>());

            // Get center's courts
            ArrayList<Court> courtArrayList = SQLStatement.getCenterCourts(center.getCenterId());

            // For each court // TODO: in case of no court?
            for (Court court : courtArrayList) {
                // Add court's empty slot
                String courtId = court.getCourtId();
                citySlot.get(centerId).put(courtId, new ArrayList<>());

                // Add slot [7AM - 21PM] to court slot list
                citySlot.get(centerId).get(courtId).add(new Slot("07:00:00", "21:00:00"));
            }
        }
    }
}