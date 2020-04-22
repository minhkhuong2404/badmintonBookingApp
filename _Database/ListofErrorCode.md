# createCity
Error code | Test Description |
------------ | ---------------- | 
CITY-000 | cityId is invalid |
CITY-001 | cityId is existed |
***
# createCityCenter
Error code | Test Description |
------------ | ---------------- | 
CEN-000 | cityId is invalid
CEN-001 | centerId is invalid
CEN-002 | cityId is not existed
CEN-003 | centerId is existed
***
# createCityCenterCourt
Error code | Test Description |
------------ | ---------------- | 
CRT-000 | cityId is invalid |
CRT-001 | centerId is invalid |
CRT-002 | courtId is invalid |
CRT-003 | centerId is existed but cityId is not existed **OR** both centerId and cityId are not existed|
CRT-004 | centerId is not existed but cityId is existed |
CRT-005 | courtId is existed |
***
# createStaff
Error code | Test Description |
------------ | ---------------- | 
CS-000 | cityId is invalid |
CS-001 | centerId is invalid |
CS-002 | staffId is invalid |
CS-003 | centerId is existed but cityId is not existed **OR** both centerId and cityId are not existed |
CS-004 | centerId is not existed but cityId is existed |
CS-005 | staffId is existed |

# ~~createPlayer
Error code | Test Description |
------------ | ---------------- | 
CPL-000 | playerId is invalid |
CPL-001 | playerId is existed~~
***
# createBooking
Error code | Test Description |
------------ | ---------------- |
~~CB-000~~ | ~~bookingId is not alphanumeric~~ |
~~CB-100~~ | ~~bookingId is existed~~ |
CB-001 | cityId is not existed | 
CB-002 | centerId is not existed | 
CB-003 | courtId is not existed |  
~~CB-004~~ | ~~playerId is not existed~~ |
CB-005 | startTime < DATE(NOW()) |  
CB-006 | startTime < openTime |
CB-007 | endTime > closeTime |
CB-008 | endTime < startTime |
CB-009 | playtime invalid (valid: 45m, 1h, 1h15m, 1h30m) |
CB-010 | create a booking that overlapped with other booking |
CB-011 | playerId have pending booking |
CB-012 | playerId no more than 3 bookings |
***
# cancelBooking
Error code | Test Description |
------------ | ---------------- |
CA-000 | bookingId is invalid |
CA-001 | playerId is invalid |
CA-002 | playerId is not existed |
CA-003 | bookingId is not existed |
CA-004 | playerId does not own the bookingId |
CA-005 | violating 24 hours before start time |
***
# updateBookingStatus
Error code | Test Description |
------------ | ---------------- |
UBS-000 | bookingId is invalid |
UBS-001 | cityId is invalid |
UBS-002 | centerId is invalid |
UBS-003 | staffId is invalid |
UBS-004 | bookingId is not existed |
UBS-005 | cityId is not existed |
UBS-006 | centerId is not existed |
UBS-007 | staffId does not manage in cityId, courtId |
UBS-008 | bookingId does not belong to cityId, centerId |
