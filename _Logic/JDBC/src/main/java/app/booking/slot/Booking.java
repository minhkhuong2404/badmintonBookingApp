package app.booking.slot;

public class Booking {
    private Time start;
    private Time end;

    // construct from time type
    public Booking(Time newStart, Time newEnd) {
        start = newStart;
        end = newEnd;
    }

    // construct from string
    public Booking(String newStart, String newEnd) {
        start = new Time(newStart);
        end = new Time(newEnd);
    }

    // set, get methods
    public void setStart(Time newStart) {
        start = newStart;
    }

    public void setEnd(Time newEnd) {
        end = newEnd;
    }

    public Time getStart() {
        return start;
    }

    public Time getEnd() {
        return end;
    }
}
