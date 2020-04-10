package app.booking.slot;

public class Time {
    private int minute;

    // construct time as specified time
    Time(Time newTime) {
        this.minute = newTime.minute;
    }

    // construction time from int
    Time(int newH, int newM) {
        minute = newH * 60 + newM;
    }

    // construct time from string
    Time(String newTime) {
        String[] timeSplit = newTime.split(":");
        minute = 60 * Integer.parseInt(timeSplit[0]);
        minute += Integer.parseInt(timeSplit[1]);
    }

    // set hour for time
    public void setHour(int newH) {
        minute = newH;
    }

    // set minute for time
    public void setMinute(int newM) {
        minute = newM;
    }

    // get hour of the time
    public int getHour() {
        return minute / 60;
    }

    // get minute of the time
    public int getMinute() {
        return minute % 60;
    }

    // return amount of time in minute
    public int toMinute() {
        return minute;
    }

    // return time as string
    public String toString() {
        String hour = String.valueOf(this.getHour());
        hour = hour.length() == 1 ? " " + hour : hour;
        String minute = String.valueOf(this.getMinute());
        minute = hour.length() == 1 ? " " + minute : minute;
        return hour + ":" + minute;
    }

    // compare this with specified time t
    public int compare(Time t) {
        // if t1 is after t2 then t1 is larger than t2
        if (this.minute < t.minute) {
            return -1;
        } else if (this.minute == (t.minute)) {
            return 0;
        } else {
            return 1;
        }
    }
}