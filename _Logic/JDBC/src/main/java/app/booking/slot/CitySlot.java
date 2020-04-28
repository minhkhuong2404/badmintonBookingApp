package app.booking.slot;

import app.booking.db.*;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CitySlot extends CourtSlot {
    private Date date;
    public CitySlot(String cityId, Date pdate, ArrayList<Booking> bookingArrayList) {
        // TODO: check city existence
        super(cityId, bookingArrayList);
        this.date = pdate;
    }

    public HashMap<String, HashMap<String, ArrayList<Slot>>> getCitySlot() throws SQLException {
        HashMap<String, HashMap<String, ArrayList<Slot>>> citySlot = new HashMap<>();

        // Add slot [ 7AM - 21PM ] to each courts of city
        initializeCitySlot(citySlot);

        // For each booking, split corresponding slot
        for (Booking booking : this.getBookingArrayList()) {
            String centerId = booking.getCenterId();
            String courtId = booking.getCourtId();
            Integer minLength = SQLStatement.getCenterMinLength(centerId);
            splitSlot(minLength, citySlot.get(centerId).get(courtId), booking);
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


                // Add slot [open - close] to court slot list
                Map<String, String> activeHour = SQLStatement.getCenterActiveHour(centerId);
                if (activeHour.containsKey(toDateOfWeek(date))){
                    String todayActiveHour = (String) activeHour.get(toDateOfWeek(date));
                    String[] splitted = todayActiveHour.split("/");
                    String start = splitted[0];
                    String end = splitted[1];
                    System.out.println(start + "%" + end);
                    citySlot.get(centerId).get(courtId).add(new Slot(start, end));
                } else {
                    citySlot.get(centerId).get(courtId).add(new Slot("07:00:00", "21:00:00"));
                }
            }
        }
    }
    private String toDateOfWeek(Date pdate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(pdate);
        int weekday = cal.get(Calendar.DAY_OF_WEEK);
        System.out.println("Weekday: " + weekday);
        // Get weekday name
        DateFormatSymbols dfs = new DateFormatSymbols();
        System.out.println("Weekday: " + dfs.getWeekdays()[weekday]);
        return dfs.getWeekdays()[weekday];
    }
}