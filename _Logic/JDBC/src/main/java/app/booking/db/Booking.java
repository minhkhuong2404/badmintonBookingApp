package app.booking.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
public class Booking {
    private Integer bookingId;
    private Timestamp timestamp;
    private Date date;
    private Time start;
    private Time end;
    private String cityId;
    private String centerId;
    private String courtId;
    private String playerId;
    private Integer status;
}
