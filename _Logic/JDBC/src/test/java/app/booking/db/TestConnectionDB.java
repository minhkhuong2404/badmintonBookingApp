package app.booking.db;

import org.junit.jupiter.api.Test;

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
        String result_code = db.createBooking("booking1", "2020-04-07 09:27:18", "2021-05-01", "10:00:00", "10:45:00", '1', '2', 'A', 'B');
        assertEquals("200", result_code, "Pass.");
    }

}
