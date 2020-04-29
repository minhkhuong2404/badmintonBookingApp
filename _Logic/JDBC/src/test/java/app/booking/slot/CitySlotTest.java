package app.booking.slot;

import app.booking.db.Booking;
import app.booking.db.SQLStatement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CitySlotTest {
    @Test
    void Construct_Given_Date() throws SQLException, JsonProcessingException {
        // Test object
        String city = "A";
        Date date = Date.valueOf("2020-05-10");
        // generate actual result
        Time open = Time.valueOf("06:00:00");
        Time close = Time.valueOf("20:00:00");
        Time min = Time.valueOf("00:45:00");
        ArrayList<Booking> bookingArrayList = SQLStatement.getCityBookings(city, date);
        CitySlot citySlotActual = new CitySlot(city, bookingArrayList, open, close, min);
        ObjectMapper mapper = new ObjectMapper();
        String actual = mapper.writeValueAsString(citySlotActual);
        System.out.println(actual);

        // generate expected result
        CitySlot citySlotExpected = new CitySlot(city, date, open, close, min);
        String expected = mapper.writeValueAsString(citySlotExpected);

        System.out.println(expected);

        assertEquals(true, expected.equals(actual));
    }
}