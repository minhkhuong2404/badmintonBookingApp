package app.booking.slot;

import app.booking.db.Booking;
import app.booking.db.Center;
import app.booking.db.Court;
import app.booking.db.SQLStatement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CenterSlot {
    private Center center;
    private HashMap<Court, ArrayList<Booking>> courtBookingMap = new HashMap<>();
    private HashMap<Court, ArrayList<Slot>> centerSlot = new HashMap<>();

    public CenterSlot(Center center, HashMap<Court, ArrayList<Booking>> courtBookingMapIn) throws SQLException {
        // TODO: check center existence
        this.center = center;
        this.courtBookingMap = courtBookingMapIn;

        ArrayList<Court> courtArrayList = SQLStatement.getCityCenterCourts(center.getCityId(), center.getCenterId());

        for (Court court : courtArrayList){
            if (courtBookingMap.get(court) == null){
                // initialize new slot arraylist
                ArrayList<Slot> slot = new ArrayList<>();
                slot.add(new Slot("07:00:00", "21:00:00"));
                centerSlot.put(court, slot);
            } else {
                ArrayList<Booking> courtBookingList = courtBookingMap.get(court);
                CourtSlot courtSlotMap = new CourtSlot(court, courtBookingList);
                centerSlot.put(court, courtSlotMap.getCourtSlot());
            }
        }

        System.out.println("End");
    }

    public Center getCenter() {
        return center;
    }

    public HashMap<Court, ArrayList<Slot>> getCenterSlot() {
        return centerSlot;
    }
}
