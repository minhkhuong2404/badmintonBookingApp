package app.booking.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public class CenterOpenHour {
    private String center;
    private Map<String, String> activeHour = new HashMap<>(); // Monday : "open/close"
}