
import java.util.ArrayList;

class Time {
    private int hour;
    private int minute;

    Time(int newH, int newM) {
        hour = newH;
        minute = newM;
    }

    public int compare(Time t) {
        // if t1 is after t2 then t1 is larger than t2
        if ( (this.hour * 60 + this.minute) < (t.getHour() * 60 + t.getMinute()) ) {
            return -1;
        } else if ( (this.hour * 60 + this.minute) == (t.getHour() * 60 + t.getMinute()) ) {
            return 0;
        } else {
            return 1;
        }
    }

    public int getHour() { return hour; }
    public int getMinute() { return minute; }

    // return time by minute
    public int toMinute() { return hour * 60 + minute; }
} 

// create a class for booking
class Booking {
    private Time start;
    private Time end;

    public Booking(Time newStart, Time newEnd) {
        start = newStart;
        end = newEnd;
    }

    public Time getStart() { return start; }
    public Time getEnd() { return end; }

    
}

// create a class for slot
class Slot {
    private Time start;
    private Time end;

    public Slot(Time newStart, Time newEnd) {
        start = newStart;
        end = newEnd;
    }

    public Time getStart() { return start; }
    public Time getEnd() { return end; }
}

public class Demo {

    public ArrayList<Slot> getCourtSlots(ArrayList<Booking> bookingList) {

        // create a initail slot
        ArrayList<Slot> slotList = new ArrayList<Slot>();   // store all slot
        
        // add an initial slot
        slotList.add(new Slot(new Time(7, 0), new Time(21,0)) );    // initial slot: [7:00 21:00]

        for(Booking booking: bookingList) {
            
            for (Slot slot : slotList) {
                // if booking. start >= slot.start and booking.end <= slot.end
                if ( (booking.getStart().compare(slot.getStart()) >= 0) && (booking.getEnd().compare(slot.getEnd())  <= 0) ) {
                    
                    // if 
                    if ( booking.getStart().toMinute() - slot.getStart().toMinute() >= 45 ) {
                        int index = slotList.indexOf(slot);
                        slotList.add(index, new Slot( slot.getStart(), booking.getStart() ) );
                    }
                    if (slot.getEnd().toMinute() - booking.getEnd().toMinute() >= 45) {
                        int index = slotList.indexOf(slot);
                        slotList.add(index + 1, new Slot( booking.getEnd(), slot.getEnd() ) );
                    }

                    slotList.remove(slot);

                    break;
                }
            }
            
        }

        return slotList;
    }

    public static void main(String[] args) {
        
        // create bookingLisst = [[8:00, 9:00], [9:45, 11:00], [11:15, 12:15], [19:45, 20:45]]
        ArrayList<Booking> bookingList = new ArrayList<Booking>();
        bookingList.add( new Booking(new Time(8, 0), new Time(9, 0)) );
        bookingList.add( new Booking(new Time(9, 45), new Time(11, 0)) );
        bookingList.add( new Booking(new Time(11, 15), new Time(12, 15)) );
        bookingList.add( new Booking(new Time(19, 45), new Time(20, 45)) );
        
        Demo a = new Demo();

        ArrayList<Slot> slotList = new ArrayList<Slot>(a.getCourtSlots(bookingList));  
        
        for (Slot slot : slotList) {
            System.out.print("[" + slot.getStart().getHour() + ":" + slot.getStart().getMinute() +
                             " " + slot.getEnd().getHour() + ":" + slot.getEnd().getMinute() + "] ");
        }
        
        

    }

}