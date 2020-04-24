package app.booking.db;

//@Setter
//@Getter
//@AllArgsConstructor
public class CityCenterStaff {
    String staffId;
    String cityId;
    String centerId;

    public CityCenterStaff(String staffId, String cityId, String centerId) {
        this.centerId = centerId;
        this.cityId = cityId;
        this.staffId = staffId;
    }

    public String getCenterId() {
        return centerId;
    }

    public String getCityId() {
        return cityId;
    }

    public String getStaffId() {
        return staffId;
    }
}
