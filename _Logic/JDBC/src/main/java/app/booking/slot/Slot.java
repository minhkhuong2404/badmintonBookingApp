package app.booking.slot;

public class Slot {
    private SimpleTime start;
    private SimpleTime end;

    // construct from string
    public Slot(String newStart, String newEnd) {
        start = new SimpleTime(newStart);
        end = new SimpleTime(newEnd);
    }

    // construct from time type
    public Slot(SimpleTime newStart, SimpleTime newEnd) {
        start = newStart;
        end = newEnd;
    }

    // set, get methods
    public void setStart(SimpleTime newStart) {
        start = newStart;
    }

    public void setEnd(SimpleTime newEnd) {
        end = newEnd;
    }

    public SimpleTime getStart() {
        return start;
    }

    public SimpleTime getEnd() {
        return end;
    }
}
