package app.booking.database;
import java.sql.*;

public class MySQLAccess {
    public Connection getConnect(){
        String url = "jdbc:mysql://localhost:3306/booking_app?noAccessToProcedureBodies=true&autoReconnect=true";
        String user = "root";
        String password = "rootroot";
        Connection myConn = null;
        try {
            myConn = DriverManager.getConnection(url, user, password);
            Statement myStmt = myConn.createStatement();
            String sql = "SELECT * FROM booking";
            ResultSet rs = myStmt.executeQuery(sql);

            while (rs.next())
                System.out.println(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myConn;
    }
    // stored procedures below

}
