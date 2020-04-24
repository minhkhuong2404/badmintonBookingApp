import app.booking.db.*;
import app.booking.slot.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    private static HashMap<City, HashMap<Center, HashMap<Court, ArrayList<Booking>>>> bookingMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
//        ArrayList<Booking> bookings = SQLStatement.getCityBookings("A", Date.valueOf("2020-05-10"));
//        String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(bookings);
//        System.out.println(json);
//
//        ArrayList<Booking> bookings3 = SQLStatement.getCenterBookings("A1", Date.valueOf("2020-05-10"));
//        String json2 = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(bookings3);
//        System.out.println(json2);
//
//        ArrayList<Booking> bookings1 = SQLStatement.getCourtBookings("A1C", Date.valueOf("2020-06-10"));
//        String json3 = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(bookings1);
//        System.out.println(json3);
//
//        ArrayList<Slot> slotArrayList = CourtSlots.getCourtSlots("A1C", Date.valueOf("2020-05-10"));
//        String json4 = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(slotArrayList);
//        System.out.println(json4);
//
//        Map<String, ArrayList<Slot>> centerSlots = CenterSlots.getCenterSlots("A1", Date.valueOf("2020-05-10"));
//        String json1 = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(centerSlots);
//        System.out.println(json1);
//
//        Map<String, Map<String, ArrayList<Slot>>> citySlots = CitySlots.getCitySlots("A", Date.valueOf("2020-05-10"));
//        String json5 = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(citySlots);
//        System.out.println(json5);
//
//        GetSlots getSlots = new GetSlots();
//        String json6 = new ObjectMapper().writerWithDefaultPrettyPrinter().
//                writeValueAsString(getSlots.getCitySlots("A", Date.valueOf("2020-05-10")));
//        System.out.println(json6);

//        ArrayList<Booking> bookings = SQLStatement.getCityBookings("A", Date.valueOf("2020-05-10"));
//        GetSlots getSlots = new GetSlots(bookings);
//        Map<String, Map<String, Map<String, ArrayList<Slot>>>> slotMaps = getSlots.getSlotMap();
//        String json6 = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(slotMaps);
//        System.out.println(json6);



//        // prepare data for test
//        City city = new City("A");
//        Center center = new Center("A1", "A");
//        Court court = new Court("A1C", "A", "A1");
//        toHashMap(SQLStatement.getCityBookings("A", Date.valueOf("2020-05-10")));
//        ArrayList<Booking> courtBookings = bookingMap.get(city).get(center).get(court);
//        CourtSlot cs = new CourtSlot(court, SQLStatement.getCourtBookings("B2C", Date.valueOf("2020-05-10")));
//        ArrayList<Slot> slotMap = cs.getCourtSlot();
//        System.out.println(slotMap.size());


        // prepare data for test
        City city = new City("A");
        Center center = new Center("A1", "A");
        toHashMap(SQLStatement.getCityBookings("A", Date.valueOf("2020-06-10")));
        HashMap<Court, ArrayList<Booking>> courtBookingMap = bookingMap.get(city).get(center);

        CenterSlot cs = new CenterSlot(center, courtBookingMap);
        HashMap<Court, ArrayList<Slot>> slotMap = cs.getCenterSlot();


        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(slotMap));
    }

    private static void toHashMap(ArrayList<Booking> bookingArrayList) {
        for (Booking booking : bookingArrayList) {
            City cityId = new City(booking.getCityId());
            Center centerId = new Center(booking.getCenterId(), booking.getCityId());
            Court courtId = new Court(booking.getCourtId(), booking.getCityId(), booking.getCenterId());
            if (!bookingMap.containsKey(cityId)) {
                HashMap<Center, HashMap<Court, ArrayList<Booking>>> cityBookingList = new HashMap<>();
                bookingMap.put(cityId, cityBookingList);
            }
            if (!bookingMap.get(cityId).containsKey(centerId)) {
                HashMap<Court, ArrayList<Booking>> centerBookingLists = new HashMap<>();
                bookingMap.get(cityId).put(centerId, centerBookingLists);
            }
            if (!bookingMap.get(cityId).get(centerId).containsKey(courtId)) {
                ArrayList<Booking> courtBookingLists = new ArrayList<>();
                bookingMap.get(cityId).get(centerId).put(courtId, courtBookingLists);
            }
            bookingMap.get(cityId).get(centerId).get(courtId).add(booking);
        }
    }
}