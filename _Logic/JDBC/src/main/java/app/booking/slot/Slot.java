package app.booking.slot;

public class Slot {
    private Time start;
    private Time end;

    // construct from string
    public Slot(String newStart, String newEnd) {
        start = new Time(newStart);
        end = new Time(newEnd);
    }

    // construct from time type
    public Slot(Time newStart, Time newEnd) {
        start = newStart;
        end = newEnd;
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
