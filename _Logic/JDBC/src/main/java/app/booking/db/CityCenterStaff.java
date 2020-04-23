package app.booking.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//@Setter
//@Getter
//@AllArgsConstructor
public class CityCenterStaff {
    String staffId;
    String cityId;
    String courtId;

    public CityCenterStaff(String staffId, String cityId, String courtId) {
        this.courtId = courtId;
        this.cityId = cityId;
        this.staffId = staffId;
    }

    public String getCourtId() {
        return courtId;
    }

    public String getCityId() {
        return cityId;
    }

    public String getStaffId() {
        return staffId;
    }
}
