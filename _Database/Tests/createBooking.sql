/* 
Test suites for:
createBooking(booking_id, timestamp, date, startTime, endTime, cityId, centerId, courtId, playerId)
Ensure the database is empty when doing the tests (clean up city, player will let database empty)
*/ 
/* Scenario for this tests: */
CALL createCity("city1");
CALL createCityCenter("center1", "city1");
CALL createCityCenterCourt("court1","city1","center1");
CALL createPlayer("player1");

/* Test if createBooking is accepted when all constraints are valid */
CALL createBooking("booking1", "2020-04-07 09:27:18", "2021-05-01", "10:00:00" , "10:45:00" , 'city1', 'center1', 'court1', 'player1');
/* expected no error code */

/* Test if createBooking is rejected when bookingId is not alphanumeric */
CALL createBooking("#booking", "2020-04-07 09:27:18", "2021-05-01", "10:30:00" , "11:30:00" , 'city1', 'center1', 'court1', 'player1');
/* expected error code CB-000 */

/* Test if createBooking is rejected when bookingId is existed */
CALL createBooking("booking1", "2020-04-07 09:27:18", "2021-05-01", "10:30:00" , "11:30:00" , 'city1', 'center1', 'court1', 'player1');
/* expected error code CB-100 */

/* Test if createBooking is rejected when cityId is not existed */
CALL createBooking("booking12", "2020-04-07 09:27:18", "2021-05-01", "10:30:00" , "11:30:00" , 'city2', 'center1', 'court1', 'player1');
/* expected error code CB-001 */

/* Test if createBooking is rejected when centerId is not existed */
CALL createBooking("booking12", "2020-04-07 09:27:18", "2021-05-01", "10:30:00" , "11:30:00" , 'city1', 'center2', 'court1', 'player1');
/* expected error code CB-002 */

/* Test if createBooking is rejected when courtId is not existed */
CALL createBooking("booking12", "2020-04-07 09:27:18", "2021-05-01", "10:30:00" , "11:30:00" , 'city1', 'center1', 'court2', 'player1');
/* expected error code CB-003 */

/* Test if createBooking is rejected when playerId is not existed */
CALL createBooking("booking12", "2020-04-07 09:27:18", "2021-05-01", "10:30:00" , "11:30:00" , 'city1', 'center1', 'court1', 'player2');
/* expected error code CB-004 */

/* Test if createBooking is rejected when startTime < DATE(NOW()) */
CALL createBooking("booking2", "2020-04-07 09:27:18", "2020-03-01", "10:10:00" , "18:35:00" , 'city1', 'center1', 'court1', 'player1');
/* expected error code CB-005 */

/* Test if createBooking is rejected when startTime < openTime */
CALL createBooking("booking2", "2020-04-07 09:27:18", "2020-05-01", "06:00:00" , "07:00:00" , 'city1', 'center1', 'court1', 'player1');
/* expected error code CB-006 */

/* Test if createBooking is rejected when endTime > closeTime */
CALL createBooking("booking2", "2020-04-07 09:27:18", "2020-05-01", "07:00:00" , "22:00:00" , 'city1', 'center1', 'court1', 'player1');
/* expected error code CB-007 */

/* Test if createBooking is rejected when endTime < startTime */
CALL createBooking("booking2", "2020-04-07 09:27:18", "2020-05-01", "09:00:00" , "07:00:00" , 'city1', 'center1', 'court1', 'player1');
/* expected error code CB-008 */

/* Test if createBooking is rejected when playtime invalid (valid: 45m, 1h, 1h15m, 1h30m) */
CALL CreateBooking("booking2", "2020-04-07 09:27:18", "2021-05-01", "10:00:00" , "10:30:00" , 'city1', 'center1', 'court1', 'player1');
/* expected error code CB-009 */

/* Test if createBooking is rejected when create a booking that overlapped with other booking */
CALL createBooking("booking2", "2020-04-07 09:27:18", "2021-05-01", "10:30:00" , "11:30:00" , 'city1', 'center1', 'court1', 'player1');
/* expected error code CB-010 */

/* Test if createBooking is rejected if playerId have pending booking */
INSERT INTO `booking_app`.`booking` (`booking_id`, `timestamp`, `date`, `startTime`, `endTime`, `city_id`, `center_id`, `court_id`, `player_id`, `status`) 
VALUES ('pending1', '2020-04-07 09:27:18', '2020-04-01', '10:00:00', '10:45:00', 'city1', 'center1', 'court1', 'player1', '0');
CALL createBooking("booking2", "2020-04-07 09:27:18", "2021-05-02", "10:30:00" , "11:30:00" , 'city1', 'center1', 'court1', 'player1');
/* expected error code CB-011 */

/* Test if createBooking is rejected if playerId no more than 3 bookings */
INSERT INTO player VALUES ("player2");
INSERT INTO `booking_app`.`booking` (`booking_id`, `timestamp`, `date`, `startTime`, `endTime`, `city_id`, `center_id`, `court_id`, `player_id`, `status`) VALUES ('bookingA', '2020-04-07 09:27:18', '2021-05-03', '07:00:00', '08:00:00', 'city1', 'center1', 'court1', 'player2', '0');
INSERT INTO `booking_app`.`booking` (`booking_id`, `timestamp`, `date`, `startTime`, `endTime`, `city_id`, `center_id`, `court_id`, `player_id`, `status`) VALUES ('bookingB', '2020-04-07 09:27:18', '2021-05-05', '07:00:00', '08:00:00', 'city1', 'center1', 'court1', 'player2', '0');
INSERT INTO `booking_app`.`booking` (`booking_id`, `timestamp`, `date`, `startTime`, `endTime`, `city_id`, `center_id`, `court_id`, `player_id`, `status`) VALUES ('bookingC', '2020-04-07 09:27:18', '2021-05-04', '07:00:00', '08:00:00', 'city1', 'center1', 'court1', 'player2', '0');
CALL createBooking("bookingD", "2020-04-07 09:27:18", "2021-05-06", "10:30:00" , "11:30:00" , 'city1', 'center1', 'court1', 'player2');
/* expected error code CB-012 */