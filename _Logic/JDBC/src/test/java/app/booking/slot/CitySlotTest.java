package app.booking.slot;

import app.booking.db.Booking;
import app.booking.db.ConnectionDB;
import app.booking.db.SQLStatement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CitySlotTest {
        @Test
        void Construct_GivenDate() throws Exception, SQLException, JsonProcessingException {
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

            String sql7 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement smt7 = ConnectionDB.getInstance().getConnection().prepareStatement(sql7);

            smt7.setString(1, "9");
            smt7.setString(2, "2020-04-22 19:08:48");
            smt7.setString(3, "2020-05-10");
            smt7.setString(4, "13:31:00");
            smt7.setString(5, "14:31:00");
            smt7.setString(6, "A");
            smt7.setString(7, "A1");
            smt7.setString(8, "A1C1");
            smt7.setString(9, "player1");
            smt7.setString(10, "0");

            smt7.execute();


            String sql8 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement smt8 = ConnectionDB.getInstance().getConnection().prepareStatement(sql8);

            smt8.setString(1, "10");
            smt8.setString(2, "2020-04-22 19:08:48");
            smt8.setString(3, "2020-05-10");
            smt8.setString(4, "08:31:00");
            smt8.setString(5, "09:31:00");
            smt8.setString(6, "A");
            smt8.setString(7, "A1");
            smt8.setString(8, "A1C2");
            smt8.setString(9, "player1");
            smt8.setString(10, "0");


            smt8.execute();

            // Test object
            String city = "A";
            Date date = Date.valueOf("2020-05-10");

            // generate actual result
            ObjectMapper mapper = new ObjectMapper();
            CitySlot citySlot = new CitySlot(city, date);
            String actual = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(citySlot);

            String expected = "{\r\n" +
                    "  \"cityId\" : \"A\",\r\n" +
                    "  \"citySlots\" : [ {\r\n" +
                    "    \"cityId\" : \"A\",\r\n" +
                    "    \"centerId\" : \"A1\",\r\n" +
                    "    \"centerSlots\" : [ {\r\n" +
                    "      \"centerId\" : \"A1\",\r\n" +
                    "      \"courtId\" : \"A1C\",\r\n" +
                    "      \"courtSlots\" : [ {\r\n" +
                    "        \"start\" : \"07:00:00\",\r\n" +
                    "        \"end\" : \"10:30:00\"\r\n" +
                    "      }, {\r\n" +
                    "        \"start\" : \"11:30:00\",\r\n" +
                    "        \"end\" : \"12:31:00\"\r\n" +
                    "      }, {\r\n" +
                    "        \"start\" : \"13:31:00\",\r\n" +
                    "        \"end\" : \"21:00:00\"\r\n" +
                    "      } ]\r\n" +
                    "    }, {\r\n" +
                    "      \"centerId\" : \"A1\",\r\n" +
                    "      \"courtId\" : \"A1C1\",\r\n" +
                    "      \"courtSlots\" : [ {\r\n" +
                    "        \"start\" : \"07:00:00\",\r\n" +
                    "        \"end\" : \"13:31:00\"\r\n" +
                    "      }, {\r\n" +
                    "        \"start\" : \"14:31:00\",\r\n" +
                    "        \"end\" : \"21:00:00\"\r\n" +
                    "      } ]\r\n" +
                    "    }, {\r\n" +
                    "      \"centerId\" : \"A1\",\r\n" +
                    "      \"courtId\" : \"A1C2\",\r\n" +
                    "      \"courtSlots\" : [ {\r\n" +
                    "        \"start\" : \"07:00:00\",\r\n" +
                    "        \"end\" : \"08:31:00\"\r\n" +
                    "      }, {\r\n" +
                    "        \"start\" : \"09:31:00\",\r\n" +
                    "        \"end\" : \"21:00:00\"\r\n"+
                    "      } ]\r\n" +
                    "    } ]\r\n" +
                    "  } ]\r\n" +
                    "}";
            assertEquals(true, expected.equals(actual));
        }


        @Test
    void Construct_Given_Date_Constraint() throws Exception {
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

            String sql7 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement smt7 = ConnectionDB.getInstance().getConnection().prepareStatement(sql7);

            smt7.setString(1, "9");
            smt7.setString(2, "2020-04-22 19:08:48");
            smt7.setString(3, "2020-05-10");
            smt7.setString(4, "13:31:00");
            smt7.setString(5, "14:31:00");
            smt7.setString(6, "A");
            smt7.setString(7, "A1");
            smt7.setString(8, "A1C1");
            smt7.setString(9, "player1");
            smt7.setString(10, "0");

            smt7.execute();


            String sql8 = "INSERT INTO booking VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement smt8 = ConnectionDB.getInstance().getConnection().prepareStatement(sql8);

            smt8.setString(1, "10");
            smt8.setString(2, "2020-04-22 19:08:48");
            smt8.setString(3, "2020-05-10");
            smt8.setString(4, "08:31:00");
            smt8.setString(5, "09:31:00");
            smt8.setString(6, "A");
            smt8.setString(7, "A1");
            smt8.setString(8, "A1C2");
            smt8.setString(9, "player1");
            smt8.setString(10, "0");


            smt8.execute();

            // Test object
            String city = "A";
            Date date = Date.valueOf("2020-05-10");

            // generate actual result
            Time open = Time.valueOf("06:00:00");
            Time close = Time.valueOf("20:00:00");
            Time min = Time.valueOf("00:45:00");
            ObjectMapper mapper = new ObjectMapper();
            CitySlot citySlot = new CitySlot(city, date, open, close, min);
            String actual = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(citySlot);

            String expected = "{\r\n" +
                    "  \"cityId\" : \"A\",\r\n" +
                    "  \"citySlots\" : [ {\r\n" +
                    "    \"cityId\" : \"A\",\r\n" +
                    "    \"centerId\" : \"A1\",\r\n" +
                    "    \"centerSlots\" : [ {\r\n" +
                    "      \"centerId\" : \"A1\",\r\n" +
                    "      \"courtId\" : \"A1C\",\r\n" +
                    "      \"courtSlots\" : [ {\r\n" +
                    "        \"start\" : \"06:00:00\",\r\n" +
                    "        \"end\" : \"10:30:00\"\r\n" +
                    "      }, {\r\n" +
                    "        \"start\" : \"11:30:00\",\r\n" +
                    "        \"end\" : \"12:31:00\"\r\n" +
                    "      }, {\r\n" +
                    "        \"start\" : \"13:31:00\",\r\n" +
                    "        \"end\" : \"20:00:00\"\r\n" +
                    "      } ]\r\n" +
                    "    }, {\r\n" +
                    "      \"centerId\" : \"A1\",\r\n" +
                    "      \"courtId\" : \"A1C1\",\r\n" +
                    "      \"courtSlots\" : [ {\r\n" +
                    "        \"start\" : \"06:00:00\",\r\n" +
                    "        \"end\" : \"13:31:00\"\r\n" +
                    "      }, {\r\n" +
                    "        \"start\" : \"14:31:00\",\r\n" +
                    "        \"end\" : \"20:00:00\"\r\n" +
                    "      } ]\r\n" +
                    "    }, {\r\n" +
                    "      \"centerId\" : \"A1\",\r\n" +
                    "      \"courtId\" : \"A1C2\",\r\n" +
                    "      \"courtSlots\" : [ {\r\n" +
                    "        \"start\" : \"06:00:00\",\r\n" +
                    "        \"end\" : \"08:31:00\"\r\n" +
                    "      }, {\r\n" +
                    "        \"start\" : \"09:31:00\",\r\n" +
                    "        \"end\" : \"20:00:00\"\r\n"+
                    "      } ]\r\n" +
                    "    } ]\r\n" +
                    "  } ]\r\n" +
                    "}";
            assertTrue(expected.equals(actual));
        }

}
