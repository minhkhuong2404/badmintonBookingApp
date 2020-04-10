# Pseudocode for getCourtSlots
## Input: 
`bookings` an array of `[b_start, b_end]` which `b_start` / `b_end` is **start / end** time of booking respectively.  
`bookings` is sorted in ascending order to time.
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
        `s` is the last item in `slots`
        If `(b_start - s_start) ≥ 45 minutes`:
            s_end = b_start
        If `s_end - b_end ≥ 45 minutes`:
            Insert [b_end, s_end] into `slots`
    Return slots
```

## Note:
If `bookings` is sorted then after running the algorithm `slots` is automatically sorted too.

# Pseudocode for getCenterSlots
## Input: 
`centerBookings` is an array of `[court, bookings]` in which `court` is a court and `bookings` is all bookings of that court.  
## Output: 
`centerSlots` is an array of `[court, slots]` in which `slots` is all available slots of `court`.
## Pseudocode
```
getCenterSlots(array centerBookings)

Initialize centerSlots = []

For each [court, bookings] in centerBookings:
    slots = getCourtSlots(bookings)
    Insert [court, slots] into centerSlots
```

# Pseudocode for getCitySlots
## Input: 
`cityBookings` is an array of `[center, centerBookings]`.  
## Output: 
`citySlots` is an array of `[center, centerSlots]`.  
## Pseudocode
```
getCitySlots(array cityBookings)

Initialize citySlots = []

For each [center, centerBookings] in cityBookings:
    centerSlots = getCenterSlots(centerBookings)
    Insert [center, centerSlots] into citySlots
```
