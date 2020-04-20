package app.booking.database;
import java.sql.*;

public class MySQLAccess {
    private static PreparedStatement getPrepareStatement(String statement) throws SQLException{
        String url = "jdbc:mysql://localhost:3306/booking_app?noAccessToProcedureBodies=true&autoReconnect=true";
        String user = "root";
        String password = "rootroot";
        Connection myConn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Cannot find MysSQL driver class");
            e.printStackTrace();
            System.exit(1);
        }
        myConn = DriverManager.getConnection(url, user, password);
        return myConn.prepareStatement(statement);
    }
    // stored procedures below
    public static void create_booking(String bookingid, String timestamp, String date, String s_time, String e_time, String cityid
            , String centerid, String courtid, String playerid){
        String query = "{call createCity(?,?,?,?,?,?,?,?,?)";
        try(PreparedStatement statement = getPrepareStatement(query)){
            statement.setString(1, bookingid);
            statement.setString(2, timestamp);
            statement.setString(3, date);
            statement.setString(4, s_time);
            statement.setString(5, e_time);
            statement.setString(6, cityid);
            statement.setString(7, centerid);
            statement.setString(8, courtid);
            statement.setString(9, playerid);
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
