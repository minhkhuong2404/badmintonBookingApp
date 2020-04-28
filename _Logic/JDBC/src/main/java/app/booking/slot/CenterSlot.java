package app.booking.slot;

import app.booking.db.Booking;
import app.booking.db.Center;
import app.booking.db.Court;
import app.booking.db.SQLStatement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CenterSlot extends CourtSlot {
    private String centerId;
    public CenterSlot(String centerId, ArrayList<Booking> bookingArrayList) throws SQLException {
        // TODO: check center existence
        super(centerId, bookingArrayList);
        this.centerId = centerId;
    }

    public HashMap<String, ArrayList<Slot>> getCenterSlot() throws SQLException {
        HashMap<String, ArrayList<Slot>> centerSlot = new HashMap<>();

        initializeCenterSlot(centerSlot);

        for (Booking booking : this.getBookingArrayList()) {
            String courtId = booking.getCourtId();
            Integer minLength = SQLStatement.getCenterMinLength(centerId);
            splitSlot(minLength, centerSlot.get(courtId), booking);
        }

        return centerSlot;
    }

    private void initializeCenterSlot(HashMap<String, ArrayList<Slot>> centerSlot) throws SQLException {
        // Get center's courts
        String centerId = this.getId();
        ArrayList<Court> courtArrayList = SQLStatement.getCenterCourts(centerId);

        for (Court court : courtArrayList) {
            // Add court's empty slot
            String courtId = court.getCourtId();
            centerSlot.put(courtId, new ArrayList<>());

            // Add slot [7AM - 21PM] to court slot list
            centerSlot.get(courtId).add(new Slot("07:00:00", "21:00:00"));
        }
    }
}
