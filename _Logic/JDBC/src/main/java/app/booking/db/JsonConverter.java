package app.booking.db;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import java.io.File;

import java.sql.*;
import java.util.ArrayList;

public class   JsonConverter {
    public static String convert(ArrayList ls) throws SQLException, JSONException {
        ObjectMapper mapper = new ObjectMapper();
        try{
            mapper.writeValue(new File("op.json"), ls);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ls);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
