package app.booking.slot;

import app.booking.db.Booking;
import app.booking.db.SQLStatement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

class CenterSlotTest {
    @Test
    void Construct_GivenDate() throws SQLException, JsonProcessingException {
        // Test object
        String center = "A1";
        Date date = Date.valueOf("2020-05-10");

        // generate actual result
        ObjectMapper mapper = new ObjectMapper();
        CenterSlot centerSlot = new CenterSlot(center, date);
        String actual = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(centerSlot);

        String expected = "{\n" +
                "  \"cityId\" : null,\n" +
                "  \"centerId\" : \"A1\",\n" +
                "  \"centerSlots\" : [ {\n" +
                "    \"centerId\" : \"A1\",\n" +
                "    \"courtId\" : \"A1C\",\n" +
                "    \"courtSlots\" : [ {\n" +
                "      \"start\" : \"07:00:00\",\n" +
                "      \"end\" : \"10:30:00\"\n" +
                "    }, {\n" +
                "      \"start\" : \"11:30:00\",\n" +
                "      \"end\" : \"12:31:00\"\n" +
                "    }, {\n" +
                "      \"start\" : \"13:31:00\",\n" +
                "      \"end\" : \"21:00:00\"\n" +
                "    } ]\n" +
                "  }, {\n" +
                "    \"centerId\" : \"A1\",\n" +
                "    \"courtId\" : \"A1C1\",\n" +
                "    \"courtSlots\" : [ {\n" +
                "      \"start\" : \"07:00:00\",\n" +
                "      \"end\" : \"13:31:00\"\n" +
                "    }, {\n" +
                "      \"start\" : \"14:31:00\",\n" +
                "      \"end\" : \"21:00:00\"\n" +
                "    } ]\n" +
                "  }, {\n" +
                "    \"centerId\" : \"A1\",\n" +
                "    \"courtId\" : \"A1C2\",\n" +
                "    \"courtSlots\" : [ {\n" +
                "      \"start\" : \"07:00:00\",\n" +
                "      \"end\" : \"08:31:00\"\n" +
                "    }, {\n" +
                "      \"start\" : \"09:31:00\",\n" +
                "      \"end\" : \"21:00:00\"\n" +
                "    } ]\n" +
                "  } ]\n" +
                "}";
        assertEquals(true, expected.equals(actual));
    }

    @Test
    void Construct_GivenDate_Arbitrary() throws SQLException, JsonProcessingException {
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

        String expected = "{\n" +
                "  \"cityId\" : null,\n" +
                "  \"centerId\" : \"A1\",\n" +
                "  \"centerSlots\" : [ {\n" +
                "    \"centerId\" : \"A1\",\n" +
                "    \"courtId\" : \"A1C\",\n" +
                "    \"courtSlots\" : [ {\n" +
                "      \"start\" : \"06:00:00\",\n" +
                "      \"end\" : \"10:30:00\"\n" +
                "    }, {\n" +
                "      \"start\" : \"11:30:00\",\n" +
                "      \"end\" : \"12:31:00\"\n" +
                "    }, {\n" +
                "      \"start\" : \"13:31:00\",\n" +
                "      \"end\" : \"20:00:00\"\n" +
                "    } ]\n" +
                "  }, {\n" +
                "    \"centerId\" : \"A1\",\n" +
                "    \"courtId\" : \"A1C1\",\n" +
                "    \"courtSlots\" : [ {\n" +
                "      \"start\" : \"06:00:00\",\n" +
                "      \"end\" : \"13:31:00\"\n" +
                "    }, {\n" +
                "      \"start\" : \"14:31:00\",\n" +
                "      \"end\" : \"20:00:00\"\n" +
                "    } ]\n" +
                "  }, {\n" +
                "    \"centerId\" : \"A1\",\n" +
                "    \"courtId\" : \"A1C2\",\n" +
                "    \"courtSlots\" : [ {\n" +
                "      \"start\" : \"06:00:00\",\n" +
                "      \"end\" : \"08:31:00\"\n" +
                "    }, {\n" +
                "      \"start\" : \"09:31:00\",\n" +
                "      \"end\" : \"20:00:00\"\n" +
                "    } ]\n" +
                "  } ]\n" +
                "}";
        assertTrue(expected.equals(actual));
    }

    @Test
    void Construct_GivenBookings() throws SQLException, JsonProcessingException {
        // Test object
        String center = "A1";
        Date date = Date.valueOf("2020-05-10");

        // generate data
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Booking> bookingArrayList = SQLStatement.getCenterBookings(center, date);

        // generate expected result
        CenterSlot centerSlotExpected = new CenterSlot(center, date);
        String expected = mapper.writeValueAsString(centerSlotExpected);

        // generate actual result
        CenterSlot centerSlotActual = new CenterSlot(center, bookingArrayList);
        String actual = mapper.writeValueAsString(centerSlotActual);

        assertTrue(actual.equals(expected));
    }

    @Test
    void Construct_Given_Bookings_Arbitrary() throws SQLException, JsonProcessingException {
        // Test object
        String center = "A1";
        Date date = Date.valueOf("2020-05-10");
        // generate actual result
        Time open = Time.valueOf("06:00:00");
        Time close = Time.valueOf("20:00:00");
        Time min = Time.valueOf("00:45:00");
        ArrayList<Booking> bookingArrayList = SQLStatement.getCenterBookings(center, date);
        CenterSlot centerSlotActual = new CenterSlot(center, bookingArrayList, open, close, min);
        ObjectMapper mapper = new ObjectMapper();
        String actual = mapper.writeValueAsString(centerSlotActual);

        // generate expected result
        CenterSlot centerSlotExpected = new CenterSlot(center, date, open, close, min);
        String expected = mapper.writeValueAsString(centerSlotExpected);

        assertEquals(true, expected.equals(actual));
    }

    @Test
    void Construct_Given_Date_No_Court() throws SQLException, JsonProcessingException {
        // Test object
        String center = "B2";
        Date date = Date.valueOf("2020-05-10");
        // generate actual result
        Time open = Time.valueOf("06:00:00");
        Time close = Time.valueOf("20:00:00");
        Time min = Time.valueOf("00:45:00");
        ArrayList<Booking> bookingArrayList = SQLStatement.getCenterBookings(center, date);
        CenterSlot centerSlotActual = new CenterSlot(center, bookingArrayList, open, close, min);
        ObjectMapper mapper = new ObjectMapper();
        String actual = mapper.writeValueAsString(centerSlotActual);
        System.out.println(actual);

        // generate expected result
        CenterSlot centerSlotExpected = new CenterSlot(center, date, open, close, min);
        String expected = mapper.writeValueAsString(centerSlotExpected);

        assertEquals(true, expected.equals(actual));
    }

    @Test
    void Construct_Given_Date_EmptyBooking_Arbitrary() throws SQLException, JsonProcessingException {
        // Test object
        String center = "A1";
        Date date = Date.valueOf("2029-05-10");
        // generate actual result
        Time open = Time.valueOf("06:00:00");
        Time close = Time.valueOf("20:00:00");
        Time min = Time.valueOf("00:45:00");
        ArrayList<Booking> bookingArrayList = SQLStatement.getCenterBookings(center, date);
        CenterSlot centerSlotActual = new CenterSlot(center, bookingArrayList, open, close, min);
        ObjectMapper mapper = new ObjectMapper();
        String actual = mapper.writeValueAsString(centerSlotActual);
        System.out.println(actual);

        // generate expected result
        CenterSlot centerSlotExpected = new CenterSlot(center, date, open, close, min);
        String expected = mapper.writeValueAsString(centerSlotExpected);

        assertEquals(true, expected.equals(actual));
    }
}