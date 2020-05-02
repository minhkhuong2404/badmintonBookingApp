package app.booking.db;

import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import org.junit.jupiter.api.Test;

import java.sql.*;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestConnectionDBv2 {


    // createBooking

    @Test
    public void createBooking_Success() throws Exception {
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("booking");

        // scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        //actual test
        String result_code = SQLStatement.createBooking(
                Date.valueOf("2029-05-01"),
                Time.valueOf("10:30:00"),
                Time.valueOf("11:30:00"),
                "1",
                "2",
                "A",
                "B");
        assertEquals("200", result_code, "Pass.");
    }


    @Test
    public void createBooking_WhencityIdIsNotexisted() throws Exception {

        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("booking");
        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();


        //actual test
        String result_code = SQLStatement.createBooking(
                Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"),
                Time.valueOf("11:30:00"), "2", "2", "A", "B");
        assertEquals("CB-001", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhencenterIdIsNotExisted() throws Exception {

        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        //actual test
        String result_code = SQLStatement.createBooking(
                Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"),
                Time.valueOf("11:30:00"), "1", "3", "A", "B");
        assertEquals("CB-002", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenCourtIdIsNotExisted() throws Exception {

        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();


        //actual test
        String result_code = SQLStatement.createBooking(
                Date.valueOf("2021-05-01"),
                Time.valueOf("10:30:00"), Time.valueOf("11:30:00"),
                "1", "2", "C", "B");
        assertEquals("CB-003", result_code, "Pass.");
    }


    @Test
    public void createBooking_WhenStartTimeLessThanDateNow() throws Exception {

        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        //actual test
        String result_code = SQLStatement.createBooking(
                Date.valueOf("2020-03-01"),
                Time.valueOf("10:10:00"), Time.valueOf("18:35:00"),
                "1", "2", "A", "B");
        assertEquals("CB-005", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenStartTimeLessThanOpenTime() throws Exception {

        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        //actual test
        String result_code = SQLStatement.createBooking(
                Date.valueOf("2021-05-01"),
                Time.valueOf("06:00:00"), Time.valueOf("07:00:00"),
                "1", "2", "A", "B");
        assertEquals("CB-006", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenEndTimeLongerThanCloseTime() throws Exception {

        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();


        //actual test
        String result_code = SQLStatement.createBooking(
                Date.valueOf("2021-05-01"),
                Time.valueOf("07:10:00"),
                Time.valueOf("21:30:00"),
                "1",
                "2",
                "A",
                "B");
        assertEquals("CB-007", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenEndTimeLessThanStartTime() throws Exception {

        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();


        //actual test
        String result_code = SQLStatement.createBooking(
                Date.valueOf("2021-05-01"),
                Time.valueOf("08:00:00"),
                Time.valueOf("07:00:00"),
                "1",
                "2",
                "A",
                "B");
        assertEquals("CB-008", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenPlayTimeIsInvalid() throws Exception {

        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();


        //actual test
        String result_code = SQLStatement.createBooking(
                Date.valueOf("2021-05-01"),
                Time.valueOf("10:00:00"),
                Time.valueOf("10:30:00"),
                "1",
                "2",
                "A",
                "B");
        assertEquals("CB-009", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenOverlappedWithOtherBookings() throws Exception {

        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("booking");
        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();


        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "1");
        smt5.setString(2, "2020-04-07 09:27:18");
        smt5.setString(3, "2021-05-01");
        smt5.setString(4, "10:00:00");
        smt5.setString(5, "10:45:00");
        smt5.setString(6, "1");
        smt5.setString(7, "2");
        smt5.setString(8, "A");
        smt5.setString(9, "B");
        smt5.setString(10, "0");


        smt5.execute();

        //actual test
        String result_code1 = SQLStatement.createBooking(
                Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"),
                Time.valueOf("11:30:00"), "1", "2", "A", "B");
        String result_code2 = SQLStatement.createBooking(
                Date.valueOf("2021-05-01"), Time.valueOf("09:30:00"),
                Time.valueOf("10:30:00"), "1", "2", "A", "B");
        String result_code3 = SQLStatement.createBooking(
                Date.valueOf("2021-05-01"), Time.valueOf("09:45:00"),
                Time.valueOf("11:00:00"), "1", "2", "A", "B");
        String result_code4 = SQLStatement.createBooking(
                Date.valueOf("2021-05-01"), Time.valueOf("10:00:00"),
                Time.valueOf("10:45:00"), "1", "2", "A", "B");
        assertEquals("CB-010", result_code1, "Pass.");
        assertEquals("CB-010", result_code2, "Pass.");
        assertEquals("CB-010", result_code3, "Pass.");
        assertEquals("CB-010", result_code4, "Pass.");
    }

    @Test
    public void createBooking_WhenPlayerIdHavePendingBooking() throws Exception {

        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("court");
        SQLStatement.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt4 = ConnectionDB.getInstance().getConnection().prepareStatement(sql4);

        smt4.setString(1, "11");
        smt4.setString(2, "2020-04-07 18:04:00");
        smt4.setString(3, "2019-04-15");
        smt4.setString(4, "09:00:00");
        smt4.setString(5, "10:00:00");
        smt4.setString(6, "1");
        smt4.setString(7, "2");
        smt4.setString(8, "A");
        smt4.setString(9, "B");
        smt4.setString(10, "0");


        smt4.execute();

        //actual test
        String result_code = SQLStatement.createBooking(
                Date.valueOf("2021-05-03"), Time.valueOf("10:30:00"),
                Time.valueOf("11:30:00"), "1", "2", "A", "B");
        assertEquals("CB-011", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenPlayerIdHaveNoMoreThan3Booking() throws Exception {

        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("court");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("booking");
        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();


        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "1");
        smt5.setString(2, "2020-04-07 09:27:18");
        smt5.setString(3, "2020-05-03");
        smt5.setString(4, "10:00:00");
        smt5.setString(5, "10:45:00");
        smt5.setString(6, "1");
        smt5.setString(7, "2");
        smt5.setString(8, "A");
        smt5.setString(9, "B");
        smt5.setString(10, "0");


        smt5.execute();

        String sql6 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt6 = ConnectionDB.getInstance().getConnection().prepareStatement(sql6);

        smt6.setString(1, "2");
        smt6.setString(2, "2020-04-07 09:27:18");
        smt6.setString(3, "2021-05-04");
        smt6.setString(4, "10:00:00");
        smt6.setString(5, "10:45:00");
        smt6.setString(6, "1");
        smt6.setString(7, "2");
        smt6.setString(8, "A");
        smt6.setString(9, "B");
        smt6.setString(10, "0");


        smt6.execute();

        String sql7 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt7 = ConnectionDB.getInstance().getConnection().prepareStatement(sql7);

        smt7.setString(1, "3");
        smt7.setString(2, "2020-04-07 09:27:18");
        smt7.setString(3, "2021-05-05");
        smt7.setString(4, "10:00:00");
        smt7.setString(5, "10:45:00");
        smt7.setString(6, "1");
        smt7.setString(7, "2");
        smt7.setString(8, "A");
        smt7.setString(9, "B");
        smt7.setString(10, "0");


        smt7.execute();


        //actual test
        String result_code = SQLStatement.createBooking(
                Date.valueOf("2021-05-06"), Time.valueOf("10:30:00"),
                Time.valueOf("11:30:00"), "1", "2", "A", "B");
        assertEquals("CB-012", result_code, "Pass.");
    }

    //test cancel booking


    @Test
    public void cancelBooking_WhenBookingIdIsNotExisted() throws Exception {

        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("booking");
        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();


        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "1");
        smt5.setString(2, "2020-04-07 09:27:18");
        smt5.setString(3, "2021-05-01");
        smt5.setString(4, "10:30:00");
        smt5.setString(5, "11:30:00");
        smt5.setString(6, "1");
        smt5.setString(7, "2");
        smt5.setString(8, "A");
        smt5.setString(9, "B");
        smt5.setString(10, "0");


        smt5.execute();

        //actual test
        String result_code = SQLStatement.cancelBooking(2, "B");
        assertEquals("CA-003", result_code, "Pass.");
    }

    @Test
    public void cancelBooking_WhenPlayerIdDoesNotOwnTheBooking() throws Exception {

        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("booking");
        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();


        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "1");
        smt5.setString(2, "2020-04-07 09:27:18");
        smt5.setString(3, "2021-05-01");
        smt5.setString(4, "10:30:00");
        smt5.setString(5, "11:30:00");
        smt5.setString(6, "1");
        smt5.setString(7, "2");
        smt5.setString(8, "A");
        smt5.setString(9, "B");
        smt5.setString(10, "0");


        smt5.execute();

        //actual test
        String result_code = SQLStatement.cancelBooking(1, "C");
        assertEquals("CA-004", result_code, "Pass.");
    }

    @Test
    public void cancelBooking_WhenViolating24HoursBeforeStartTime() throws Exception {

        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("booking");
        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();


        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "1");
        smt5.setString(2, "2020-04-11 18:12:00");
        smt5.setString(3, "2020-04-11");
        smt5.setString(4, "20:00:00");
        smt5.setString(5, "21:00:00");
        smt5.setString(6, "1");
        smt5.setString(7, "2");
        smt5.setString(8, "A");
        smt5.setString(9, "B");
        smt5.setString(10, "0");


        smt5.execute();

        //actual test
        String result_code = SQLStatement.cancelBooking(1, "B");
        assertEquals("CA-005", result_code, "Pass.");
    }

    //test update booking
   /* @Test
    public void updateBookingStatus_success() throws Exception {

        // clean up some data
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = ConnectionDB.getInstance().getConnection().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql6 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt6 = ConnectionDB.getInstance().getConnection().prepareStatement(sql6);

        smt6.setString(1, "S");
        smt6.setString(2, "1");
        smt6.setString(3, "2");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "booking1");
        smt5.setString(2, "2020-04-11 18:12:00");
        smt5.setString(3, "2020-04-11");
        smt5.setString(4, "20:00:00");
        smt5.setString(5, "21:00:00");
        smt5.setString(6, "1");
        smt5.setString(7, "2");
        smt5.setString(8, "A");
        smt5.setString(9, "B");
        smt5.setString(10, "0");


        smt5.execute();

        //actual test
        String result_code = SQLStatement.updateBookingStatus('1', "booking1", "1", "2", "S");
        assertEquals("200", result_code, "Pass.");
    }

    @Test
    public void updateBooingStatus_WhenBookingIdIsInvalid() throws Exception {

        // clean up some data
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = ConnectionDB.getInstance().getConnection().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql6 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt6 = ConnectionDB.getInstance().getConnection().prepareStatement(sql6);

        smt6.setString(1, "S");
        smt6.setString(2, "1");
        smt6.setString(3, "2");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "booking1");
        smt5.setString(2, "2020-04-11 18:12:00");
        smt5.setString(3, "2020-04-11");
        smt5.setString(4, "20:00:00");
        smt5.setString(5, "21:00:00");
        smt5.setString(6, "1");
        smt5.setString(7, "2");
        smt5.setString(8, "A");
        smt5.setString(9, "B");
        smt5.setString(10, "0");


        smt5.execute();

        //actual test
        String result_code = SQLStatement.updateBookingStatus('1', "#booking", "1", "2", "S");
        assertEquals("UBS-000", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenCityIdIsInvalid() throws Exception {

        // clean up some data
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = ConnectionDB.getInstance().getConnection().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql6 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt6 = ConnectionDB.getInstance().getConnection().prepareStatement(sql6);

        smt6.setString(1, "S");
        smt6.setString(2, "1");
        smt6.setString(3, "2");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "booking1");
        smt5.setString(2, "2020-04-11 18:12:00");
        smt5.setString(3, "2020-04-11");
        smt5.setString(4, "20:00:00");
        smt5.setString(5, "21:00:00");
        smt5.setString(6, "1");
        smt5.setString(7, "2");
        smt5.setString(8, "A");
        smt5.setString(9, "B");
        smt5.setString(10, "0");


        smt5.execute();
        //actual test
        String result_code = SQLStatement.updateBookingStatus('1', "booking1", "#", "2", "S");
        assertEquals("UBS-001", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenCenterIdIsInvalid() throws Exception {

        // clean up some data
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = ConnectionDB.getInstance().getConnection().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql6 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt6 = ConnectionDB.getInstance().getConnection().prepareStatement(sql6);

        smt6.setString(1, "S");
        smt6.setString(2, "1");
        smt6.setString(3, "2");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "booking1");
        smt5.setString(2, "2020-04-11 18:12:00");
        smt5.setString(3, "2020-04-11");
        smt5.setString(4, "20:00:00");
        smt5.setString(5, "21:00:00");
        smt5.setString(6, "1");
        smt5.setString(7, "2");
        smt5.setString(8, "A");
        smt5.setString(9, "B");
        smt5.setString(10, "0");


        smt5.execute();

        //actual test
        String result_code = SQLStatement.updateBookingStatus('1', "booking1", "1", "#", "S");
        assertEquals("UBS-002", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenStaffIdIsInvalid() throws Exception {

        // clean up some data
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = ConnectionDB.getInstance().getConnection().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql6 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt6 = ConnectionDB.getInstance().getConnection().prepareStatement(sql6);

        smt6.setString(1, "S");
        smt6.setString(2, "1");
        smt6.setString(3, "2");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "booking1");
        smt5.setString(2, "2020-04-11 18:12:00");
        smt5.setString(3, "2020-04-11");
        smt5.setString(4, "20:00:00");
        smt5.setString(5, "21:00:00");
        smt5.setString(6, "1");
        smt5.setString(7, "2");
        smt5.setString(8, "A");
        smt5.setString(9, "B");
        smt5.setString(10, "0");


        smt5.execute();

        //actual test
        String result_code = SQLStatement.updateBookingStatus('1', "booking1", "1", "2", "#");
        assertEquals("UBS-003", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenBookingIdIsNotExisted() throws Exception {

        // clean up some data
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = ConnectionDB.getInstance().getConnection().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql6 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt6 = ConnectionDB.getInstance().getConnection().prepareStatement(sql6);

        smt6.setString(1, "S");
        smt6.setString(2, "1");
        smt6.setString(3, "2");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "booking1");
        smt5.setString(2, "2020-04-11 18:12:00");
        smt5.setString(3, "2020-04-11");
        smt5.setString(4, "20:00:00");
        smt5.setString(5, "21:00:00");
        smt5.setString(6, "1");
        smt5.setString(7, "2");
        smt5.setString(8, "A");
        smt5.setString(9, "B");
        smt5.setString(10, "0");


        smt5.execute();

        //actual test
        String result_code = SQLStatement.updateBookingStatus('1', "booking2", "1", "2", "S");
        assertEquals("UBS-004", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenCityIdIsNotExisted() throws Exception {

        // clean up some data
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = ConnectionDB.getInstance().getConnection().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql6 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt6 = ConnectionDB.getInstance().getConnection().prepareStatement(sql6);

        smt6.setString(1, "S");
        smt6.setString(2, "1");
        smt6.setString(3, "2");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "booking1");
        smt5.setString(2, "2020-04-11 18:12:00");
        smt5.setString(3, "2020-04-11");
        smt5.setString(4, "20:00:00");
        smt5.setString(5, "21:00:00");
        smt5.setString(6, "1");
        smt5.setString(7, "2");
        smt5.setString(8, "A");
        smt5.setString(9, "B");
        smt5.setString(10, "0");


        smt5.execute();

        //actual test
        String result_code = SQLStatement.updateBookingStatus('1', "booking1", "3", "2", "S");
        assertEquals("UBS-005", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenCenterIdIsNotExisted() throws Exception {

        // clean up some data
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = ConnectionDB.getInstance().getConnection().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql6 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt6 = ConnectionDB.getInstance().getConnection().prepareStatement(sql6);

        smt6.setString(1, "S");
        smt6.setString(2, "1");
        smt6.setString(3, "2");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "booking1");
        smt5.setString(2, "2020-04-11 18:12:00");
        smt5.setString(3, "2020-04-11");
        smt5.setString(4, "20:00:00");
        smt5.setString(5, "21:00:00");
        smt5.setString(6, "1");
        smt5.setString(7, "2");
        smt5.setString(8, "A");
        smt5.setString(9, "B");
        smt5.setString(10, "0");


        smt5.execute();
        //actual test
        String result_code = SQLStatement.updateBookingStatus('1', "booking1", "1", "404", "S");
        assertEquals("UBS-006", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenstaffIdDoesNotManageIncityIdcourtId() throws Exception {

        // clean up some data
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = ConnectionDB.getInstance().getConnection().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql6 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt6 = ConnectionDB.getInstance().getConnection().prepareStatement(sql6);

        smt6.setString(1, "S");
        smt6.setString(2, "1");
        smt6.setString(3, "2");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "booking1");
        smt5.setString(2, "2020-04-11 18:12:00");
        smt5.setString(3, "2020-04-11");
        smt5.setString(4, "20:00:00");
        smt5.setString(5, "21:00:00");
        smt5.setString(6, "1");
        smt5.setString(7, "2");
        smt5.setString(8, "A");
        smt5.setString(9, "B");
        smt5.setString(10, "0");


        smt5.execute();

        //actual test
        String result_code = SQLStatement.updateBookingStatus('1', "booking1", "1", "2", "S2");
        assertEquals("UBS-007", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_bookingIdDoesNotBelongTocityIdcenterId() throws Exception {

        // clean up some data
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = ConnectionDB.getInstance().getConnection().prepareStatement(sql2);

        smt2.setString(1, "1");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = ConnectionDB.getInstance().getConnection().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "1");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = ConnectionDB.getInstance().getConnection().prepareStatement(sql4);

        smt4.setString(1, "C");

        smt4.execute();

        String sql6 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt6 = ConnectionDB.getInstance().getConnection().prepareStatement(sql6);

        smt6.setString(1, "S");
        smt6.setString(2, "1");
        smt6.setString(3, "1");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = ConnectionDB.getInstance().getConnection().prepareStatement(sql5);

        smt5.setString(1, "booking1");
        smt5.setString(2, "2020-04-11 18:12:00");
        smt5.setString(3, "2020-04-11");
        smt5.setString(4, "20:00:00");
        smt5.setString(5, "21:00:00");
        smt5.setString(6, "1");
        smt5.setString(7, "1");
        smt5.setString(8, "A");
        smt5.setString(9, "C");
        smt5.setString(10, "0");


        smt5.execute();

        String sql7 = "INSERT INTO city VALUE (?)";

        PreparedStatement smt7 = ConnectionDB.getInstance().getConnection().prepareStatement(sql7);

        smt7.setString(1, "2");

        smt7.execute();

        String sql8 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt8 = ConnectionDB.getInstance().getConnection().prepareStatement(sql8);

        smt8.setString(1, "2");
        smt8.setString(2, "2");

        smt8.execute();

        String sql9 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt9 = ConnectionDB.getInstance().getConnection().prepareStatement(sql9);

        smt9.setString(1, "B");
        smt9.setString(2, "2");
        smt9.setString(3, "2");

        smt9.execute();

        String sql10 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt10 = ConnectionDB.getInstance().getConnection().prepareStatement(sql10);

        smt10.setString(1, "S2");
        smt10.setString(2, "2");
        smt10.setString(3, "2");

        smt10.execute();

        //actual test
        String result_code = SQLStatement.updateBookingStatus('1', "booking1", "2", "2", "S2");
        assertEquals("UBS-008", result_code, "Pass.");
    }*/

}

