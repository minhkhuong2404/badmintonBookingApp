package app.booking.slotnew;

import app.booking.db.Court;
import app.booking.db.SQLStatement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CourtSlotTest {
    @Test
    public void Construct_GivenDate() throws SQLException {
        CourtSlot courtSlot = new CourtSlot("A1C", Date.valueOf("2020-05-10"));

        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("07:00:00", "10:30:00"));
        assertThat(slots.get(1)).isEqualToComparingFieldByField(new Slot("11:30:00", "12:31:00"));
        assertThat(slots.get(2)).isEqualToComparingFieldByField(new Slot("13:31:00", "21:00:00"));
    }

    @Test
    public void Construct_GivenDate_EmptyBookingList() throws SQLException {
        CourtSlot courtSlot = new CourtSlot("A1C", Date.valueOf("2029-05-10"));

        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("07:00:00", "21:00:00"));
    }

    @Test
    public void Construct_GivenDate_No_Slot() throws SQLException {
        CourtSlot courtSlot = new CourtSlot("C1C2", Date.valueOf("2020-05-10"));
        // expected result
        ArrayList<Slot> slots = new ArrayList<>();
        ArrayList<Slot> slots2 = courtSlot.getCourtSlots();

        assertEquals(slots, slots2);
    }

    @Test
    public void Construct_GivenDate_Continous_Bookings() throws SQLException {
        CourtSlot courtSlot = new CourtSlot("C1C2", Date.valueOf("2020-05-06"));
        // expected result
        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("07:00:00", "13:31:00"));
        assertThat(slots.get(1)).isEqualToComparingFieldByField(new Slot("16:00:00", "21:00:00"));
    }

    @Test
    public void Construct_GivenDate_Given_Time_Constraint() throws SQLException, JsonProcessingException {
        Time open = Time.valueOf("06:00:00");
        Time close = Time.valueOf("20:00:00");
        Time min = Time.valueOf("00:25:00");

        CourtSlot courtSlotActual = new CourtSlot("C1C2", Date.valueOf("2020-05-06"), open, close, min);
        ObjectMapper mapper = new ObjectMapper();
        String actual = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(courtSlotActual);
        String expected = "{\n" +
                "  \"centerId\" : null,\n" +
                "  \"courtId\" : \"C1C2\",\n" +
                "  \"courtSlots\" : [ {\n" +
                "    \"start\" : \"06:00:00\",\n" +
                "    \"end\" : \"13:31:00\"\n" +
                "  }, {\n" +
                "    \"start\" : \"14:31:00\",\n" +
                "    \"end\" : \"15:00:00\"\n" +
                "  }, {\n" +
                "    \"start\" : \"16:00:00\",\n" +
                "    \"end\" : \"20:00:00\"\n" +
                "  } ]\n" +
                "}";
        assertEquals(true, expected.equals(actual));
    }

    @Test
    public void Construct_GivenBooking() throws SQLException {
        String courtId = "A1C";
        ArrayList courtBookings = SQLStatement.getCourtBookings(courtId, Date.valueOf("2020-05-10"));
        CourtSlot courtSlot = new CourtSlot(courtId, courtBookings);

        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("07:00:00", "10:30:00"));
        assertThat(slots.get(1)).isEqualToComparingFieldByField(new Slot("11:30:00", "12:31:00"));
        assertThat(slots.get(2)).isEqualToComparingFieldByField(new Slot("13:31:00", "21:00:00"));
    }

    @Test
    public void Construct_GivenBooking_EmptyBookingList() throws SQLException {
        String courtId = "A1C";
        ArrayList courtBookings = SQLStatement.getCourtBookings(courtId, Date.valueOf("2029-05-10"));
        CourtSlot courtSlot = new CourtSlot(courtId, courtBookings);

        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("07:00:00", "21:00:00"));
    }

    @Test
    public void Construct_GivenBooking_No_Slot() throws SQLException {
        String courtId = "C1C2";
        ArrayList courtBookings = SQLStatement.getCourtBookings(courtId, Date.valueOf("2020-05-10"));
        CourtSlot courtSlot = new CourtSlot(courtId, courtBookings);
        // expected result
        ArrayList<Slot> slots = new ArrayList<>();
        ArrayList<Slot> slots2 = courtSlot.getCourtSlots();

        assertEquals(slots, slots2);
    }

    @Test
    public void Construct_GivenBooking_Continous_Bookings() throws SQLException {
        String courtId = "C1C2";
        ArrayList courtBookings = SQLStatement.getCourtBookings(courtId, Date.valueOf("2020-05-06"));
        CourtSlot courtSlot = new CourtSlot(courtId, courtBookings);
        // expected result
        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("07:00:00", "13:31:00"));
        assertThat(slots.get(1)).isEqualToComparingFieldByField(new Slot("16:00:00", "21:00:00"));
    }

    @Test
    public void Construct_GivenBookingDate_Time_Constraint() throws SQLException, JsonProcessingException {
        Time open = Time.valueOf("06:00:00");
        Time close = Time.valueOf("20:00:00");
        Time min = Time.valueOf("00:25:00");
        String courtId = "C1C2";
        ArrayList courtBookings = SQLStatement.getCourtBookings(courtId, Date.valueOf("2020-05-06"));
        CourtSlot courtSlotActual = new CourtSlot(courtId, courtBookings, open, close, min);

        ObjectMapper mapper = new ObjectMapper();
        String actual = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(courtSlotActual);
        String expected = "{\n" +
                "  \"centerId\" : null,\n" +
                "  \"courtId\" : \"C1C2\",\n" +
                "  \"courtSlots\" : [ {\n" +
                "    \"start\" : \"06:00:00\",\n" +
                "    \"end\" : \"13:31:00\"\n" +
                "  }, {\n" +
                "    \"start\" : \"14:31:00\",\n" +
                "    \"end\" : \"15:00:00\"\n" +
                "  }, {\n" +
                "    \"start\" : \"16:00:00\",\n" +
                "    \"end\" : \"20:00:00\"\n" +
                "  } ]\n" +
                "}";
        assertEquals(true, expected.equals(actual));
    }

}