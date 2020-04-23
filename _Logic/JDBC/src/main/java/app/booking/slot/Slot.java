package app.booking.slot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
public class Slot {
    private Time start;
    private Time end;

    public Slot(String newStart, String newEnd) {
        start = Time.valueOf(newStart);
        end = Time.valueOf(newEnd);
    }
}
