package app.booking.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return cityId.equals(city.cityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityId);
    }
}
