# Pseudocode for getCourtSlots
## Input: 
`bookings` an array of `[b_start, b_end]` which `b_start` / `b_end` is **start / end** time of booking respectively.  

## Output: 
`slots` an array of `[s_start, s_end]` which `s_start` / `s_end` is **start / end** time of slot respectively.  

### _Example_ 
`bookings = [[8:00, 9:00], [9:45, 11:00], [11:15, 12:15], [19:45, 20:45]]`  
`slots = [[7:00, 8:00], [9:00, 9:45], [12:15, 19:45]]`  

## Pseudocode:
```pseudocode
getCourtSlots(array `bookings`)
    Initialize `slots` = [[7:00, 21:00]]

    For each `b` in `bookings`:
        For each `s` in `slots`:
            If `s_start ≤ b_start` and `b_end ≤ s_end`
            Then:  
                If `(b_start - s_start) ≥ 45 minutes`:
                    Insert [s_start, b_start] into `slots`
                If `s_end - b_end ≥ 45 minutes`
                    Insert [b_end, s_end] into `slots`
                Delete `s` from `slots`
            Break this loop

    Return slots
```

## Note:
If `bookings` is sorted then after running the algorithm `slots` is automatically sorted too.

# Pseudocode for getCitySlots
## Input: 
`cityBookings` is an array of `[court, bookings]` in which `court` is a court and `bookings` is all bookings of that court in the city.
## Output: 
`citySlots` is an array of `[court, slots]` in which `slots` is all available slots of `court`.
## Pseudocode
```
getCitySlots(array cityBookings)

Initialize citySlots = []

For each [court, bookings] in cityBookings:
    slots = getCourtSlots(bookings)
    Insert [court, slots] into citySlots
```
# Pseudocode for getCourtBookings(court, date)