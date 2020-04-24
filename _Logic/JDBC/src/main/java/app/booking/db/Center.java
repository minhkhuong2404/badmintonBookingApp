package app.booking.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

//@AllArgsConstructor
//@Setter
//@Getter
public class Center {
    String centerId;
    String cityId;

    public Center(String centerId, String cityId) {
        this.centerId = centerId;
        this.cityId = cityId;
    }

    public String getCenterId() {
        return centerId;
    }

    public String getCityId() {
        return cityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Center center = (Center) o;
        return centerId.equals(center.centerId) &&
                cityId.equals(center.cityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerId, cityId);
    }
}
