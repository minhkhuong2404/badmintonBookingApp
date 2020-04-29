package app.booking.slotnew;

import app.booking.db.Booking;
import app.booking.db.Court;
import app.booking.db.SQLStatement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

class CenterSlotTest {
    @Test
    void Construct_From_SQL_Given_Date() throws SQLException, JsonProcessingException {
        // Test object
        String center = "A1";
        Date date = Date.valueOf("2020-05-10");

        // generate actual result
        ObjectMapper mapper = new ObjectMapper();
        CenterSlot centerSlot = new CenterSlot(center, date);
        String actual = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(centerSlot);

        System.out.println(actual);
    }

    @Test
    void Construct_From_SQL_Given_Bookings() throws SQLException, JsonProcessingException {
        // Test object
        String center = "A1";
        Date date = Date.valueOf("2020-05-10");

        // generate data
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Booking> bookingArrayList = SQLStatement.getCenterBookings(center, date);
        // generate expected result
        CenterSlot centerSlotExpected = new CenterSlot(center, date);
        String expected = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(centerSlotExpected);

        // generate actual result

        CenterSlot centerSlotActual = new CenterSlot(center, bookingArrayList);
        String actual = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(centerSlotActual);

        assertEquals(true, centerSlotExpected.equals(centerSlotActual));
    }

    @Test
    void Construct_From_SQL_Given_Date_Arbitrary() throws SQLException, JsonProcessingException {
        // Test object
        String center = "A1";
        Date date = Date.valueOf("2020-05-10");

        // generate actual result
        Time open = Time.valueOf("06:00:00");
        Time close = Time.valueOf("20:00:00");
        Time min = Time.valueOf("00:45:00");
        ObjectMapper mapper = new ObjectMapper();
        CenterSlot centerSlot = new CenterSlot(center, date, open, close, min);
        String actual = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(centerSlot);

        System.out.println(actual);
    }

    @Test
    void Construct_From_SQL_Given_Bookings_Arbitrary() throws SQLException, JsonProcessingException {
        // Test object
        String center = "A1";
        Date date = Date.valueOf("2020-05-10");

        // generate data
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Booking> bookingArrayList = SQLStatement.getCenterBookings(center, date);
        // generate expected result
        CenterSlot centerSlotExpected = new CenterSlot(center, date);
        String expected = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(centerSlotExpected);

        // generate actual result

        CenterSlot centerSlotActual = new CenterSlot(center, bookingArrayList);
        String actual = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(centerSlotActual);

        assertEquals(true, centerSlotExpected.equals(centerSlotActual));
    }
}