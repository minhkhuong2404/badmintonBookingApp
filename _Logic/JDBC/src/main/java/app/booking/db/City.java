package app.booking.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//@AllArgsConstructor
//@Getter
//@Setter
public class City {
    String cityId;

    public City(String cityId) {
        this.cityId = cityId;
    }

    public String getCityId() {
        return cityId;
    }
}
