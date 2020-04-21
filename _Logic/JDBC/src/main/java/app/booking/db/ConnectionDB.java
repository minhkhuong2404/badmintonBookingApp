package app.booking.db;

import java.sql.*;
import java.util.Properties;
import java.util.TimeZone;

public class ConnectionDB {
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/booking_app?noAccessToProcedureBodies=true&autoReconnect=true&serverTimezone=" + TimeZone.getDefault().getID();

    public Connection getConnect() throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties p = new Properties();
            p.put("user", "root");
            p.put("password", "rootroot");
            conn = DriverManager.getConnection(CONNECTION, p);
            return conn;
        } catch (Exception e) {
            throw e;
        }
    }

    // clean up the table
    public void cleanTable(String table) throws Exception {
        ConnectionDB db = new ConnectionDB();
        Connection conn = db.getConnect();
        PreparedStatement stm = null;

        try {
            stm = conn.prepareStatement("DELETE from " + table);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {}
        }
    }

    // createCity
    public String createCity(String cityId) throws Exception {
        String code;
        ConnectionDB db = new ConnectionDB();
        Connection conn = db.getConnect();
        CallableStatement stm = null;
        try {
            stm = conn.prepareCall("{ CALL createCity(? , ?) }");
            stm.setString(1, cityId);
            stm.registerOutParameter(2, Types.VARCHAR);
            stm.executeUpdate();
            code = stm.getString(2);
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {}
        }
        return code;
    }

    // createCityCenter
    public String createCityCenter(String centerId, String cityId) throws Exception {
        String code;
        ConnectionDB db = new ConnectionDB();
        Connection conn = db.getConnect();
        CallableStatement stm = null;
        try {
            stm = conn.prepareCall("{ CALL createCityCenter(?, ?, ?) }");
            stm.setString(1, centerId);
            stm.setString(2, cityId);
            stm.registerOutParameter(3, Types.VARCHAR);
            stm.executeUpdate();
            code = stm.getString(3);
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {}
        }
        return code;
    }

    // createCityCenterCourt
    public String createCityCenterCourt(String courtId, String cityId, String centerId) throws Exception {
        String code;
        ConnectionDB db = new ConnectionDB();
        Connection conn = db.getConnect();
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
            throw e;
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {}
        }
        return code;
    }

    // createStaff
    public String createStaff(String staffId, String cityId, String centerId) throws Exception {
        String code;
        ConnectionDB db = new ConnectionDB();
        Connection conn = db.getConnect();
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
            throw e;
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {}
        }
        return code;
    }

    // createPlayer
    public String createPlayer(String playerId) throws Exception {
        String code;
        ConnectionDB db = new ConnectionDB();
        Connection conn = db.getConnect();
        CallableStatement stm = null;
        try {
            stm = conn.prepareCall("{ CALL createPlayer(? , ?) }");
            stm.setString(1, playerId);
            stm.registerOutParameter(2, Types.VARCHAR);
            stm.executeUpdate();
            code = stm.getString(2);
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {}
        }
        return code;
    }

    // updateBookingStatus
    public String updateBookingStatus(int status, String bookingId, String cityId, String centerId, String staffId)
            throws Exception {
        String code;
        ConnectionDB db = new ConnectionDB();
        Connection conn = db.getConnect();
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
            throw e;
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {}
        }
        return code;
    }
    // cancelBooking
    public String cancelBooking(String bookingId, String playerId)
            throws Exception {
        String code;
        ConnectionDB db = new ConnectionDB();
        Connection conn = db.getConnect();
        CallableStatement stm = null;
        try {
            stm = conn.prepareCall("{ CALL cancelBooking(?, ?, ?) }");
            stm.setString(1, bookingId);
            stm.setString(2, playerId);
            stm.registerOutParameter(3, Types.VARCHAR);
            stm.executeUpdate();
            code = stm.getString(3);
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {}
        }
        return code;
    }
    // createBooking
    public String createBooking(String bookingId, Timestamp timestamp, Date date, Time start, Time end,
                                String cityId, String centerId, String courtId, String playerId) throws Exception {
        String code;
        ConnectionDB db = new ConnectionDB();
        Connection conn = db.getConnect();
        CallableStatement stm = null;
        try {
            stm = conn.prepareCall("{ CALL createBooking(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            stm.setString(1, bookingId);
            stm.setTimestamp(2, timestamp);
            stm.setDate(3, date);
            stm.setTime(4, start);
            stm.setTime(5, end);
            stm.setString(6, cityId);
            stm.setString(7, centerId);
            stm.setString(8, courtId);
            stm.setString(9, playerId);
            stm.registerOutParameter(10, Types.VARCHAR);
            stm.executeUpdate();
            code = stm.getString(10);
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {}
        }
        return code;
    }

}
