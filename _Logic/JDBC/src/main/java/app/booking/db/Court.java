package app.booking.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

//@AllArgsConstructor
//@Getter
//@Setter

public class Court {
    String courtId;
    String cityId;
    String centerId;

    public Court(String courtId, String cityId, String centerId) {
        this.courtId = courtId;
        this.cityId = cityId;
        this.centerId = centerId;
    }

    public String getCourtId() {
        return courtId;
    }

    public String getCityId() {
        return cityId;
    }

    public String getCenterId() {
        return centerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Court court = (Court) o;
        return courtId.equals(court.courtId) &&
                cityId.equals(court.cityId) &&
                centerId.equals(court.centerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courtId, cityId, centerId);
    }
}