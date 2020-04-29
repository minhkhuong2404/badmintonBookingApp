package app.booking.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import java.io.File;

import java.sql.*;
import java.util.ArrayList;

public class JsonConverter {
    public static String toJson(Object o) throws JSONException {
        ObjectMapper mapper = new ObjectMapper();
        try{
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
