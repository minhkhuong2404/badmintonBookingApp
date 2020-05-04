package app.booking.slot;

import app.booking.db.ConnectionDB;
import app.booking.db.SQLStatement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CourtSlotTest {
    @Test
    public void Construct_GivenDate() throws Exception {
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("court");
        SQLStatement.cleanTable("booking");

        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "A");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "A1");
        smt2.setString(2, "A");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A1C");
        smt3.setString(2, "A");
        smt3.setString(3, "A1");

        smt3.execute();


        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "1");
        smt5.setString(2, "2020-04-22 18:23:18");
        smt5.setString(3, "2020-05-10");
        smt5.setString(4, "10:30:00");
        smt5.setString(5, "11:30:00");
        smt5.setString(6, "A");
        smt5.setString(7, "A1");
        smt5.setString(8, "A1C");
        smt5.setString(9, "player1");
        smt5.setString(10, "0");


        smt5.execute();

        String sql6 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt6 = ConnectionDB.getInstance().getConnection().prepareStatement(sql6);

        smt6.setString(1, "8");
        smt6.setString(2, "2020-04-22 19:08:48");
        smt6.setString(3, "2020-05-10");
        smt6.setString(4, "12:31:00");
        smt6.setString(5, "13:31:00");
        smt6.setString(6, "A");
        smt6.setString(7, "A1");
        smt6.setString(8, "A1C");
        smt6.setString(9, "player1");
        smt6.setString(10, "0");


        smt6.execute();


        CourtSlot courtSlot = new CourtSlot("A1C", Date.valueOf("2020-05-10"));

        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("07:00:00", "10:30:00"));
        assertThat(slots.get(1)).isEqualToComparingFieldByField(new Slot("11:30:00", "12:31:00"));
        assertThat(slots.get(2)).isEqualToComparingFieldByField(new Slot("13:31:00", "21:00:00"));
    }

    @Test
    public void Construct_GivenDate_EmptyBookingList() throws Exception {
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("court");
        SQLStatement.cleanTable("booking");

        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "A");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "A1");
        smt2.setString(2, "A");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A1C");
        smt3.setString(2, "A");
        smt3.setString(3, "A1");

        smt3.execute();


        CourtSlot courtSlot = new CourtSlot("A1C", Date.valueOf("2029-05-10"));

        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("07:00:00", "21:00:00"));
    }

    @Test
    public void Construct_GivenDate_No_Slot() throws Exception {
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("court");
        SQLStatement.cleanTable("booking");

        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "A");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "A1");
        smt2.setString(2, "A");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A1C");
        smt3.setString(2, "A");
        smt3.setString(3, "A1");

        smt3.execute();

        String sql9 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt9 = ConnectionDB.getInstance().getConnection().prepareStatement(sql9);

        smt9.setString(1, "A1C1");
        smt9.setString(2, "A");
        smt9.setString(3, "A1");

        smt9.execute();

        String sql10 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt10 = ConnectionDB.getInstance().getConnection().prepareStatement(sql10);

        smt10.setString(1, "A1C2");
        smt10.setString(2, "A");
        smt10.setString(3, "A1");

        smt10.execute();


        String sql7 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt7 = ConnectionDB.getInstance().getConnection().prepareStatement(sql7);

        smt7.setString(1, "9");
        smt7.setString(2, "2020-04-22 19:08:48");
        smt7.setString(3, "2020-05-10");
        smt7.setString(4, "07:00:00");
        smt7.setString(5, "21:00:00");
        smt7.setString(6, "A");
        smt7.setString(7, "A1");
        smt7.setString(8, "A1C1");
        smt7.setString(9, "player1");
        smt7.setString(10, "0");

        smt7.execute();

        CourtSlot courtSlot = new CourtSlot("A1C1", Date.valueOf("2020-05-10"));
        // expected result
        ArrayList<Slot> slots = new ArrayList<>();
        ArrayList<Slot> slots2 = courtSlot.getCourtSlots();

        assertEquals(slots, slots2);
    }

    @Test
    public void Construct_GivenDate_Continous_Bookings() throws Exception {
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("court");
        SQLStatement.cleanTable("booking");

        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "A");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "A1");
        smt2.setString(2, "A");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A1C");
        smt3.setString(2, "A");
        smt3.setString(3, "A1");

        smt3.execute();


        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "1");
        smt5.setString(2, "2020-04-22 18:23:18");
        smt5.setString(3, "2020-05-10");
        smt5.setString(4, "13:31:00");
        smt5.setString(5, "16:00:00");
        smt5.setString(6, "A");
        smt5.setString(7, "A1");
        smt5.setString(8, "A1C");
        smt5.setString(9, "player1");
        smt5.setString(10, "0");


        smt5.execute();


        CourtSlot courtSlot = new CourtSlot("A1C", Date.valueOf("2020-05-10"));
        // expected result
        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("07:00:00", "13:31:00"));
        assertThat(slots.get(1)).isEqualToComparingFieldByField(new Slot("16:00:00", "21:00:00"));
    }

    @Test
    public void Construct_GivenDate_Given_Time_Constraint() throws Exception {
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("court");
        SQLStatement.cleanTable("booking");

        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "A");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "A1");
        smt2.setString(2, "A");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A1C");
        smt3.setString(2, "A");
        smt3.setString(3, "A1");

        smt3.execute();

        String sql9 = "INSERT INTO court VALUE (?, ?, ?)";


        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "1");
        smt5.setString(2, "2020-04-22 18:23:18");
        smt5.setString(3, "2020-05-10");
        smt5.setString(4, "13:31:00");
        smt5.setString(5, "14:31:00");
        smt5.setString(6, "A");
        smt5.setString(7, "A1");
        smt5.setString(8, "A1C");
        smt5.setString(9, "player1");
        smt5.setString(10, "0");


        smt5.execute();

        String sql6 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt6 = ConnectionDB.getInstance().getConnection().prepareStatement(sql6);

        smt6.setString(1, "8");
        smt6.setString(2, "2020-04-22 19:08:48");
        smt6.setString(3, "2020-05-10");
        smt6.setString(4, "15:00:00");
        smt6.setString(5, "16:00:00");
        smt6.setString(6, "A");
        smt6.setString(7, "A1");
        smt6.setString(8, "A1C");
        smt6.setString(9, "player1");
        smt6.setString(10, "0");

        smt6.execute();

        Time open = Time.valueOf("06:00:00");
        Time close = Time.valueOf("20:00:00");
        Time min = Time.valueOf("00:25:00");

        CourtSlot courtSlotActual = new CourtSlot("A1C", Date.valueOf("2020-05-10"), open, close, min);
        ObjectMapper mapper = new ObjectMapper();
        String actual = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(courtSlotActual);
        String expected = "{\r\n" +
                "  \"centerId\" : null,\r\n" +
                "  \"courtId\" : \"A1C\",\r\n" +
                "  \"courtSlots\" : [ {\r\n" +
                "    \"start\" : \"06:00:00\",\r\n" +
                "    \"end\" : \"13:31:00\"\r\n" +
                "  }, {\r\n" +
                "    \"start\" : \"14:31:00\",\r\n" +
                "    \"end\" : \"15:00:00\"\r\n" +
                "  }, {\r\n" +
                "    \"start\" : \"16:00:00\",\r\n" +
                "    \"end\" : \"20:00:00\"\r\n" +
                "  } ]\r\n" +
                "}";
        assertEquals(true, expected.equals(actual));
    }

    @Test
    public void Construct_GivenBooking() throws Exception {
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("court");
        SQLStatement.cleanTable("booking");

        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "A");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "A1");
        smt2.setString(2, "A");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A1C");
        smt3.setString(2, "A");
        smt3.setString(3, "A1");

        smt3.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "1");
        smt5.setString(2, "2020-04-22 18:23:18");
        smt5.setString(3, "2020-05-10");
        smt5.setString(4, "10:30:00");
        smt5.setString(5, "11:30:00");
        smt5.setString(6, "A");
        smt5.setString(7, "A1");
        smt5.setString(8, "A1C");
        smt5.setString(9, "player1");
        smt5.setString(10, "0");


        smt5.execute();

        String sql6 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt6 = ConnectionDB.getInstance().getConnection().prepareStatement(sql6);

        smt6.setString(1, "8");
        smt6.setString(2, "2020-04-22 19:08:48");
        smt6.setString(3, "2020-05-10");
        smt6.setString(4, "12:31:00");
        smt6.setString(5, "13:31:00");
        smt6.setString(6, "A");
        smt6.setString(7, "A1");
        smt6.setString(8, "A1C");
        smt6.setString(9, "player1");
        smt6.setString(10, "0");


        smt6.execute();


        String courtId = "A1C";
        ArrayList courtBookings = SQLStatement.getCourtBookings(courtId, Date.valueOf("2020-05-10"));
        CourtSlot courtSlot = new CourtSlot(courtId, courtBookings);

        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("07:00:00", "10:30:00"));
        assertThat(slots.get(1)).isEqualToComparingFieldByField(new Slot("11:30:00", "12:31:00"));
        assertThat(slots.get(2)).isEqualToComparingFieldByField(new Slot("13:31:00", "21:00:00"));
    }

    @Test
    public void Construct_GivenBooking_EmptyBookingList() throws Exception {
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("court");
        SQLStatement.cleanTable("booking");

        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "A");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "A1");
        smt2.setString(2, "A");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A1C");
        smt3.setString(2, "A");
        smt3.setString(3, "A1");

        smt3.execute();

        String courtId = "A1C";
        ArrayList courtBookings = SQLStatement.getCourtBookings(courtId, Date.valueOf("2029-05-10"));
        CourtSlot courtSlot = new CourtSlot(courtId, courtBookings);

        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("07:00:00", "21:00:00"));
    }

    @Test
    public void Construct_GivenBooking_No_Slot() throws Exception {
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("court");
        SQLStatement.cleanTable("booking");

        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "A");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "A1");
        smt2.setString(2, "A");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A1C");
        smt3.setString(2, "A");
        smt3.setString(3, "A1");

        smt3.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "1");
        smt5.setString(2, "2020-04-22 18:23:18");
        smt5.setString(3, "2020-05-10");
        smt5.setString(4, "07:00:00");
        smt5.setString(5, "21:00:00");
        smt5.setString(6, "A");
        smt5.setString(7, "A1");
        smt5.setString(8, "A1C");
        smt5.setString(9, "player1");
        smt5.setString(10, "0");


        smt5.execute();

        String courtId = "A1C";
        ArrayList courtBookings = SQLStatement.getCourtBookings(courtId, Date.valueOf("2020-05-10"));
        CourtSlot courtSlot = new CourtSlot(courtId, courtBookings);
        // expected result
        ArrayList<Slot> slots = new ArrayList<>();
        ArrayList<Slot> slots2 = courtSlot.getCourtSlots();

        assertEquals(slots, slots2);
    }

    @Test
    public void Construct_GivenBooking_Continous_Bookings() throws Exception {
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("court");
        SQLStatement.cleanTable("booking");

        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "A");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "A1");
        smt2.setString(2, "A");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A1C");
        smt3.setString(2, "A");
        smt3.setString(3, "A1");

        smt3.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "1");
        smt5.setString(2, "2020-04-22 18:23:18");
        smt5.setString(3, "2020-05-10");
        smt5.setString(4, "13:31:00");
        smt5.setString(5, "16:00:00");
        smt5.setString(6, "A");
        smt5.setString(7, "A1");
        smt5.setString(8, "A1C");
        smt5.setString(9, "player1");
        smt5.setString(10, "0");


        smt5.execute();


        String courtId = "A1C";
        ArrayList courtBookings = SQLStatement.getCourtBookings(courtId, Date.valueOf("2020-05-10"));
        CourtSlot courtSlot = new CourtSlot(courtId, courtBookings);
        // expected result
        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("07:00:00", "13:31:00"));
        assertThat(slots.get(1)).isEqualToComparingFieldByField(new Slot("16:00:00", "21:00:00"));
    }

    @Test
    public void Construct_GivenBookingDate_Time_Constraint() throws Exception {
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("court");
        SQLStatement.cleanTable("booking");

        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "A");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "A1");
        smt2.setString(2, "A");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A1C");
        smt3.setString(2, "A");
        smt3.setString(3, "A1");

        smt3.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "1");
        smt5.setString(2, "2020-04-22 18:23:18");
        smt5.setString(3, "2020-05-10");
        smt5.setString(4, "13:31:00");
        smt5.setString(5, "14:31:00");
        smt5.setString(6, "A");
        smt5.setString(7, "A1");
        smt5.setString(8, "A1C");
        smt5.setString(9, "player1");
        smt5.setString(10, "0");


        smt5.execute();

        String sql6 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt6 = ConnectionDB.getInstance().getConnection().prepareStatement(sql6);

        smt6.setString(1, "8");
        smt6.setString(2, "2020-04-22 19:08:48");
        smt6.setString(3, "2020-05-10");
        smt6.setString(4, "15:00:00");
        smt6.setString(5, "16:00:00");
        smt6.setString(6, "A");
        smt6.setString(7, "A1");
        smt6.setString(8, "A1C");
        smt6.setString(9, "player1");
        smt6.setString(10, "0");


        smt6.execute();

        Time open = Time.valueOf("06:00:00");
        Time close = Time.valueOf("20:00:00");
        Time min = Time.valueOf("00:25:00");
        String courtId = "A1C";
        ArrayList courtBookings = SQLStatement.getCourtBookings(courtId, Date.valueOf("2020-05-10"));
        CourtSlot courtSlotActual = new CourtSlot(courtId, courtBookings, open, close, min);

        ObjectMapper mapper = new ObjectMapper();
        String actual = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(courtSlotActual);
        String expected = "{\r\n" +
                "  \"centerId\" : null,\r\n" +
                "  \"courtId\" : \"A1C\",\r\n" +
                "  \"courtSlots\" : [ {\r\n" +
                "    \"start\" : \"06:00:00\",\r\n" +
                "    \"end\" : \"13:31:00\"\r\n" +
                "  }, {\r\n" +
                "    \"start\" : \"14:31:00\",\r\n" +
                "    \"end\" : \"15:00:00\"\r\n" +
                "  }, {\r\n" +
                "    \"start\" : \"16:00:00\",\r\n" +
                "    \"end\" : \"20:00:00\"\r\n" +
                "  } ]\r\n" +
                "}";
        assertEquals(true, expected.equals(actual));
    }

    @Test
    public void Construct_GivenDate_BookingAtStartOfDay() throws Exception {
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("court");
        SQLStatement.cleanTable("booking");

        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "A");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "A1");
        smt2.setString(2, "A");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A1C");
        smt3.setString(2, "A");
        smt3.setString(3, "A1");

        smt3.execute();


        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "1");
        smt5.setString(2, "2020-04-22 18:23:18");
        smt5.setString(3, "2020-05-10");
        smt5.setString(4, "07:00:00");
        smt5.setString(5, "07:45:00");
        smt5.setString(6, "A");
        smt5.setString(7, "A1");
        smt5.setString(8, "A1C");
        smt5.setString(9, "player1");
        smt5.setString(10, "0");


        smt5.execute();


        CourtSlot courtSlot = new CourtSlot("A1C", Date.valueOf("2020-05-10"));

        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("07:45:00", "21:00:00"));
    }

    @Test
    public void Construct_GivenDate_BookingAtEndOfDay() throws Exception {
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("court");
        SQLStatement.cleanTable("booking");

        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "A");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "A1");
        smt2.setString(2, "A");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A1C");
        smt3.setString(2, "A");
        smt3.setString(3, "A1");

        smt3.execute();


        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "1");
        smt5.setString(2, "2020-04-22 18:23:18");
        smt5.setString(3, "2020-05-10");
        smt5.setString(4, "20:15:00");
        smt5.setString(5, "21:00:00");
        smt5.setString(6, "A");
        smt5.setString(7, "A1");
        smt5.setString(8, "A1C");
        smt5.setString(9, "player1");
        smt5.setString(10, "0");


        smt5.execute();


        CourtSlot courtSlot = new CourtSlot("A1C", Date.valueOf("2020-05-10"));

        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("07:00:00", "20:15:00"));
    }

    @Test
    public void Construct_GivenDate_BookingAtStartAndEndOfDay() throws Exception {
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("court");
        SQLStatement.cleanTable("booking");

        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "A");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "A1");
        smt2.setString(2, "A");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A1C");
        smt3.setString(2, "A");
        smt3.setString(3, "A1");

        smt3.execute();


        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "1");
        smt5.setString(2, "2020-04-22 18:23:18");
        smt5.setString(3, "2020-05-10");
        smt5.setString(4, "07:00:00");
        smt5.setString(5, "07:45:00");
        smt5.setString(6, "A");
        smt5.setString(7, "A1");
        smt5.setString(8, "A1C");
        smt5.setString(9, "player1");
        smt5.setString(10, "0");


        smt5.execute();

        String sql6 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt6 = ConnectionDB.getInstance().getConnection().prepareStatement(sql6);

        smt6.setString(1, "2");
        smt6.setString(2, "2020-04-22 18:23:18");
        smt6.setString(3, "2020-05-10");
        smt6.setString(4, "20:15:00");
        smt6.setString(5, "21:00:00");
        smt6.setString(6, "A");
        smt6.setString(7, "A1");
        smt6.setString(8, "A1C");
        smt6.setString(9, "player1");
        smt6.setString(10, "0");


        smt6.execute();

        CourtSlot courtSlot = new CourtSlot("A1C", Date.valueOf("2020-05-10"));

        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("07:45:00", "20:15:00"));
    }

    @Test
    public void Construct_GivenDate_SlotAtStartOfDay() throws Exception {
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("court");
        SQLStatement.cleanTable("booking");

        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "A");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "A1");
        smt2.setString(2, "A");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A1C");
        smt3.setString(2, "A");
        smt3.setString(3, "A1");

        smt3.execute();


        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "1");
        smt5.setString(2, "2020-04-22 18:23:18");
        smt5.setString(3, "2020-05-10");
        smt5.setString(4, "07:45:00");
        smt5.setString(5, "21:00:00");
        smt5.setString(6, "A");
        smt5.setString(7, "A1");
        smt5.setString(8, "A1C");
        smt5.setString(9, "player1");
        smt5.setString(10, "0");


        smt5.execute();


        CourtSlot courtSlot = new CourtSlot("A1C", Date.valueOf("2020-05-10"));

        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("07:00:00", "07:45:00"));
    }

    @Test
    public void Construct_GivenDate_SlotAtEndOfDay() throws Exception {
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("court");
        SQLStatement.cleanTable("booking");

        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "A");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "A1");
        smt2.setString(2, "A");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A1C");
        smt3.setString(2, "A");
        smt3.setString(3, "A1");

        smt3.execute();


        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "1");
        smt5.setString(2, "2020-04-22 18:23:18");
        smt5.setString(3, "2020-05-10");
        smt5.setString(4, "07:00:00");
        smt5.setString(5, "20:15:00");
        smt5.setString(6, "A");
        smt5.setString(7, "A1");
        smt5.setString(8, "A1C");
        smt5.setString(9, "player1");
        smt5.setString(10, "0");


        smt5.execute();


        CourtSlot courtSlot = new CourtSlot("A1C", Date.valueOf("2020-05-10"));

        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("20:15:00", "21:00:00"));
    }

    @Test
    public void Construct_GivenDate_SlotAtStartAndEndOfDay() throws Exception {
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("court");
        SQLStatement.cleanTable("booking");

        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "A");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "A1");
        smt2.setString(2, "A");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A1C");
        smt3.setString(2, "A");
        smt3.setString(3, "A1");

        smt3.execute();


        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "1");
        smt5.setString(2, "2020-04-22 18:23:18");
        smt5.setString(3, "2020-05-10");
        smt5.setString(4, "07:45:00");
        smt5.setString(5, "20:15:00");
        smt5.setString(6, "A");
        smt5.setString(7, "A1");
        smt5.setString(8, "A1C");
        smt5.setString(9, "player1");
        smt5.setString(10, "0");


        smt5.execute();


        CourtSlot courtSlot = new CourtSlot("A1C", Date.valueOf("2020-05-10"));

        ArrayList<Slot> slots = courtSlot.getCourtSlots();

        assertThat(slots.get(0)).isEqualToComparingFieldByField(new Slot("07:00:00", "07:45:00"));
        assertThat(slots.get(1)).isEqualToComparingFieldByField(new Slot("20:15:00", "21:00:00"));
    }

    @Test
    public void Construct_GivenDate_IntervalSmallerThan45min() throws Exception {
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("court");
        SQLStatement.cleanTable("booking");

        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "A");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "A1");
        smt2.setString(2, "A");

        smt2.execute();

        String sql9 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt9 = ConnectionDB.getInstance().getConnection().prepareStatement(sql9);

        smt9.setString(1, "A1C1");
        smt9.setString(2, "A");
        smt9.setString(3, "A1");

        smt9.execute();


        String sql7 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt7 = ConnectionDB.getInstance().getConnection().prepareStatement(sql7);

        smt7.setString(1, "9");
        smt7.setString(2, "2020-04-22 19:08:48");
        smt7.setString(3, "2020-05-10");
        smt7.setString(4, "07:30:00");
        smt7.setString(5, "21:00:00");
        smt7.setString(6, "A");
        smt7.setString(7, "A1");
        smt7.setString(8, "A1C1");
        smt7.setString(9, "player1");
        smt7.setString(10, "0");

        smt7.execute();

        CourtSlot courtSlot = new CourtSlot("A1C1", Date.valueOf("2020-05-10"));
        // expected result
        ArrayList<Slot> slots = new ArrayList<>();
        ArrayList<Slot> slots2 = courtSlot.getCourtSlots();

        assertEquals(slots, slots2);
    }

}