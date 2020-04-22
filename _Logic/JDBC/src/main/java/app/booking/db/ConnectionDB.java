package app.booking.db;

import java.sql.*;
import java.util.Properties;
import java.util.TimeZone;

public class ConnectionDB {
    private static Connection conn = null;
    private static ConnectionDB singleInstance = null;

    private ConnectionDB() throws ClassNotFoundException {
        String CONNECTION = "jdbc:mysql://localhost:3306/booking_app?noAccessToProcedureBodies=true&autoReconnect=true&serverTimezone=" + TimeZone.getDefault().getID();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Properties p = new Properties();
        p.put("user", "root");
        p.put("password", "rootroot");
        try {
            conn = DriverManager.getConnection(CONNECTION, p);
        } catch (SQLException e) {
            throw new IllegalStateException("DB Errors: ", e);
        }
    }

    public static ConnectionDB getInstance() throws ClassNotFoundException {
        if (singleInstance == null) {
            synchronized (ConnectionDB.class) {
                if (singleInstance == null) {
                    singleInstance = new ConnectionDB();
                }
            }
        }

        return singleInstance;
    }

    public Connection getConnection() {
        return conn;
    }
}
