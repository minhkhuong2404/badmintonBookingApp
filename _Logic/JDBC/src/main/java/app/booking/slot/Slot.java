package app.booking.slot;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
public class Slot {
    private Time start;
    private Time end;

    public Slot(Time start, Time end) {
        this.start = start;
        this.end = end;
    }
    public Slot(String start, String end) {
        this.start = Time.valueOf(start);
        this.end = Time.valueOf(end);
    }

    public Slot(Slot slot) {
        this.start = slot.start;
        this.end = slot.end;
    }

    public Time getStart() {
        return start;
    }

    public Time getEnd() {
        return end;
    }
}