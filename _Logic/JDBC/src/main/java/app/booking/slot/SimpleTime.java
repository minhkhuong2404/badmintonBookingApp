package app.booking.slot;

import java.sql.Time;

public class SimpleTime {
    private int minute;

    SimpleTime(String newTime) {
        String[] timeSplit = newTime.split(":");
        minute = 60 * Integer.parseInt(timeSplit[0]);
        minute += Integer.parseInt(timeSplit[1]);
    }

    SimpleTime(Time newTime) {
        String[] timeSplit = newTime.toString().split(":");
        minute = 60 * Integer.parseInt(timeSplit[0]);
        minute += Integer.parseInt(timeSplit[1]);
    }

    public int toMinute() {
        return minute;
    }
}