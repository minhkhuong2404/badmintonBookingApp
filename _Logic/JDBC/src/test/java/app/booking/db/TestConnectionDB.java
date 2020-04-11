package app.booking.db;

import org.junit.jupiter.api.Test;
import java.sql.*;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestConnectionDB {

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
        db.createCity("E");
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
        db.createCity("1");
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
        db.createCity("1");
        db.createCityCenter("A", "1");
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
        db.createCity("1");
        db.createCityCenter("2", "1");
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
        db.createCity("1");
        db.createCityCenter("2", "1");
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
        db.createCity("1");
        db.createCityCenter("2", "1");
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
        db.createCity("1");
        db.createCityCenter("2", "1");
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
        db.createCity("1");
        db.createCityCenter("2", "1");
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
        db.createCity("1");
        db.createCityCenter("2", "1");
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
        db.createCity("1");
        db.createCityCenter("2", "1");
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
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
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
        db.createPlayer("A");
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
        db.createCity("1");
        db.createCityCenter("2", "1");
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
        db.createCity("1");
        db.createCityCenter("2", "1");
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
        db.createCity("1");
        db.createCityCenter("2", "1");
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
        db.createCity("1");
        db.createCityCenter("2", "1");
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
        db.createCity("1");
        db.createCityCenter("2", "1");
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
        db.createCity("1");
        db.createCityCenter("2", "1");
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
        db.createCity("1");
        db.createCityCenter("2", "1");
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
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createStaff("A", "1", "2");
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
        db.cleanTable("booking");
        // scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");
        // actual test
        String result_code = db.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:00:00"), Time.valueOf("10:45:00"), "1", "2", "A", "B");
        assertEquals("200", result_code, "Pass.");
    }

    @Test
    public void createBooking_failWhenBookingIdIsNotAlphanumeric() throws Exception {
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");
        //actual test
        String result_code = db.createBooking("#booking", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");
        assertEquals("CB-000", result_code, "Pass.");
    }

    @Test
    public void create_Booking_fail_when_bookingID_is_existed() throws  Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");
        //actual test
        String result_code = db.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");
        assertEquals("CB-100", result_code, "Pass.");
    }

    @Test
    public void create_booking_fail_when_cityID_is_not_existed() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");
        //actual test
        String result_code = db.createBooking("booking12", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "2", "2", "A", "B");
        assertEquals("CB-001", result_code, "Pass.");
    }

    @Test
    public void create_booking_fail_when_centerID_is_not_existed()throws  Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");
        //actual test
        String result_code = db.createBooking("booking12", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "3", "A", "B");
        assertEquals("CB-002", result_code, "Pass.");
    }

    @Test
    public void create_Booking_failWhenCourtIdIsNotExisted() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");
        //actual test
        String result_code = db.createBooking("booking12", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "C", "B");
        assertEquals("CB-003", result_code, "Pass.");
    }

    @Test
    public void create_Booking_failWhenPlayerIdIsNotExisted() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");
        //actual test
        String result_code = db.createBooking("booking12", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "D");
        assertEquals("CB-004", result_code, "Pass.");
    }

    @Test
    public void create_booking_failWhenStartTime_less_than_Date() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");
        //actual test
        String result_code = db.createBooking("booking2", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2020-03-01"), Time.valueOf("10:10:00"), Time.valueOf("18:35:00"), "1", "2", "A", "B");
        assertEquals("CB-005", result_code, "Pass.");
    }

    @Test
    public void create_booking_failWhenStartTime_less_than_openTime() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");
        //actual test
        String result_code = db.createBooking("booking2", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2020-05-01"), Time.valueOf("06:00:00"), Time.valueOf("07:00:00"), "1", "2", "A", "B");
        assertEquals("CB-006", result_code, "Pass.");
    }

    @Test
    public void create_booking_failWhenEndTime_longer_than_closeTime() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");
        //actual test
        String result_code = db.createBooking("booking2", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2020-05-01"), Time.valueOf("07:10:00"), Time.valueOf("21:30:00"), "1", "2", "A", "B");
        assertEquals("CB-007", result_code, "Pass.");
    }

    @Test
    public void create_booking_failWhenEndTime_less_than_startTime() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");
        //actual test
        String result_code = db.createBooking("booking2", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2020-05-01"), Time.valueOf("08:00:00"), Time.valueOf("07:00:00"), "1", "2", "A", "B");
        assertEquals("CB-008", result_code, "Pass.");
    }

    @Test
    public void create_booking_failWhenPlayTimeIsInvalid() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");
        //actual test
        String result_code = db.createBooking("booking2", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:00:00"), Time.valueOf("10:30:00"), "1", "2", "A", "B");
        assertEquals("CB-009", result_code, "Pass.");
    }

    @Test
    public void create_booking_failWhenOverlappedWithOtherBookings() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");
        //actual test
        String result_code = db.createBooking("booking2", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");
        assertEquals("CB-010", result_code, "Pass.");
    }

    @Test
    public void createBooking_failWhenPlayerIdHavePendingBooking() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");

        db.createBooking("pending1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2020-04-01"), Time.valueOf("10:00:00"), Time.valueOf("10:45:00"), "1", "2", "A", "B");

        //actual test
        String result_code = db.createBooking("booking2", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-02"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");
        assertEquals("CB-011", result_code, "Pass.");
    }

    @Test
    public void create_booking_failWhenPlayerIdHaveNoMoreThan3Booking() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");

        db.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2020-05-03"), Time.valueOf("10:00:00"), Time.valueOf("10:45:00"), "1", "2", "A", "B");
        db.createBooking("booking2", Timestamp.valueOf("2020-04-08 09:27:18"), Date.valueOf("2020-05-04"), Time.valueOf("10:00:00"), Time.valueOf("10:45:00"), "1", "2", "A", "B");
        db.createBooking("booking3", Timestamp.valueOf("2020-04-09 09:27:18"), Date.valueOf("2020-05-05"), Time.valueOf("10:00:00"), Time.valueOf("10:45:00"), "1", "2", "A", "B");

        //actual test
        String result_code = db.createBooking("booking4", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-06"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "E");
        assertEquals("CB-012", result_code, "Pass.");
    }

    //test cancel booking
    @Test
    public void cancel_booking_failWhenBookingIdIsInvalid() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");
        //actual test
        String result_code = db.cancelBooking("#booking","B");
        assertEquals("CA-000", result_code, "Pass.");
    }

    @Test
    public void cancel_booking_failWhenPlayerIdIsInvalid() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");
        //actual test
        String result_code = db.cancelBooking("booking1","#B");
        assertEquals("CA-001", result_code, "Pass.");
    }

    @Test
    public void cancel_booking_failWhenPlayerIdIsNotExited() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");

        db.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");

        //actual test
        String result_code = db.cancelBooking("booking1","C");
        assertEquals("CA-002", result_code, "Pass.");
    }

    @Test
    public void cancel_booking_failWhenBookingIdIsNotExisted() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");

        db.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");
        //actual test
        String result_code = db.cancelBooking("booking2","B");
        assertEquals("CA-003", result_code, "Pass.");
    }

    @Test
    public void cancel_booking_failWhenPlayerIdDoesNotOwnTheBooking() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");
        db.createPlayer("C");

        db.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");
        //actual test
        String result_code = db.cancelBooking("booking1","C");
        assertEquals("CA-004", result_code, "Pass.");
    }

    @Test
    public void cancel_booking_failWhenViolating24HoursBeforeStartTime() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up database
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createPlayer("B");

        db.createBooking("booking1", Timestamp.valueOf("2020-04-07 18:04:00"), Date.valueOf("2020-04-06"), Time.valueOf("07:00:00"), Time.valueOf("08:00:00"), "1", "2", "A", "B");
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
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createStaff("S","1","2");
        db.createPlayer("B");

        db.createBooking("booking1", Timestamp.valueOf("2020-04-07 18:04:00"), Date.valueOf("2020-04-15"), Time.valueOf("09:00:00"), Time.valueOf("10:00:00"), "1", "2", "A", "B");
        //actual test
        String result_code = db.updateBookingStatus("1","booking1","1","2","S");
        assertEquals("200", result_code, "Pass.");
    }

    @Test
    public void updateBooingStatus_failWhenBookingIdIsInvalid() throws  Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up some data
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createStaff("S","1","2");
        db.createPlayer("B");

        db.createBooking("booking1", Timestamp.valueOf("2020-04-07 18:04:00"), Date.valueOf("2020-04-15"), Time.valueOf("09:00:00"), Time.valueOf("10:00:00"), "1", "2", "A", "B");
        //actual test
        String result_code = db.updateBookingStatus("1","#booking","1","2","S");
        assertEquals("UBS-000", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_failWhenCityIdIsInvalid() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up some data
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createStaff("S","1","2");
        db.createPlayer("B");

        db.createBooking("booking1", Timestamp.valueOf("2020-04-07 18:04:00"), Date.valueOf("2020-04-15"), Time.valueOf("09:00:00"), Time.valueOf("10:00:00"), "1", "2", "A", "B");
        //actual test
        String result_code = db.updateBookingStatus("1","booking1","#","2","S");
        assertEquals("UBS-001", result_code, "Pass.");
    }

    @Test
    public void undateBookingStatus_failWhenCenterIdIsInvalid() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up some data
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createStaff("S","1","2");
        db.createPlayer("B");

        db.createBooking("booking1", Timestamp.valueOf("2020-04-07 18:04:00"), Date.valueOf("2020-04-15"), Time.valueOf("09:00:00"), Time.valueOf("10:00:00"), "1", "2", "A", "B");
        //actual test
        String result_code = db.updateBookingStatus("1","booking1","1","#","S");
        assertEquals("UBS-002", result_code, "Pass.");
    }

    @Test
    public void undateBookingStatus_failWhenStaffIdIsInvalid() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up some data
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createStaff("S","1","2");
        db.createPlayer("B");

        db.createBooking("booking1", Timestamp.valueOf("2020-04-07 18:04:00"), Date.valueOf("2020-04-15"), Time.valueOf("09:00:00"), Time.valueOf("10:00:00"), "1", "2", "A", "B");
        //actual test
        String result_code = db.updateBookingStatus("1","booking1","1","2","#");
        assertEquals("UBS-003", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_failWhenBookingIdIsNotExisted() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up some data
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createStaff("S","1","2");
        db.createPlayer("B");

        db.createBooking("booking1", Timestamp.valueOf("2020-04-07 18:04:00"), Date.valueOf("2020-04-15"), Time.valueOf("09:00:00"), Time.valueOf("10:00:00"), "1", "2", "A", "B");
        //actual test
        String result_code = db.updateBookingStatus("1","booking2","1","2","S");
        assertEquals("UBS-004", result_code, "Pass.");
    }

    @Test
    public void undateBookingStatus_failWhenCityIdIsNotExisted() throws Exception{
        ConnectionDB db = new ConnectionDB();
        // clean up some data
        db.cleanTable("city");
        db.cleanTable("center");
        db.cleanTable("staff");
        db.cleanTable("booking");
        //scenario
        db.createCity("1");
        db.createCityCenter("2", "1");
        db.createCityCenterCourt("A", "1", "2");
        db.createStaff("S","1","2");
        db.createPlayer("B");

        db.createBooking("booking1", Timestamp.valueOf("2020-04-07 18:04:00"), Date.valueOf("2020-04-15"), Time.valueOf("09:00:00"), Time.valueOf("10:00:00"), "1", "2", "A", "B");
        //actual test
        String result_code = db.updateBookingStatus("1","booking1","3","2","S");
        assertEquals("UBS-005", result_code, "Pass.");
    }
}
