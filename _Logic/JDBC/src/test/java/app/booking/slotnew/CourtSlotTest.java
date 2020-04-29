package app.booking.slotnew;

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
    public void CourtTest_Construct_With_Date() throws SQLException {
        CourtSlot courtSlot = new CourtSlot("A1C", Date.valueOf("2020-05-10"));

        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("07:00:00", "10:30:00"));
        assertThat(slots.get(1)).isEqualToComparingFieldByField(new Slot("11:30:00", "12:31:00"));
        assertThat(slots.get(2)).isEqualToComparingFieldByField(new Slot("13:31:00", "21:00:00"));
    }

    @Test
    public void CourtTest_Construct_With_Date_EmptyBookingList() throws SQLException {
        CourtSlot courtSlot = new CourtSlot("A1C", Date.valueOf("2029-05-10"));

        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("07:00:00", "21:00:00"));
    }

    @Test
    public void CourtTest_Construct_With_Date_No_Slot() throws SQLException {
        CourtSlot courtSlot = new CourtSlot("C1C2", Date.valueOf("2020-05-10"));
        // expected result
        ArrayList<Slot> slots = new ArrayList<>();
        ArrayList<Slot> slots2 = courtSlot.getCourtSlots();

        assertEquals(slots, slots2);
    }

    @Test
    public void CourtTest_Construct_With_Date_Continous_Bookings() throws SQLException {
        CourtSlot courtSlot = new CourtSlot("C1C2", Date.valueOf("2020-05-06"));
        // expected result
        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("07:00:00", "13:31:00"));
        assertThat(slots.get(1)).isEqualToComparingFieldByField(new Slot("16:00:00", "21:00:00"));
    }
    @Test
    public void CourtTest_Construct_WithDate_Given_Time_Constraint() throws SQLException, JsonProcessingException {
        Time open = Time.valueOf("06:00:00");
        Time close = Time.valueOf("20:00:00");
        Time min = Time.valueOf("00:25:00");

        CourtSlot courtSlot = new CourtSlot("C1C2", Date.valueOf("2020-05-06"), open, close, min);

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(courtSlot));
    }
}