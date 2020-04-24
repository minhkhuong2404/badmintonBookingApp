package app.booking.slot;

import app.booking.db.Booking;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetSlots {
    private Map<String, Map<String, Map<String, ArrayList<Slot>>>> slotMap = new HashMap<>();
    private Map<String, Map<String, Map<String, ArrayList<Booking>>>> bookingMap = new HashMap<>();

    public GetSlots(ArrayList<Booking> bookingArrayList) {
        // booking arraylist to map
        toMap(bookingArrayList);

        for (String cityId : bookingMap.keySet()) {
            // get city bookings
            Map<String, Map<String, ArrayList<Booking>>> cityBookings = bookingMap.get(cityId);
            // put {city, city slots} to slotMap
            Map<String, Map<String, ArrayList<Slot>>> citySlots = new HashMap<>();
            slotMap.put(cityId, citySlots);
            // for each center
            for (String centerId : cityBookings.keySet()) {
                // get center bookings
                Map<String, ArrayList<Booking>> centerBookings = cityBookings.get(centerId);
                // put {center, center slots} to city slots
                Map<String, ArrayList<Slot>> centerSlots = new HashMap<>();
                citySlots.put(centerId, centerSlots);
                // for each court
                for (String courtId : centerBookings.keySet()) {
                    // get court bookings
                    ArrayList<Booking> courtBookings = centerBookings.get(courtId);
                    // initialize court slot [7AM, 21PM]
                    ArrayList<Slot> courtSlots = new ArrayList<>();
                    courtSlots.add(new Slot("07:00:00", "21:00:00"));
                    // put {court, court slots} to center slots
                    centerSlots.put(courtId, courtSlots);
                    // for each booking
                    for (Booking booking : courtBookings) {
                        makeSlot(courtSlots, booking);
                    }
                }
            }
        }
    }

    private void toMap(ArrayList<Booking> bookingArrayList) {
        for (Booking booking : bookingArrayList) {
            String cityId = booking.getCityId();
            String centerId = booking.getCenterId();
            String courtId = booking.getCourtId();
            if (!bookingMap.containsKey(cityId)) {
                Map<String, Map<String, ArrayList<Booking>>> cityBookingList = new HashMap<>();
                bookingMap.put(cityId, cityBookingList);
            }
            if (!bookingMap.get(cityId).containsKey(centerId)) {
                Map<String, ArrayList<Booking>> centerBookingLists = new HashMap<>();
                bookingMap.get(cityId).put(centerId, centerBookingLists);
            }
            if (!bookingMap.get(cityId).get(centerId).containsKey(courtId)) {
                ArrayList<Booking> courtBookingLists = new ArrayList<>();
                bookingMap.get(cityId).get(centerId).put(courtId, courtBookingLists);
            }
            bookingMap.get(cityId).get(centerId).get(courtId).add(booking);
        }
    }

    private void makeSlot(ArrayList<Slot> slotArrayList, Booking booking) {
        Slot slot = slotArrayList.get(slotArrayList.size() - 1);

        if (leftMinusRight(slot.getEnd(), booking.getEnd()) >= 45) {
            slotArrayList.add(new Slot(booking.getEnd(), slot.getEnd()));
        }

        if (leftMinusRight(booking.getStart(), slot.getStart()) >= 45) {
            slot.setEnd(booking.getStart());
        } else {
            slotArrayList.remove(slot);
        }
    }

    private int leftMinusRight(Time t1, Time t2) {
        SimpleTime st1 = new SimpleTime(t1.toString());
        SimpleTime st2 = new SimpleTime(t2.toString());

        return (st1.toMinute() - st2.toMinute());
    }

    public Map<String, Map<String, Map<String, ArrayList<Booking>>>> getBookingMap() {
        return bookingMap;
    }

    public Map<String, Map<String, Map<String, ArrayList<Slot>>>> getSlotMap() {
        return slotMap;
    }
}