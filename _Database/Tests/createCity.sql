/* 
Test suites for:
createBooking(booking_id, timestamp, date, startTime, endTime, cityId, centerId, courtId, playerId)
Ensure the database is empty when doing the tests (clean up city, player will let database empty)
*/ 

/* Test if createCity is accepted when cityId is valid */
delete from city where city_id = "1";
CALL createCity("1");
/* expected no error code */
 
/* Test if createCity is rejected when cityId is invalid */
CALL createCity("$A");
/* expected error CITY-000 */

/* Test if createCity is rejected when cityId is existed */
delete from city where city_id = "1";
CALL createCity("1");
CALL createCity("1");
/* expected error CITY-001 */

