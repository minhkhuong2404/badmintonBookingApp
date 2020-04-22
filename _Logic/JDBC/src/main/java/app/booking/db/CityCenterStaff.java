package app.booking.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//@Setter
//@Getter
//@AllArgsConstructor
public class CityCenterStaff {
    String courtId;
    String cityId;
    String staffId;

    public CityCenterStaff(String courtId, String cityId, String staffId) {
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
