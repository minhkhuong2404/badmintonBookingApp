
import app.booking.db.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {


        ArrayList<City> cityList = SQLStatement.getCities();
        System.out.println(cityList);

        ArrayList<CityCenter> cityCenterList = SQLStatement.getCityCenters("A");
        System.out.println(cityCenterList);
        
        ArrayList<CityCenterCourt> cityCenterCourtList = SQLStatement.getCityCenterCourts("A", "A1");
        System.out.println(cityCenterCourtList);

        ObjectMapper mapper = new ObjectMapper();

        ArrayList<CityCenterStaff> cityCenterStaffList = SQLStatement.getCityCenterStaffs("A", "A1");
        System.out.println(cityCenterStaffList);

        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cityCenterStaffList);
            System.out.println(json);
        } catch (IOException e){
            e.printStackTrace();
        }


        ArrayList<CityCenterStaff> cityStaffList = SQLStatement.getStaffs();
        System.out.println(cityStaffList);

        ArrayList<Booking> bookingList = SQLStatement.getCenterBookings("A1");
        System.out.println(bookingList);

        ArrayList<Booking> player_bookingList = SQLStatement.getPlayerBookings("player1");
        System.out.println(player_bookingList);

        //JSONArray json = ResultSetConverter.convert(crs.getResultSet());
        //System.out.println(json);
    }

}
