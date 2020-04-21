package app.booking.db;

import org.junit.jupiter.api.Test;
import java.sql.*;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestConnectionDBv2 {

    // createCity

    @Test
    public void createCity_Success() throws Exception {
        // initialize the connection to database
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        // scenario

        // actual test
        String result_code = db.createCity("D");
        assertEquals("200", result_code, "Pass.");
    }

    @Test
    public void createCity_Invalid() throws Exception {
        // initialize the connection to database
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        // scenario

        // actual test
        String result_code = db.createCity("#");
        assertEquals("CITY-000", result_code, "Pass.");
    }

    @Test
    public void createCity_Existed() throws Exception {
        // initialize the connection to database
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        // scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "E");

        smt.execute();

        // actual test
        String result_code = db.createCity("E");
        assertEquals("CITY-001", result_code, "Pass.");
    }

    // createCityCenter

    @Test
    public void createCityCenter_Success() throws Exception {
        // initialize the connection to database
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        // scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        // actual test
        String result_code = db.createCityCenter("A", "1");
        assertEquals("200", result_code, "Pass.");
    }

    @Test
    public void createCityCenter_CityInvalid() throws Exception {
        // initialize the connection to database
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        // scenario

        // actual test
        String result_code = db.createCityCenter("A", "#");
        assertEquals("CEN-000", result_code, "Pass.");
    }

    @Test
    public void createCityCenter_CenterInvalid() throws Exception {
        // initialize the connection to database
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        // scenario

        // actual test
        String result_code = db.createCityCenter("#", "A");
        assertEquals("CEN-001", result_code, "Pass.");
    }

    @Test
    public void createCityCenter_CityNotExist() throws Exception {
        // initialize the connection to database
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        // scenario

        // actual test
        String result_code = db.createCityCenter("A", "A");
        // check the result
        assertEquals("CEN-002", result_code, "Pass.");
    }

    @Test
    public void createCityCenter_CenterExisted() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        // scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "A");
        smt2.setString(2, "1");

        smt2.execute();

        // actual test
        String result_code = db.createCityCenter("A", "1");
        assertEquals("CEN-003", result_code, "Pass.");
    }

    // createCityCenterCourt

    @Test
    public void createCityCenterCourt_Success() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("court");
        // scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();
        // actual test
        String result_code = db.createCityCenterCourt("A", "1", "2");
        assertEquals("200", result_code, "Pass.");
    }

    @Test
    public void createCityCenterCourt_cityIdIsInvalid() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("court");
        // scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        // actual test
        String result_code = db.createCityCenterCourt("A", "#", "2");
        assertEquals("CRT-000", result_code, "Pass.");
    }

    @Test
    public void createCityCenterCourt_centerIdIsInvalid() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("court");
        // scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        // actual test
        String result_code = db.createCityCenterCourt("A", "1", "#");
        assertEquals("CRT-001", result_code, "Pass.");
    }

    @Test
    public void createCityCenterCourt_courtIdIsInvalid() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("court");
        // scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        // actual test
        String result_code = db.createCityCenterCourt("#", "1", "2");
        assertEquals("CRT-002", result_code, "Pass.");
    }

    @Test
    public void createCityCenterCourt_centerIdIsExistedButcityIdIsNotExisted() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("court");
        // scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        // actual test
        String result_code = db.createCityCenterCourt("A", "404", "2");
        assertEquals("CRT-003", result_code, "Pass.");
    }

    @Test
    public void createCityCenterCourt_centerIdIsNotExistedButcityIdIsExisted() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("court");
        // scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        // actual test
        String result_code = db.createCityCenterCourt("A", "1", "404");
        assertEquals("CRT-004", result_code, "Pass.");
    }

    @Test
    public void createCityCenterCourt_BothcenterIdAndcityIdAreNotExisted() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("court");
        // scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        // actual test
        String result_code = db.createCityCenterCourt("A", "404", "404");
        assertEquals("CRT-003", result_code, "Pass.");
    }

    @Test
    public void createCityCenterCourt_courtIdIsExisted() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("court");
        // scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        // actual test
        String result_code = db.createCityCenterCourt("A", "1", "2");
        assertEquals("CRT-005", result_code, "Pass.");
    }

    // createPlayer
    @Test
    public void createPlayer_Success() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("player");
        // scenario

        // actual test
        String result_code = db.createPlayer("A");
        assertEquals("200", result_code, "Pass.");
    }

    @Test
    public void createPlayer_playerIdIsInvalid() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("player");
        // scenario

        // actual test
        String result_code = db.createPlayer("#A");
        assertEquals("CPL-000", result_code, "Pass.");
    }

    @Test
    public void createPlayer_playerIdIsExisted() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("player");
        // scenario
        String sql = "INSERT INTO player VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "A");

        smt.execute();

        // actual test
        String result_code = db.createPlayer("A");
        assertEquals("CPL-001", result_code, "Pass.");
    }

    // createStaff

    @Test
    public void createStaff_Success() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        // scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        // actual test
        String result_code = db.createStaff("A", "1", "2");
        assertEquals("200", result_code, "Pass.");
    }

    @Test
    public void createStaff_cityIdIsInvalid() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        // scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();
        // actual test
        String result_code = db.createStaff("A", "#", "2");
        assertEquals("CS-000", result_code, "Pass.");
    }

    @Test
    public void createStaff_centerIdIsInvalid() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        // scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        // actual test
        String result_code = db.createStaff("A", "1", "#");
        assertEquals("CS-001", result_code, "Pass.");
    }

    @Test
    public void createStaff_staffIdIsInvalid() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        // scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        // actual test
        String result_code = db.createStaff("#", "1", "2");
        assertEquals("CS-002", result_code, "Pass.");
    }

    @Test
    public void createStaff_centerIdIsExistedButcityIdIsNotExisted() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        // scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        // actual test
        String result_code = db.createStaff("A", "404", "2");
        assertEquals("CS-003", result_code, "Pass.");
    }

    @Test
    public void createStaff_centerIdIsNotExistedButcityIdIsExisted() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        // scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        // actual test
        String result_code = db.createStaff("A", "1", "404");
        assertEquals("CS-004", result_code, "Pass.");
    }

    @Test
    public void createStaff_BothcenterIdAndcityIdIsNotExisted() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        // scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        // actual test
        String result_code = db.createStaff("A", "404", "404");
        assertEquals("CS-003", result_code, "Pass.");
    }

    @Test
    public void createStaff_staffIdIsExisted() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        // scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        // actual test
        String result_code = db.createStaff("A", "1", "2");
        assertEquals("CS-005", result_code, "Pass.");
    }

    // createBooking
    @Test
    public void createBooking_Success() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        // scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        // actual test
        String result_code = db.createBooking(
                "booking1",
                Timestamp.valueOf("2020-04-07 09:27:18"),
                Date.valueOf("2021-05-01"),
                Time.valueOf("10:00:00"),
                Time.valueOf("10:45:00"),
                "1",
                "2",
                "A",
                "B");
        assertEquals("200", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenBookingIdIsNotAlphanumeric() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        db.cleanTable("player");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        //actual test
        String result_code = db.createBooking("#booking", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");
        assertEquals("CB-000", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenbookingIdIsExisted() throws  Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = db.getConnect().prepareStatement(sql5);

        smt5.setString(1, "booking1");
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
        String result_code = db.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");
        assertEquals("CB-100", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhencityIdIsNotexisted() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        //actual test
        String result_code = db.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "2", "2", "A", "B");
        assertEquals("CB-001", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhencenterIdIsNotExisted()throws  Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        //actual test
        String result_code = db.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "3", "A", "B");
        assertEquals("CB-002", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenCourtIdIsNotExisted() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();


        //actual test
        String result_code = db.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "C", "B");
        assertEquals("CB-003", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenPlayerIdIsNotExisted() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();


        //actual test
        String result_code = db.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "D");
        assertEquals("CB-004", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenStartTimeLessThanDateNow() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();


        //actual test
        String result_code = db.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2020-03-01"), Time.valueOf("10:10:00"), Time.valueOf("18:35:00"), "1", "2", "A", "B");
        assertEquals("CB-005", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenStartTimeLessThanOpenTime() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();


        //actual test
        String result_code = db.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2020-05-01"), Time.valueOf("06:00:00"), Time.valueOf("07:00:00"), "1", "2", "A", "B");
        assertEquals("CB-006", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenEndTimeLongerThanCloseTime() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();


        //actual test
        String result_code = db.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2020-05-01"), Time.valueOf("07:10:00"), Time.valueOf("21:30:00"), "1", "2", "A", "B");
        assertEquals("CB-007", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenEndTimeLessThanStartTime() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();


        //actual test
        String result_code = db.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2020-05-01"), Time.valueOf("08:00:00"), Time.valueOf("07:00:00"), "1", "2", "A", "B");
        assertEquals("CB-008", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenPlayTimeIsInvalid() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        //actual test
        String result_code = db.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:00:00"), Time.valueOf("10:30:00"), "1", "2", "A", "B");
        assertEquals("CB-009", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenOverlappedWithOtherBookings() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = db.getConnect().prepareStatement(sql5);

        smt5.setString(1, "booking1");
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
        String result_code1 = db.createBooking("booking2", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");
        String result_code2 = db.createBooking("booking3", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("09:30:00"), Time.valueOf("10:30:00"), "1", "2", "A", "B");
        String result_code3 = db.createBooking("booking4", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("09:45:00"), Time.valueOf("11:00:00"), "1", "2", "A", "B");
        String result_code4 = db.createBooking("booking5", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:00:00"), Time.valueOf("10:45:00"), "1", "2", "A", "B");
        assertEquals("CB-010", result_code1, "Pass.");
        assertEquals("CB-010", result_code2, "Pass.");
        assertEquals("CB-010", result_code3, "Pass.");
        assertEquals("CB-010", result_code4, "Pass.");
    }

    @Test
    public void createBooking_WhenPlayerIdHavePendingBooking() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createStaff("A", "1", "2");
        db.createPlayer("B");
        String sql = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "pending1");
        smt.setString(2, "2020-04-07 18:04:00");
        smt.setString(3, "2019-04-15");
        smt.setString(4, "09:00:00");
        smt.setString(5, "10:00:00");
        smt.setString(6, "1");
        smt.setString(7, "2");
        smt.setString(8, "A");
        smt.setString(9, "B");
        smt.setString(10, "0");


        smt.execute();

        //actual test
        String result_code = db.createBooking("1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2020-05-03"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");
        assertEquals("CB-011", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenPlayerIdHaveNoMoreThan3Booking() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = db.getConnect().prepareStatement(sql5);

        smt5.setString(1, "booking1");
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

        PreparedStatement smt6 = db.getConnect().prepareStatement(sql6);

        smt6.setString(1, "booking2");
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

        PreparedStatement smt7 = db.getConnect().prepareStatement(sql7);

        smt7.setString(1, "booking3");
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
        String result_code = db.createBooking("booking4", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-06"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");
        assertEquals("CB-012", result_code, "Pass.");
    }

    //test cancel booking
    @Test
    public void cancelBooking_WhenBookingIdIsInvalid() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        //actual test
        String result_code = db.cancelBooking("#booking","B");
        assertEquals("CA-000", result_code, "Pass.");
    }

    @Test
    public void cancelBooking_WhenPlayerIdIsInvalid() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = db.getConnect().prepareStatement(sql5);

        smt5.setString(1, "booking1");
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
        String result_code = db.cancelBooking("booking1","#B");
        assertEquals("CA-001", result_code, "Pass.");
    }

    @Test
    public void cancelBooking_WhenPlayerIdIsNotExited() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = db.getConnect().prepareStatement(sql5);

        smt5.setString(1, "booking1");
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
        String result_code = db.cancelBooking("booking1","C");
        assertEquals("CA-002", result_code, "Pass.");
    }

    @Test
    public void cancelBooking_WhenBookingIdIsNotExisted() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = db.getConnect().prepareStatement(sql5);

        smt5.setString(1, "booking1");
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
        String result_code = db.cancelBooking("booking2","B");
        assertEquals("CA-003", result_code, "Pass.");
    }

    @Test
    public void cancelBooking_WhenPlayerIdDoesNotOwnTheBooking() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("Player");
        db.cleanTable("booking");
        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql6 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt6 = db.getConnect().prepareStatement(sql6);

        smt6.setString(1, "C");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = db.getConnect().prepareStatement(sql5);

        smt5.setString(1, "booking1");
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
        String result_code = db.cancelBooking("booking1","C");
        assertEquals("CA-004", result_code, "Pass.");
    }

    @Test
    public void cancelBooking_WhenViolating24HoursBeforeStartTime() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = db.getConnect().prepareStatement(sql5);

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
        String result_code = db.cancelBooking("booking1","B");
        assertEquals("CA-005", result_code, "Pass.");
    }

    //test update booking
    @Test
    public void updateBookingStatus_success() throws  Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up some data
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql6 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt6 = db.getConnect().prepareStatement(sql6);

        smt6.setString(1, "S");
        smt6.setString(2, "1");
        smt6.setString(3, "2");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = db.getConnect().prepareStatement(sql5);

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
        String result_code = db.updateBookingStatus('1',"booking1","1","2","S");
        assertEquals("200", result_code, "Pass.");
    }

    @Test
    public void updateBooingStatus_WhenBookingIdIsInvalid() throws  Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up some data
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql6 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt6 = db.getConnect().prepareStatement(sql6);

        smt6.setString(1, "S");
        smt6.setString(2, "1");
        smt6.setString(3, "2");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = db.getConnect().prepareStatement(sql5);

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
        String result_code = db.updateBookingStatus('1',"#booking","1","2","S");
        assertEquals("UBS-000", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenCityIdIsInvalid() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up some data
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql6 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt6 = db.getConnect().prepareStatement(sql6);

        smt6.setString(1, "S");
        smt6.setString(2, "1");
        smt6.setString(3, "2");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = db.getConnect().prepareStatement(sql5);

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
        String result_code = db.updateBookingStatus('1',"booking1","#","2","S");
        assertEquals("UBS-001", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenCenterIdIsInvalid() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up some data
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql6 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt6 = db.getConnect().prepareStatement(sql6);

        smt6.setString(1, "S");
        smt6.setString(2, "1");
        smt6.setString(3, "2");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = db.getConnect().prepareStatement(sql5);

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
        String result_code = db.updateBookingStatus('1',"booking1","1","#","S");
        assertEquals("UBS-002", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenStaffIdIsInvalid() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up some data
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql6 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt6 = db.getConnect().prepareStatement(sql6);

        smt6.setString(1, "S");
        smt6.setString(2, "1");
        smt6.setString(3, "2");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = db.getConnect().prepareStatement(sql5);

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
        String result_code = db.updateBookingStatus('1',"booking1","1","2","#");
        assertEquals("UBS-003", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenBookingIdIsNotExisted() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up some data
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql6 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt6 = db.getConnect().prepareStatement(sql6);

        smt6.setString(1, "S");
        smt6.setString(2, "1");
        smt6.setString(3, "2");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = db.getConnect().prepareStatement(sql5);

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
        String result_code = db.updateBookingStatus('1',"booking2","1","2","S");
        assertEquals("UBS-004", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenCityIdIsNotExisted() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up some data
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql6 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt6 = db.getConnect().prepareStatement(sql6);

        smt6.setString(1, "S");
        smt6.setString(2, "1");
        smt6.setString(3, "2");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = db.getConnect().prepareStatement(sql5);

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
        String result_code = db.updateBookingStatus('1',"booking1","3","2","S");
        assertEquals("UBS-005", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenCenterIdIsNotExisted() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up some data
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql6 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt6 = db.getConnect().prepareStatement(sql6);

        smt6.setString(1, "S");
        smt6.setString(2, "1");
        smt6.setString(3, "2");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = db.getConnect().prepareStatement(sql5);

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
        String result_code = db.updateBookingStatus('1',"booking1","1","404","S");
        assertEquals("UBS-006", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenstaffIdDoesNotManageIncityIdcourtId() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up some data
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario
        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "2");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "2");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "B");

        smt4.execute();

        String sql6 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt6 = db.getConnect().prepareStatement(sql6);

        smt6.setString(1, "S");
        smt6.setString(2, "1");
        smt6.setString(3, "2");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = db.getConnect().prepareStatement(sql5);

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
        String result_code = db.updateBookingStatus('1',"booking1","1","2","S2");
        assertEquals("UBS-007", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_bookingIdDoesNotBelongTocityIdcenterId() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up some data
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("player");
        db.cleanTable("booking");
        //scenario

        String sql = "INSERT INTO city VALUE (?)";

        PreparedStatement smt = db.getConnect().prepareStatement(sql);

        smt.setString(1, "1");

        smt.execute();

        String sql2 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt2 = db.getConnect().prepareStatement(sql2);

        smt2.setString(1, "1");
        smt2.setString(2, "1");

        smt2.execute();

        String sql3 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt3 = db.getConnect().prepareStatement(sql3);

        smt3.setString(1, "A");
        smt3.setString(2, "1");
        smt3.setString(3, "1");

        smt3.execute();

        String sql4 = "INSERT INTO player VALUE (?)";

        PreparedStatement smt4 = db.getConnect().prepareStatement(sql4);

        smt4.setString(1, "C");

        smt4.execute();

        String sql6 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt6 = db.getConnect().prepareStatement(sql6);

        smt6.setString(1, "S");
        smt6.setString(2, "1");
        smt6.setString(3, "1");

        smt6.execute();

        String sql5 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt5 = db.getConnect().prepareStatement(sql5);

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

        PreparedStatement smt7 = db.getConnect().prepareStatement(sql7);

        smt7.setString(1, "2");

        smt7.execute();

        String sql8 = "INSERT INTO center VALUE (?, ?)";

        PreparedStatement smt8 = db.getConnect().prepareStatement(sql8);

        smt8.setString(1, "2");
        smt8.setString(2, "2");

        smt8.execute();

        String sql9 = "INSERT INTO court VALUE (?, ?, ?)";

        PreparedStatement smt9 = db.getConnect().prepareStatement(sql9);

        smt9.setString(1, "B");
        smt9.setString(2, "2");
        smt9.setString(3, "2");

        smt9.execute();

        String sql10 = "INSERT INTO staff VALUE (?, ?, ?)";

        PreparedStatement smt10 = db.getConnect().prepareStatement(sql10);

        smt10.setString(1, "S2");
        smt10.setString(2, "2");
        smt10.setString(3, "2");

        smt10.execute();

        //actual test
        String result_code = db.updateBookingStatus('1',"booking1","2","2","S2");
        assertEquals("UBS-008", result_code, "Pass.");
    }

}

