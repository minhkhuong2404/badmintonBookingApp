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
}
