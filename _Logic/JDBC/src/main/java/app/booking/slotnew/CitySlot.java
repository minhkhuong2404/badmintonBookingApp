package app.booking.slotnew;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@Setter
@Getter
public class CitySlot {
    private String city_id;
    private ArrayList<CenterSlot> centerSlots;
}
