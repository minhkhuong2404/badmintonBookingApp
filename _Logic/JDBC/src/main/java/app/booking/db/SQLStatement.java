package app.booking.db;

import lombok.Data;

import java.sql.*;
import java.util.ArrayList;

@Data
public class SQLStatement {
    private static Connection conn;

    static {
        try {
            conn = ConnectionDB.getInstance().getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // clean up the table
    public static void cleanTable(String table) throws Exception {
        PreparedStatement stm = null;

        try {
            stm = conn.prepareStatement("DELETE from " + table);
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
            }
        }
    }

    // createCity
    public static String createCity(String cityId) throws Exception {
        String code;
        CallableStatement stm = null;
        try {
            stm = conn.prepareCall("{ CALL createCity(? , ?) }");
            stm.setString(1, cityId);
            stm.registerOutParameter(2, Types.VARCHAR);
            stm.executeUpdate();
            code = stm.getString(2);
        } catch (SQLException e) {
            code = e.getMessage();
            System.out.println(e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
                code = e.getMessage();
                System.out.println(e);
            }
        }
        return code;
    }

    // createCityCenter
    public static String createCityCenter(String centerId, String cityId) throws Exception {
        String code;

        CallableStatement stm = null;
        try {
            stm = conn.prepareCall("{ CALL createCityCenter(?, ?, ?) }");
            stm.setString(1, centerId);
            stm.setString(2, cityId);
            stm.registerOutParameter(3, Types.VARCHAR);
            stm.executeUpdate();
            code = stm.getString(3);
        } catch (SQLException e) {
            code = e.getMessage();
            System.out.println(e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
                code = e.getMessage();
                System.out.println(e);
            }
        }
        return code;
    }

    // createCityCenterCourt
    public static String createCityCenterCourt(String courtId, String cityId, String centerId) throws Exception {
        String code;
        CallableStatement stm = null;
        try {
            stm = conn.prepareCall("{ CALL createCityCenterCourt(?, ?, ?, ?) }");
            stm.setString(1, courtId);
            stm.setString(2, cityId);
            stm.setString(3, centerId);
            stm.registerOutParameter(4, Types.VARCHAR);
            stm.executeUpdate();
            code = stm.getString(4);
        } catch (SQLException e) {
            code = e.getMessage();
            System.out.println(e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
                code = e.getMessage();
                System.out.println(e);
            }
        }
        return code;
    }

    // createStaff
    public static String createStaff(String staffId, String cityId, String centerId) throws Exception {
        String code;
        CallableStatement stm = null;
        try {
            stm = conn.prepareCall("{ CALL createStaff(?, ?, ?, ?) }");
            stm.setString(1, staffId);
            stm.setString(2, cityId);
            stm.setString(3, centerId);
            stm.registerOutParameter(4, Types.VARCHAR);
            stm.executeUpdate();
            code = stm.getString(4);
        } catch (SQLException e) {
            code = e.getMessage();
            System.out.println(e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
                code = e.getMessage();
                System.out.println(e);
            }
        }
        return code;
    }

    // updateBookingStatus
    public static String updateBookingStatus(int status, String bookingId, String cityId, String centerId, String staffId) throws Exception {
        String code;
        CallableStatement stm = null;
        try {
            stm = conn.prepareCall("{ CALL updateBookingStatus(?, ?, ?, ?, ?, ?) }");
            stm.setInt(1, status);
            stm.setString(2, bookingId);
            stm.setString(3, cityId);
            stm.setString(4, centerId);
            stm.setString(5, staffId);
            stm.registerOutParameter(6, Types.VARCHAR);
            stm.executeUpdate();
            code = stm.getString(6);
        } catch (SQLException e) {
            code = e.getMessage();
            System.out.println(e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
                code = e.getMessage();
                System.out.println(e);
            }
        }
        return code;
    }

    // cancelBooking
    public static String cancelBooking(int bookingId, String playerId) throws Exception {
        String code;
        CallableStatement stm = null;
        try {
            stm = conn.prepareCall("{ CALL cancelBooking(?, ?, ?) }");
            stm.setInt(1, bookingId);
            stm.setString(2, playerId);
            stm.registerOutParameter(3, Types.VARCHAR);
            stm.executeUpdate();
            code = stm.getString(3);
        } catch (SQLException e) {
            code = e.getMessage();
            System.out.println(e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
                code = e.getMessage();
                System.out.println(e);
            }
        }
        return code;
    }

    // createBooking
    public static String createBooking(Date date, Time start, Time end,
                                       String cityId, String centerId, String courtId, String playerId) throws Exception {
        String code;
        CallableStatement stm = null;
        try {
            stm = conn.prepareCall("{ CALL createBooking(?, ?, ?, ?, ?, ?, ?, ?) }");
            stm.setDate(1, date);
            stm.setTime(2, start);
            stm.setTime(3, end);
            stm.setString(4, cityId);
            stm.setString(5, centerId);
            stm.setString(6, courtId);
            stm.setString(7, playerId);
            stm.registerOutParameter(8, Types.VARCHAR);
            stm.executeUpdate();
            code = stm.getString(8);
        } catch (SQLException e) {
            code = e.getMessage();
            System.out.println(e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
                code = e.getMessage();
                System.out.println(e);
            }
        }
        return code;
    }
    // getCities
    public static ArrayList<String> getCities() throws NullPointerException, SQLException {
        CallableStatement stm = null;
        ArrayList<String> cityList = new ArrayList<>();

        try {
            stm = conn.prepareCall("{ CALL getCities(?) }");
            stm.registerOutParameter(1, Types.VARCHAR);
            stm.executeUpdate();
            String resultCode = stm.getString(1);
            ResultSet resultSet = stm.getResultSet();
            System.out.println("getCities() executed with result code: " + resultCode);
            while (resultSet.next()) {
                cityList.add(resultSet.getString("city_id"));
            }
        } catch (NullPointerException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
            }
        }
        if (cityList.isEmpty())
            System.out.println("getCities returns empty list");

        return cityList;
    }

    public static ArrayList<Center> getCityCenters(String cityId) throws NullPointerException, SQLException {
        CallableStatement stm = null;
        ArrayList<Center> centerList = new ArrayList<>();
        try {
            stm = conn.prepareCall("{ CALL getCityCenters(?, ?) }");
            stm.setString(1, cityId);
            stm.registerOutParameter(2, Types.VARCHAR);
            stm.executeUpdate();
            String resultCode = stm.getString(2);
            ResultSet resultSet = stm.getResultSet();
            System.out.println("getCityCenters() executed with result code: " + resultCode);
            while (resultSet.next()) {
                centerList.add(new Center(
                        resultSet.getString("center_id"),
                        resultSet.getString("city_id"))
                );
            }
        } catch (NullPointerException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
            }
        }
        if (centerList.isEmpty())
            System.out.println("getCityCenters returns empty list");

        return centerList;
    }

    // getCenterCourts
    public static ArrayList<Court> getCenterCourts(String centerId) throws NullPointerException, SQLException {
        CallableStatement stm = null;
        ArrayList<Court> courtList = new ArrayList<>();
        try {
            stm = conn.prepareCall("{ CALL getCenterCourts(?, ?) }");
            stm.setString(1, centerId);
            stm.registerOutParameter(2, Types.VARCHAR);
            stm.executeUpdate();
            String resultCode = stm.getString(2);
            ResultSet resultSet = stm.getResultSet();
            System.out.println("getCenterCourts() executed with result code: " + resultCode);
            while (resultSet.next()) {
                courtList.add(new Court(
                        resultSet.getString("court_id"),
                        resultSet.getString("city_id"),
                        resultSet.getString("center_id")
                ));
            }
        } catch (NullPointerException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
            }
        }
        if (courtList.isEmpty())
            System.out.println("getCenterCourts returns empty list");

        return courtList;
    }

    // getCityCenterCourts
    public static ArrayList<Court> getCityCenterCourts(String cityId, String centerId) throws NullPointerException, SQLException {
        CallableStatement stm = null;
        ArrayList<Court> courtList = new ArrayList<>();

        try {
            stm = conn.prepareCall("{ CALL getCityCenterCourts(?, ?, ?) }");
            stm.setString(1, cityId);
            stm.setString(2, centerId);
            stm.registerOutParameter(3, Types.VARCHAR);
            stm.executeUpdate();
            String resultCode = stm.getString(3);
            ResultSet resultSet = stm.getResultSet();
            System.out.println("getCityCenterCourts() executed with result code: " + resultCode);
            while (resultSet.next()) {
                courtList.add(new Court(
                        resultSet.getString("court_id"),
                        resultSet.getString("city_id"),
                        resultSet.getString("center_id")
                ));
            }
        } catch (NullPointerException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
            }
        }
        if (courtList.isEmpty())
            System.out.println("getCityCenterCourts returns empty list");

        return courtList;
    }
    // getCityCenterStaffs
    public static ArrayList<Staff> getCityCenterStaffs(String cityId, String centerId) throws NullPointerException, SQLException {
        CallableStatement stm = null;
        ArrayList<Staff> cityCenterCourtList = new ArrayList<>();

        try {
            stm = conn.prepareCall("{ CALL getCityCenterStaffs(?, ?, ?) }");
            stm.setString(1, cityId);
            stm.setString(2, centerId);
            stm.registerOutParameter(3, Types.VARCHAR);
            stm.executeUpdate();
            String resultCode = stm.getString(3);
            ResultSet resultSet = stm.getResultSet();
            System.out.println("getCityCenterStaffs() executed with result code: " + resultCode);
            while (resultSet.next()) {
                cityCenterCourtList.add(new Staff(
                        resultSet.getString("staff_id"),
                        resultSet.getString("city_id"),
                        resultSet.getString("center_id")
                ));
            }
        } catch (NullPointerException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
            }
        }
        if (cityCenterCourtList.isEmpty())
            System.out.println("getCityCenterStaffs returns empty list");

        return cityCenterCourtList;
    }

    // getStaffs
    public static ArrayList<Staff> getStaffs() throws NullPointerException, SQLException {
        CallableStatement stm = null;
        ArrayList<Staff> cityCenterCourtList = new ArrayList<>();

        try {
            stm = conn.prepareCall("{ CALL getStaffs(?) }");
            stm.registerOutParameter(1, Types.VARCHAR);
            stm.executeUpdate();
            String resultCode = stm.getString(1);
            ResultSet resultSet = stm.getResultSet();
            System.out.println("getStaffs() executed with result code: " + resultCode);
            while (resultSet.next()) {
                cityCenterCourtList.add(new Staff(
                        resultSet.getString("staff_id"),
                        resultSet.getString("city_id"),
                        resultSet.getString("center_id")
                ));
            }
        } catch (NullPointerException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
            }
        }
        if (cityCenterCourtList.isEmpty())
            System.out.println("getStaffs returns empty list");

        return cityCenterCourtList;
    }
    // getCityBookings
    public static ArrayList<Booking> getCityBookings(String cityId, Date pdate) throws NullPointerException, SQLException {
        CallableStatement stm = null;
        ArrayList<Booking> bookingList = new ArrayList<Booking>();

        try {
            stm = conn.prepareCall("{ CALL getCityBookings(?, ?, ?) }");
            stm.setString(1, cityId);
            stm.setDate(2, pdate);
            stm.registerOutParameter(3, Types.VARCHAR);
            stm.executeUpdate();
            String resultCode = stm.getString(3);
            ResultSet resultSet = stm.getResultSet();
            System.out.println("getCityBookings() executed with result code: " + resultCode);

            while (resultSet.next()) {
                bookingList.add(new Booking(
                        resultSet.getInt("booking_id"),
                        resultSet.getTimestamp("timestamp"),
                        resultSet.getDate("date"),
                        resultSet.getTime("startTime"),
                        resultSet.getTime("endTime"),
                        resultSet.getString("city_id"),
                        resultSet.getString("center_id"),
                        resultSet.getString("court_id"),
                        resultSet.getString("player_id"),
                        resultSet.getInt("status")
                ));
            }
        } catch (NullPointerException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
            }
        }
        if (bookingList.isEmpty())
            System.out.println("getCourtBookings returns empty list");

        return bookingList;
    }

    // getCenterBookings
    public static ArrayList<Booking> getCenterBookings(String centerId, Date pdate) throws NullPointerException, SQLException {
        CallableStatement stm = null;
        ArrayList<Booking> bookingList = new ArrayList<>();

        try {
            stm = conn.prepareCall("{ CALL getCenterBookings(?, ?, ?) }");
            stm.setString(1, centerId);
            stm.setDate(2, pdate);
            stm.registerOutParameter(3, Types.VARCHAR);
            stm.executeUpdate();
            String resultCode = stm.getString(3);
            ResultSet resultSet = stm.getResultSet();
            System.out.println("getCenterBookings() executed with result code: " + resultCode);

            while (resultSet.next()) {
                bookingList.add(new Booking(
                        resultSet.getInt("booking_id"),
                        resultSet.getTimestamp("timestamp"),
                        resultSet.getDate("date"),
                        resultSet.getTime("startTime"),
                        resultSet.getTime("endTime"),
                        resultSet.getString("city_id"),
                        resultSet.getString("center_id"),
                        resultSet.getString("court_id"),
                        resultSet.getString("player_id"),
                        resultSet.getInt("status")
                ));
            }
        } catch (NullPointerException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
            }
        }
        if (bookingList.isEmpty())
            System.out.println("getCenterBookings returns empty list");

        return bookingList;
    }

    // getCourtBookings
    public static ArrayList<Booking> getCourtBookings(String courtId, Date pdate) throws NullPointerException, SQLException {
        CallableStatement stm = null;
        ArrayList<Booking> bookingList = new ArrayList<>();

        try {
            stm = conn.prepareCall("{ CALL getCourtBookings(?, ?, ?) }");
            stm.setString(1, courtId);
            stm.setDate(2, pdate);
            stm.registerOutParameter(3, Types.VARCHAR);
            stm.executeUpdate();
            String resultCode = stm.getString(3);
            ResultSet resultSet = stm.getResultSet();
            System.out.println("getCourtBookings() executed with result code: " + resultCode);

            while (resultSet.next()) {
                bookingList.add(new Booking(
                        resultSet.getInt("booking_id"),
                        resultSet.getTimestamp("timestamp"),
                        resultSet.getDate("date"),
                        resultSet.getTime("startTime"),
                        resultSet.getTime("endTime"),
                        resultSet.getString("city_id"),
                        resultSet.getString("center_id"),
                        resultSet.getString("court_id"),
                        resultSet.getString("player_id"),
                        resultSet.getInt("status")
                ));
            }
        } catch (NullPointerException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
            }
        }
        if (bookingList.isEmpty())
            System.out.println("getCenterBookings returns empty list");

        return bookingList;
    }

    public static ArrayList<Booking> getPlayerBookings(String playerId, Date date) throws NullPointerException, SQLException {
        CallableStatement stm = null;
        ArrayList<Booking> bookingList = new ArrayList<>();

        try {
            stm = conn.prepareCall("{ CALL getPlayerBookings(?, ?, ?) }");
            stm.setString(1, playerId);
            stm.setDate(2, date);
            stm.registerOutParameter(3, Types.VARCHAR);
            stm.executeUpdate();
            String resultCode = stm.getString(3);
            ResultSet resultSet = stm.getResultSet();
            System.out.println("getPlayerBookings() executed with result code: " + resultCode);
            while (resultSet.next()) {
                bookingList.add(new Booking(
                        resultSet.getInt("booking_id"),
                        resultSet.getTimestamp("timestamp"),
                        resultSet.getDate("date"),
                        resultSet.getTime("startTime"),
                        resultSet.getTime("endTime"),
                        resultSet.getString("city_id"),
                        resultSet.getString("center_id"),
                        resultSet.getString("court_id"),
                        resultSet.getString("player_id"),
                        resultSet.getInt("status")
                ));
            }
        } catch (NullPointerException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
            }
        }
        if (bookingList.isEmpty())
            System.out.println("getPlayerBookings returns empty list");

        return bookingList;
    }
}

