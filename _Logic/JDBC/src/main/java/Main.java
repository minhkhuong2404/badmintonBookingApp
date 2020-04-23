import app.booking.db.Booking;
import app.booking.db.SQLStatement;
import app.booking.slot.CenterSlots;
import app.booking.slot.CitySlots;
import app.booking.slot.CourtSlots;
import app.booking.slot.Slot;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        ArrayList<Booking> bookings = SQLStatement.getCityBookings("A", Date.valueOf("2020-05-10"));
        String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(bookings);
        System.out.println(json);

        ArrayList<Booking> bookings3 = SQLStatement.getCenterBookings("A1", Date.valueOf("2020-05-10"));
        String json2 = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(bookings3);
        System.out.println(json2);

        ArrayList<Booking> bookings1 = SQLStatement.getCourtBookings("A1C", Date.valueOf("2020-06-10"));
        String json3 = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(bookings1);
        System.out.println(json3);

        ArrayList<Slot> slotArrayList = CourtSlots.getCourtSlots("A1C", Date.valueOf("2020-05-10"));
        String json4 = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(slotArrayList);
        System.out.println(json4);

        Map<String, ArrayList<Slot>> centerSlots = CenterSlots.getCenterSlots("A1", Date.valueOf("2020-05-10"));
        String json1 = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(centerSlots);
        System.out.println(json1);

        Map<String, Map<String, ArrayList<Slot>>> citySlots = CitySlots.getCitySlots("A", Date.valueOf("2020-05-10"));
        String json5 = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(citySlots);
        System.out.println(json5);
    }
}
