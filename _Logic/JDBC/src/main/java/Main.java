import app.booking.db.*;
import app.booking.slot.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    private static HashMap<City, HashMap<Center, HashMap<Court, ArrayList<Booking>>>> bookingMap = new HashMap<>();

    public static void main(String[] args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        // CitySlot
        CitySlot citySlot = new CitySlot("B", SQLStatement.getCityBookings("B", Date.valueOf("2020-06-10")));
        HashMap<String, HashMap<String, ArrayList<Slot>>> slotMap = citySlot.getCitySlot();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(slotMap));

        // CenterSlot
        CenterSlot centerSlot = new CenterSlot("B1", SQLStatement.getCenterBookings("B1", Date.valueOf("2020-05-10")));
        HashMap<String, ArrayList<Slot>> slotMap2 = centerSlot.getCenterSlot();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(slotMap2));

        // CourtSlot
        CourtSlot courtSlot = new CourtSlot("B13", SQLStatement.getCourtBookings("B13", Date.valueOf("2020-05-10"))) {};
        ArrayList<Slot> slotMap3 = courtSlot.getCourtSlot();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(slotMap3));
    }
}