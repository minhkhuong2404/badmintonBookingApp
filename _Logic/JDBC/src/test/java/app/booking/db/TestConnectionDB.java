package app.booking.db;

import org.junit.jupiter.api.Test;
import java.sql.*;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestConnectionDB {

    /*

    // createCity

    @Test
    public void createCity_Success() throws Exception {
        
        
        // clean up database
        SQLStatement.cleanTable("city");
        // scenario

        // actual test
        String result_code = SQLStatement.createCity("D");
        assertEquals("200", result_code, "Pass.");
    }

    @Test
    public void createCity_Invalid() throws Exception {
        
        
        // clean up database
        SQLStatement.cleanTable("city");
        // scenario

        // actual test
        String result_code = SQLStatement.createCity("#");
        assertEquals("CITY-000", result_code, "Pass.");
    }

    @Test
    public void createCity_Existed() throws Exception {
        
        
        // clean up database
        SQLStatement.cleanTable("city");
        // scenario
         SQLStatement.createCity("E");
        // actual test
        String result_code = SQLStatement.createCity("E");
        assertEquals("CITY-001", result_code, "Pass.");
    }

    // createCityCenter

    @Test
    public void createCityCenter_Success() throws Exception {
        
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        // scenario
        SQLStatement.createCity("1");
        // actual test
        String result_code = SQLStatement.createCityCenter("A", "1");
        assertEquals("200", result_code, "Pass.");
    }

    @Test
    public void createCityCenter_CityInvalid() throws Exception {
        
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        // scenario

        // actual test
        String result_code = SQLStatement.createCityCenter("A", "#");
        assertEquals("CEN-000", result_code, "Pass.");
    }

    @Test
    public void createCityCenter_CenterInvalid() throws Exception {
        
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        // scenario

        // actual test
        String result_code = SQLStatement.createCityCenter("#", "A");
        assertEquals("CEN-001", result_code, "Pass.");
    }

    @Test
    public void createCityCenter_CityNotExist() throws Exception {
        
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        // scenario

        // actual test
        String result_code = SQLStatement.createCityCenter("A", "A");
        // check the result
        assertEquals("CEN-002", result_code, "Pass.");
    }

    @Test
    public void createCityCenter_CenterExisted() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        // scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("A", "1");
        // actual test
        String result_code = SQLStatement.createCityCenter("A", "1");
        assertEquals("CEN-003", result_code, "Pass.");
    }

    // createCityCenterCourt

    @Test
    public void createCityCenterCourt_Success() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("court");
        // scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        // actual test
        String result_code = SQLStatement.createCityCenterCourt("A", "1", "2");
        assertEquals("200", result_code, "Pass.");
    }

    @Test
    public void createCityCenterCourt_cityIdIsInvalid() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("court");
        // scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        // actual test
        String result_code = SQLStatement.createCityCenterCourt("A", "#", "2");
        assertEquals("CRT-000", result_code, "Pass.");
    }

    @Test
    public void createCityCenterCourt_centerIdIsInvalid() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("court");
        // scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        // actual test
        String result_code = SQLStatement.createCityCenterCourt("A", "1", "#");
        assertEquals("CRT-001", result_code, "Pass.");
    }

    @Test
    public void createCityCenterCourt_courtIdIsInvalid() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("court");
        // scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        // actual test
        String result_code = SQLStatement.createCityCenterCourt("#", "1", "2");
        assertEquals("CRT-002", result_code, "Pass.");
    }

    @Test
    public void createCityCenterCourt_centerIdIsExistedButcityIdIsNotExisted() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("court");
        // scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        // actual test
        String result_code = SQLStatement.createCityCenterCourt("A", "404", "2");
        assertEquals("CRT-003", result_code, "Pass.");
    }

    @Test
    public void createCityCenterCourt_centerIdIsNotExistedButcityIdIsExisted() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("court");
        // scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        // actual test
        String result_code = SQLStatement.createCityCenterCourt("A", "1", "404");
        assertEquals("CRT-004", result_code, "Pass.");
    }

    @Test
    public void createCityCenterCourt_BothcenterIdAndcityIdAreNotExisted() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("court");
        // scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        // actual test
        String result_code = SQLStatement.createCityCenterCourt("A", "404", "404");
        assertEquals("CRT-003", result_code, "Pass.");
    }

    @Test
    public void createCityCenterCourt_courtIdIsExisted() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("court");
        // scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        // actual test
        String result_code = SQLStatement.createCityCenterCourt("A", "1", "2");
        assertEquals("CRT-005", result_code, "Pass.");
    }

    // createPlayer
    @Test
    public void createPlayer_Success() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("player");
        // scenario

        // actual test
        String result_code = SQLStatement.createPlayer("A");
        assertEquals("200", result_code, "Pass.");
    }

    @Test
    public void createPlayer_playerIdIsInvalid() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("player");
        // scenario

        // actual test
        String result_code = SQLStatement.createPlayer("#A");
        assertEquals("CPL-000", result_code, "Pass.");
    }

    @Test
    public void createPlayer_playerIdIsExisted() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("player");
        // scenario
        SQLStatement.createPlayer("A");
        // actual test
        String result_code = SQLStatement.createPlayer("A");
        assertEquals("CPL-001", result_code, "Pass.");
    }

    // createStaff

    @Test
    public void createStaff_Success() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        // scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        // actual test
        String result_code = SQLStatement.createStaff("A", "1", "2");
        assertEquals("200", result_code, "Pass.");
    }

    @Test
    public void createStaff_cityIdIsInvalid() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        // scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        // actual test
        String result_code = SQLStatement.createStaff("A", "#", "2");
        assertEquals("CS-000", result_code, "Pass.");
    }

    @Test
    public void createStaff_centerIdIsInvalid() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        // scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        // actual test
        String result_code = SQLStatement.createStaff("A", "1", "#");
        assertEquals("CS-001", result_code, "Pass.");
    }

    @Test
    public void createStaff_staffIdIsInvalid() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        // scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        // actual test
        String result_code = SQLStatement.createStaff("#", "1", "2");
        assertEquals("CS-002", result_code, "Pass.");
    }

    @Test
    public void createStaff_centerIdIsExistedButcityIdIsNotExisted() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        // scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        // actual test
        String result_code = SQLStatement.createStaff("A", "404", "2");
        assertEquals("CS-003", result_code, "Pass.");
    }

    @Test
    public void createStaff_centerIdIsNotExistedButcityIdIsExisted() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        // scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        // actual test
        String result_code = SQLStatement.createStaff("A", "1", "404");
        assertEquals("CS-004", result_code, "Pass.");
    }

    @Test
    public void createStaff_BothcenterIdAndcityIdIsNotExisted() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        // scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        // actual test
        String result_code = SQLStatement.createStaff("A", "404", "404");
        assertEquals("CS-003", result_code, "Pass.");
    }

    @Test
    public void createStaff_staffIdIsExisted() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        // scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createStaff("A", "1", "2");
        // actual test
        String result_code = SQLStatement.createStaff("A", "1", "2");
        assertEquals("CS-005", result_code, "Pass.");
    }

    // createBooking
    @Test
    public void createBooking_Success() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        // scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createPlayer("B");
        // actual test
        String result_code = SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:00:00"), Time.valueOf("10:45:00"), "1", "2", "A", "B");
        assertEquals("200", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenBookingIdIsNotAlphanumeric() throws Exception {
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createPlayer("B");
        //actual test
        String result_code = SQLStatement.createBooking("#booking", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");
        assertEquals("CB-000", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenbookingIdIsExisted() throws  Exception{
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createPlayer("B");
        SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");
        //actual test
        String result_code = SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");
        assertEquals("CB-100", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhencityIdIsNotexisted() throws Exception{
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createPlayer("B");
        //actual test
        String result_code = SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "2", "2", "A", "B");
        assertEquals("CB-001", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhencenterIdIsNotExisted()throws  Exception{
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createPlayer("B");
        //actual test
        String result_code = SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "3", "A", "B");
        assertEquals("CB-002", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenCourtIdIsNotExisted() throws Exception{
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createPlayer("B");
        //actual test
        String result_code = SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "C", "B");
        assertEquals("CB-003", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenPlayerIdIsNotExisted() throws Exception{
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createPlayer("B");
        //actual test
        String result_code = SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "D");
        assertEquals("CB-004", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenStartTimeLessThanDateNow() throws Exception{
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createPlayer("B");
        //actual test
        String result_code = SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2020-03-01"), Time.valueOf("10:10:00"), Time.valueOf("18:35:00"), "1", "2", "A", "B");
        assertEquals("CB-005", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenStartTimeLessThanOpenTime() throws Exception{
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createPlayer("B");
        //actual test
        String result_code = SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2020-05-01"), Time.valueOf("06:00:00"), Time.valueOf("07:00:00"), "1", "2", "A", "B");
        assertEquals("CB-006", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenEndTimeLongerThanCloseTime() throws Exception{
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createPlayer("B");
        //actual test
        String result_code = SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2020-05-01"), Time.valueOf("07:10:00"), Time.valueOf("21:30:00"), "1", "2", "A", "B");
        assertEquals("CB-007", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenEndTimeLessThanStartTime() throws Exception{
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createPlayer("B");
        //actual test
        String result_code = SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2020-05-01"), Time.valueOf("08:00:00"), Time.valueOf("07:00:00"), "1", "2", "A", "B");
        assertEquals("CB-008", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenPlayTimeIsInvalid() throws Exception{
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createPlayer("B");
        //actual test
        String result_code = SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:00:00"), Time.valueOf("10:30:00"), "1", "2", "A", "B");
        assertEquals("CB-009", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenOverlappedWithOtherBookings() throws Exception{
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createPlayer("B");
        SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:00:00"), Time.valueOf("10:45:00"), "1", "2", "A", "B");
        //actual test
        String result_code1 = SQLStatement.createBooking("booking2", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");
        String result_code2 = SQLStatement.createBooking("booking3", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("09:30:00"), Time.valueOf("10:30:00"), "1", "2", "A", "B");
        String result_code3 = SQLStatement.createBooking("booking4", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("09:45:00"), Time.valueOf("11:00:00"), "1", "2", "A", "B");
        String result_code4 = SQLStatement.createBooking("booking5", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:00:00"), Time.valueOf("10:45:00"), "1", "2", "A", "B");
        assertEquals("CB-010", result_code1, "Pass.");
        assertEquals("CB-010", result_code2, "Pass.");
        assertEquals("CB-010", result_code3, "Pass.");
        assertEquals("CB-010", result_code4, "Pass.");
    }

    @Test
    public void createBooking_WhenPlayerIdHavePendingBooking() throws Exception{
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createStaff("A", "1", "2");
        SQLStatement.createPlayer("B");
        String sql = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement smt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);

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
        String result_code = SQLStatement.createBooking("1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2020-05-03"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");
        assertEquals("CB-011", result_code, "Pass.");
    }

    @Test
    public void createBooking_WhenPlayerIdHaveNoMoreThan3Booking() throws Exception{
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createPlayer("B");

        SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2020-05-03"), Time.valueOf("10:00:00"), Time.valueOf("10:45:00"), "1", "2", "A", "B");
        SQLStatement.createBooking("booking2", Timestamp.valueOf("2020-04-08 09:27:18"), Date.valueOf("2020-05-04"), Time.valueOf("10:00:00"), Time.valueOf("10:45:00"), "1", "2", "A", "B");
        SQLStatement.createBooking("booking3", Timestamp.valueOf("2020-04-09 09:27:18"), Date.valueOf("2020-05-05"), Time.valueOf("10:00:00"), Time.valueOf("10:45:00"), "1", "2", "A", "B");

        //actual test
        String result_code = SQLStatement.createBooking("booking4", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-06"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");
        assertEquals("CB-012", result_code, "Pass.");
    }

    //test cancel booking
    @Test
    public void cancelBooking_WhenBookingIdIsInvalid() throws Exception{
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createPlayer("B");
        //actual test
        String result_code = SQLStatement.cancelBooking("#booking","B");
        assertEquals("CA-000", result_code, "Pass.");
    }

    @Test
    public void cancelBooking_WhenPlayerIdIsInvalid() throws Exception{
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createPlayer("B");
        //actual test
        String result_code = SQLStatement.cancelBooking("booking1","#B");
        assertEquals("CA-001", result_code, "Pass.");
    }

    @Test
    public void cancelBooking_WhenPlayerIdIsNotExited() throws Exception{
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createPlayer("B");

        SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");

        //actual test
        String result_code = SQLStatement.cancelBooking("booking1","C");
        assertEquals("CA-002", result_code, "Pass.");
    }

    @Test
    public void cancelBooking_WhenBookingIdIsNotExisted() throws Exception{
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createPlayer("B");

        SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");
        //actual test
        String result_code = SQLStatement.cancelBooking("booking2","B");
        assertEquals("CA-003", result_code, "Pass.");
    }

    @Test
    public void cancelBooking_WhenPlayerIdDoesNotOwnTheBooking() throws Exception{
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createPlayer("B");
        SQLStatement.createPlayer("C");

        SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 09:27:18"), Date.valueOf("2021-05-01"), Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), "1", "2", "A", "B");
        //actual test
        String result_code = SQLStatement.cancelBooking("booking1","C");
        assertEquals("CA-004", result_code, "Pass.");
    }

    @Test
    public void cancelBooking_WhenViolating24HoursBeforeStartTime() throws Exception{
        
        // clean up database
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createPlayer("B");

        SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-11 18:12:00"), Date.valueOf("2020-04-11"), Time.valueOf("20:00:00"), Time.valueOf("21:00:00"), "1", "2", "A", "B");
        //actual test
        String result_code = SQLStatement.cancelBooking("booking1","B");
        assertEquals("CA-005", result_code, "Pass.");
    }

    //test update booking
    @Test
    public void updateBookingStatus_success() throws  Exception{
        
        // clean up some data
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createStaff("S","1","2");
        SQLStatement.createPlayer("B");

        SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 18:04:00"), Date.valueOf("2020-04-15"), Time.valueOf("09:00:00"), Time.valueOf("10:00:00"), "1", "2", "A", "B");
        //actual test
        String result_code = SQLStatement.updateBookingStatus('1',"booking1","1","2","S");
        assertEquals("200", result_code, "Pass.");
    }

    @Test
    public void updateBooingStatus_WhenBookingIdIsInvalid() throws  Exception{
        
        // clean up some data
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createStaff("S","1","2");
        SQLStatement.createPlayer("B");

        SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 18:04:00"), Date.valueOf("2020-04-15"), Time.valueOf("09:00:00"), Time.valueOf("10:00:00"), "1", "2", "A", "B");
        //actual test
        String result_code = SQLStatement.updateBookingStatus('1',"#booking","1","2","S");
        assertEquals("UBS-000", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenCityIdIsInvalid() throws Exception{
        
        // clean up some data
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createStaff("S","1","2");
        SQLStatement.createPlayer("B");

        SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 18:04:00"), Date.valueOf("2020-04-15"), Time.valueOf("09:00:00"), Time.valueOf("10:00:00"), "1", "2", "A", "B");
        //actual test
        String result_code = SQLStatement.updateBookingStatus('1',"booking1","#","2","S");
        assertEquals("UBS-001", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenCenterIdIsInvalid() throws Exception{
        
        // clean up some data
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createStaff("S","1","2");
        SQLStatement.createPlayer("B");

        SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 18:04:00"), Date.valueOf("2020-04-15"), Time.valueOf("09:00:00"), Time.valueOf("10:00:00"), "1", "2", "A", "B");
        //actual test
        String result_code = SQLStatement.updateBookingStatus('1',"booking1","1","#","S");
        assertEquals("UBS-002", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenStaffIdIsInvalid() throws Exception{
        
        // clean up some data
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createStaff("S","1","2");
        SQLStatement.createPlayer("B");

        SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 18:04:00"), Date.valueOf("2020-04-15"), Time.valueOf("09:00:00"), Time.valueOf("10:00:00"), "1", "2", "A", "B");
        //actual test
        String result_code = SQLStatement.updateBookingStatus('1',"booking1","1","2","#");
        assertEquals("UBS-003", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenBookingIdIsNotExisted() throws Exception{
        
        // clean up some data
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createStaff("S","1","2");
        SQLStatement.createPlayer("B");

        SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 18:04:00"), Date.valueOf("2020-04-15"), Time.valueOf("09:00:00"), Time.valueOf("10:00:00"), "1", "2", "A", "B");
        //actual test
        String result_code = SQLStatement.updateBookingStatus('1',"booking2","1","2","S");
        assertEquals("UBS-004", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenCityIdIsNotExisted() throws Exception{
        
        // clean up some data
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createStaff("S","1","2");
        SQLStatement.createPlayer("B");

        SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 18:04:00"), Date.valueOf("2020-04-15"), Time.valueOf("09:00:00"), Time.valueOf("10:00:00"), "1", "2", "A", "B");
        //actual test
        String result_code = SQLStatement.updateBookingStatus('1',"booking1","3","2","S");
        assertEquals("UBS-005", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenCenterIdIsNotExisted() throws Exception{
        
        // clean up some data
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createStaff("S","1","2");
        SQLStatement.createPlayer("B");

        SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 18:04:00"), Date.valueOf("2020-04-15"), Time.valueOf("09:00:00"), Time.valueOf("10:00:00"), "1", "2", "A", "B");
        //actual test
        String result_code = SQLStatement.updateBookingStatus('1',"booking1","1","404","S");
        assertEquals("UBS-006", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_WhenstaffIdDoesNotManageIncityIdcourtId() throws Exception{
        
        // clean up some data
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("2", "1");
        SQLStatement.createCityCenter("3", "1");
        SQLStatement.createCityCenterCourt("A", "1", "2");
        SQLStatement.createStaff("S","1","2");
        SQLStatement.createCityCenterCourt("A", "1", "3");
        SQLStatement.createStaff("S2","1","3");
        SQLStatement.createPlayer("B");

        SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 18:04:00"), Date.valueOf("2020-04-15"), Time.valueOf("09:00:00"), Time.valueOf("10:00:00"), "1", "2", "A", "B");
        //actual test
        String result_code = SQLStatement.updateBookingStatus('1',"booking1","1","2","S2");
        assertEquals("UBS-007", result_code, "Pass.");
    }

    @Test
    public void updateBookingStatus_bookingIdDoesNotBelongTocityIdcenterId() throws Exception{
        
        // clean up some data
        SQLStatement.cleanTable("city");
        SQLStatement.cleanTable("center");
        SQLStatement.cleanTable("staff");
        SQLStatement.cleanTable("player");
        SQLStatement.cleanTable("booking");
        //scenario
        SQLStatement.createCity("1");
        SQLStatement.createCityCenter("1", "1");
        SQLStatement.createCityCenterCourt("A", "1", "1");
        SQLStatement.createStaff("S","1","1");

        SQLStatement.createCity("2");
        SQLStatement.createCityCenter("2", "2");
        SQLStatement.createCityCenterCourt("B", "2", "2");
        SQLStatement.createStaff("S2","2","2");

        SQLStatement.createPlayer("B");

        SQLStatement.createBooking("booking1", Timestamp.valueOf("2020-04-07 18:04:00"), Date.valueOf("2020-04-15"), Time.valueOf("09:00:00"), Time.valueOf("10:00:00"), "1", "1", "A", "B");
        //actual test
        String result_code = SQLStatement.updateBookingStatus('1',"booking1","2","2","S2");
        assertEquals("UBS-008", result_code, "Pass.");
    }
    */
}

