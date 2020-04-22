package app.booking.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

//@AllArgsConstructor
//@Getter
//@Setter
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

    public Booking(Integer bookingId, Timestamp timestamp, Date date, Time start, Time end, String cityId, String centerId, String courtId, String playerId, Integer status) {
        this.bookingId = bookingId;
        this.timestamp = timestamp;
        this.date = date;
        this.start = start;
        this.end = end;
        this.cityId = cityId;
        this.centerId = centerId;
        this.courtId = courtId;
        this.playerId = playerId;
        this.status = status;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Date getDate() {
        return date;
    }

    public Time getStart() {
        return start;
    }

    public Time getEnd() {
        return end;
    }

    public String getCityId() {
        return cityId;
    }

    public String getCenterId() {
        return centerId;
    }

    public String getCourtId() {
        return courtId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Integer getStatus() {
        return status;
    }
}
