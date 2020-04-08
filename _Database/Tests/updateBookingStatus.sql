/* 
[English]
Test guide for: updateBookingStatus(status, bookingId, cityId, centerId, staffId)
- Ensure the database is empty when doing the tests (clean up city, player will let database empty)
- Every test has n SQL statements, the (n - 1) first SQL statements are the scenario for the test.
- The last SQL statements is the actual test.
[Tiếng Việt]
Hướng dẫn kiểm thử cho: updateBookingStatus(status, bookingId, cityId, centerId, staffId)
- Đảm bảo rằng database hoàn toàn trống (xoá dữ liệu trong city, player sẽ làm toàn bộ database trống)
- Mỗi test có n câu lệnh SQL, (n - 1) câu lệnh đầu tiên dùng để tạo dữ liệu mẫu.
- Câu lệnh SQL cuối cùng là câu lệnh dùng để test.
*/ 

/* Test if updateBookingStatus is accepted when all constraints are passed */
delete from city where city_id = "CityA";
delete from center where center_id = "CenterA" and city_id = "CityA";
delete from court where court_id = "Court1" and city_id = "CityA" and center_id = "CenterA";
delete from staff where staff_id = "Staff" and city_id = "CityA" and center_id = "CenterA";
delete from player where player_id ="Player";

CALL createCity("CityA");
CALL createCityCenter("CenterA", "CityA");
CALL createCityCenterCourt("Court1", "CityA", "CenterA");
CALL createStaff("Staff", "CityA", "CenterA");
CALL createPlayer("Player");
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) 
VALUES ('Booking1','2020-04-17 18:04:00','2020-04-15','09:00:00','10:00:00','CityA','CenterA','Court1','Player',0);
CALL updateBookingStatus(1, "Booking1", "CityA", "CenterA", "Staff");
/* expected no error code */  

/* Test if updateBookingStatus is rejected when bookingId is invalid */
CALL updateBookingStatus(1, "#BOOKING", "CITY", "CENTER", "STAFF");
/* expected error code UBS-000 */

/* Test if updateBookingStatus is rejected when cityId is invalid */
CALL updateBookingStatus(1, "BOOKING", "#CITY", "CENTER", "STAFF");
/* expected error code UBS-001 */

/* Test if updateBookingStatus is rejected when centerId is invalid */
CALL updateBookingStatus(1, "BOOKING", "CITY", "#CENTER", "STAFF");
/* expected error code UBS-002 */

/* Test if updateBookingStatus is rejected when staffId is invalid */
CALL updateBookingStatus(1, "BOOKING", "CITY", "CENTER", "#STAFF");
/* expected error code UBS-003 */

/* Test if updateBookingStatus is rejected when bookingId is not existed */
CALL updateBookingStatus(1, "BOOKING12", "CITY", "CENTER", "STAFF");
/* expected error code UBS-004 */

/* Test if updateBookingStatus is rejected when cityId is not existed */
delete from city where city_id = "CityA";
delete from center where center_id = "CenterA" and city_id = "CityA";
delete from court where court_id = "Court1" and city_id = "CityA" and center_id = "CenterA";
delete from staff where staff_id = "Staff" and city_id = "CityA" and center_id = "CenterA";
delete from player where player_id ="Player";

CALL createCity("CityA");
CALL createCityCenter("CenterA", "CityA");
CALL createCityCenterCourt("Court1", "CityA", "CenterA");
CALL createStaff("Staff", "CityA", "CenterA");
CALL createPlayer("Player");
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) 
VALUES ('Booking1','2020-04-17 18:04:00','2020-04-15','09:00:00','10:00:00','CityA','CenterA','Court1','Player',0);
CALL updateBookingStatus(1, "Booking1", "CITY3", "CenterA", "STAFF");
/* expected error code UBS-005 */

/* Test if updateBookingStatus is rejected when centerId is not existed */
delete from city where city_id = "CityA";
delete from center where center_id = "CenterA" and city_id = "CityA";
delete from court where court_id = "Court1" and city_id = "CityA" and center_id = "CenterA";
delete from staff where staff_id = "Staff" and city_id = "CityA" and center_id = "CenterA";
delete from player where player_id ="Player";

CALL createCity("CityA");
CALL createCityCenter("CenterA", "CityA");
CALL createCityCenterCourt("Court1", "CityA", "CenterA");
CALL createStaff("Staff", "CityA", "CenterA");
CALL createPlayer("Player");
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) 
VALUES ('Booking1','2020-04-17 18:04:00','2020-04-15','09:00:00','10:00:00','CityA','CenterA','Court1','Player',0);
CALL updateBookingStatus(1, "Booking1", "CityA", "CENTER3", "STAFF");
/* expected error code UBS-006 */

/* Test if updateBookingStatus is rejected when staffId does not manage in cityId, courtId*/
delete from city where city_id = "CityA";
delete from center where center_id = "CenterA" and city_id = "CityA";
delete from court where court_id = "Court1" and city_id = "CityA" and center_id = "CenterA";
delete from staff where staff_id = "Staff" and city_id = "CityA" and center_id = "CenterA";
delete from player where player_id ="Player";

CALL createCity("CityA");
CALL createCityCenter("CenterA", "CityA");
CALL createCityCenterCourt("Court1", "CityA", "CenterA");
CALL createStaff("Staff", "CityA", "CenterA");
CALL createPlayer("Player");
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) 
VALUES ('Booking1','2020-04-17 18:04:00','2020-04-15','09:00:00','10:00:00','CityA','CenterA','Court1','Player',0);
CALL updateBookingStatus(1, "Booking1", "CityA", "CenterA", "STAFF1");
/* expected error code UBS-007 */

/* Test if updateBookingStatus is rejected when bookingId does not belong to cityId, centerId */
delete from city where city_id = "CityA";
delete from city where city_id = "CityB";
delete from center where center_id = "CenterA" and city_id = "CityA";
delete from center where center_id = "CenterB" and city_id = "CityB";
delete from court where court_id = "Court1" and city_id = "CityA" and center_id = "CenterA";
delete from staff where staff_id = "Staff" and city_id = "CityA" and center_id = "CenterA";
delete from staff where staff_id = "Staff2" and city_id = "CityB" and center_id = "CenterB";
delete from player where player_id ="Player";

CALL createCity("CityA");
CALL createCity("CityB");
CALL createCityCenter("CenterA", "CityA");
CALL createCityCenter("CenterB", "CityB");
CALL createCityCenterCourt("Court1", "CityA", "CenterA");
CALL createStaff("Staff", "CityA", "CenterA");
CALL createStaff("Staff2", "CityB", "CenterB");
CALL createPlayer("Player");
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) 
VALUES ('Booking1','2020-04-17 18:04:00','2020-04-15','09:00:00','10:00:00','CityA','CenterA','Court1','Player',0);
CALL updateBookingStatus(1, "Booking1", "CityB", "CenterB", "Staff2");
/* expected error code UBS-008 */

/* Test if updateBookingStatus is accepted when all above constraints are passed */
delete from city where city_id = "CityA";
delete from center where center_id = "CenterA" and city_id = "CityA";
delete from court where court_id = "Court1" and city_id = "CityA" and center_id = "CenterA";
delete from staff where staff_id = "Staff" and city_id = "CityA" and center_id = "CenterA";
delete from player where player_id ="Player";

CALL createCity("CityA");
CALL createCityCenter("CenterA", "CityA");
CALL createCityCenterCourt("Court1", "CityA", "CenterA");
CALL createStaff("Staff", "CityA", "CenterA");
CALL createPlayer("Player");
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) 
VALUES ('Booking1','2020-04-17 18:04:00','2020-04-15','09:00:00','10:00:00','CityA','CenterA','Court1','Player',0);
CALL updateBookingStatus(1, "Booking1", "CityA", "CenterA", "Staff");
/* expected no error code */
