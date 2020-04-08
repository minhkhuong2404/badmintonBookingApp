/* 
[English]
Test guide for: cancelBooking(bookingId, playerId)
- Ensure the database is empty when doing the tests (clean up city, player will let database empty)
- Every test has n SQL statements, the (n - 1) first SQL statements are the scenario for the test.
- The last SQL statements is the actual test.
[Tiếng Việt]
Hướng dẫn kiểm thử cho: cancelBooking(bookingId, playerId)
- Đảm bảo rằng database hoàn toàn trống (xoá dữ liệu trong city, player sẽ làm toàn bộ database trống)
- Mỗi test có n câu lệnh SQL, (n - 1) câu lệnh đầu tiên dùng để tạo dữ liệu mẫu.
- Câu lệnh SQL cuối cùng là câu lệnh dùng để test.
*/ 

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

/* Test if cancelBooking is rejected when playerId does not own the bookingId */
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
