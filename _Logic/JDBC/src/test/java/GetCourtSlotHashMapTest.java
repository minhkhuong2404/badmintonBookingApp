import app.booking.slotnew.Slot;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GetCourtSlotHashMapTest {
    private ArrayList<ArrayList<String>> getCourtHashMap(ArrayList<String> courts, ArrayList<String> bookings){

        ArrayList<ArrayList<String>> courtArrayList = new ArrayList<>();

        Integer j = 0;
        for (int i = 0; i < courts.size(); i++){
            courtArrayList.add(new ArrayList<>());
            while (j < bookings.size()) {
                if (bookings.get(j) == courts.get(i)) {
                    courtArrayList.get(i).add(bookings.get(j));
                }
                else break;
                j++;
            }
        }
        return courtArrayList;
    }
    @Test
    void getCourtHashMap() {
        ArrayList<String> courts = new ArrayList<>();
        ArrayList<String> bookings = new ArrayList<>();
        courts.add("A");
        courts.add("B");
        courts.add("C");
        courts.add("D");
        courts.add("E");
        courts.add("F");

        bookings.add("A");
//        bookings.add("A");
//        bookings.add("B");
//        bookings.add("C");
        bookings.add("C");
//        bookings.add("E");
//        bookings.add("E");

        ArrayList<ArrayList<String>> courtHashMap = getCourtHashMap(courts, bookings);

        System.out.println("A");
    }
}