/* Test if cancelBooking is rejected when bookingId is invalid */
CALL cancelBooking("#", "A");
/* expected error code CA-000 */

/* Test if cancelBooking is rejected when playerId is invalid */
CALL cancelBooking("A", "#");
/* expected error code CA-001 */

/* Test if cancelBooking is rejected when playerId is not existed */
CALL createCity("CityA");
CALL createCityCenter("CenterA", "CityA");
CALL createCityCenterCourt("Court1", "CityA", "CenterA");
CALL createPlayer("Player");
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) 
VALUES ('Booking1','2020-04-17 18:04:00','2020-04-15','09:00:00','10:00:00','CityA','CenterA','Court1','Player',0);
CALL cancelBooking("Booking1", "Player2");
/* expected error code CA-002 */

/* Test if cancelBooking is rejected when bookingId is not existed */
CALL createCity("CityA");
CALL createCityCenter("CenterA", "CityA");
CALL createCityCenterCourt("Court1", "CityA", "CenterA");
CALL createPlayer("Player");
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) 
VALUES ('Booking1','2020-04-17 18:04:00','2020-04-15','09:00:00','10:00:00','CityA','CenterA','Court1','Player',0);
CALL cancelBooking("Booking2", "Player");
/* expected error code CA-003 */

/* Test if cancelBooking is rejected when bookingId is not existed */
CALL createCity("CityA");
CALL createCityCenter("CenterA", "CityA");
CALL createCityCenterCourt("Court1", "CityA", "CenterA");
CALL createPlayer("Player");
CALL createPlayer("Player2");
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) 
VALUES ('Booking1','2020-04-17 18:04:00','2020-04-15','09:00:00','10:00:00','CityA','CenterA','Court1','Player',0);
CALL cancelBooking("Booking1", "Player2");
/* expected error code CA-004 */

/* Test if cancelBooking is rejected when violating 24 hours before start time */
CALL createCity("CityA");
CALL createCityCenter("CenterA", "CityA");
CALL createCityCenterCourt("Court1", "CityA", "CenterA");
CALL createPlayer("Player");
CALL createPlayer("Player2");
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) 
VALUES ('Booking2','2020-04-17 18:04:00',DATE(NOW()),'07:00:00','8:00:00','CityA','CenterA','Court1','Player',0);
CALL cancelBooking("Booking2", "Player");
/* expected error code CA-005 */