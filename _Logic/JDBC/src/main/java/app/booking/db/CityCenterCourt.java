package app.booking.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//@AllArgsConstructor
//@Getter
//@Setter

public class CityCenterCourt {
    String courtId;
    String cityId;
    String centerId;

    public CityCenterCourt(String courtId, String cityId, String centerId) {
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
}