package app.booking.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//@AllArgsConstructor
//@Setter
//@Getter
public class CityCenter {
    String centerId;
    String cityId;

    public CityCenter(String centerId, String cityId) {
        this.centerId = centerId;
        this.cityId = cityId;
    }

    public String getCenterId() {
        return centerId;
    }

    public String getCityId() {
        return cityId;
    }
}
