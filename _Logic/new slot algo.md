
open : open time
close : close time
min : min length of slot
bookings : ascending order by start time, they dont overlap each other
list: list of slots

new empty list
new slot
slot.start = open

for each booking in bookings:
    if (booking.start - slot.start >= min):
        slot.end = booking.start
        list.add(slot)
    slot.start = booking.end

if (close - slot.start >= min):
    slot.end = close

