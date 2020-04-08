/* Test cases for createBooking 
CB-000 | booking_id not alphanumeric
CB-001 | startTime < DATE(NOW())
CB-002 | startTime < openTime 
CB-003 | endTime > closeTime 
CB-004 | endTime < startTime   
CB-005 | playtime invalid (valid: 45m, 1h, 1h15m, 1h30m)  
CB-006 | overlapping booking  - 4 smaller cases
CB-007 | Player has pending booking in past 
CB-008 | Player can book no more than 3 bookings 
CB-009 | Customer does not existed 
CB-010 | Court does not existed
CB-011 | City does not existed
CB-012 | Court & Customer do not existed
*/

delete from city where city_id="HCM";
delete from center where center_id = "HCM_quan1" and city_id = "HCM";
delete from court where court_id = "Court#1" and city_id = "HCM" and center_id = "HCM_quan1";
delete from player where player_id = "Customer#A";
CALL createCity("HCM");
CALL createCityCenter("HCM_quan1", "HCM");
CALL createCityCenterCourt("Court#1","HCM","HCM_quan1");
CALL createPlayer("Customer#A");

/* CreateBooking(booking_id : alphanumeric, 
				timestamp : DATETIME, date, startTime, endTime, cityId, centerId, courtId, playerId
/* test 13 */
CALL CreateBooking(13, "2020-03-29 09:27:18", "2020-02-01", "10:10:00" , "18:35:00" , 'HCM', 'HCM_quan1', 'Court#1', 'Customer#A');
/* error: CB-001 */

/* test 14 */	
CALL CreateBooking(14, "2020-03-29 09:27:18", "2020-05-01", "6:10:00" , "18:35:00" , 'HCM', 'HCM_quan1', 'Court#1', 'Customer#A');
/* error: CB-002 */

/* test 15 */
CALL CreateBooking(15, "2020-03-29 09:27:18", "2020-04-15", "9:00:00" , "23:00:00" , 'HCM', 'HCM_quan1', 'Court#1', 'Customer#A');
/* error: CB-003 */

/* test 16 */
CALL CreateBooking(16, "2020-03-29 09:27:18", "2020-04-16", "9:00:00" , "8:00:00" , 'HCM', 'HCM_quan1', 'Court#1', 'Customer#A');
/* error: CB-004 */

/* test 17 */
CALL CreateBooking(17, "2020-03-29 09:27:18", "2020-04-17", "9:00:00" , "9:35:00" , 'HCM', 'HCM_quan1', 'Court#1', 'Customer#A');
/* error: CB-005 */

/* test 18 */
--       9:00===========10:00  existed booking
--    8:00========9:30
--           9:30==============10:30
--         9:15=========10:00
--     8:30=============10:00

delete from city where city_id="HCM";
delete from center where center_id = "HCM_quan2" and city_id = "HCM";
delete from court where court_id = "Court#2" and city_id = "HCM" and center_id = "HCM_quan2";
delete from player where player_id = "Customer#B";
CALL createCity("HCM");
CALL createCityCenter("HCM_quan2", "HCM");
CALL createCityCenterCourt("Court#2","HCM","HCM_quan2");
CALL createPlayer("Customer#B");

-- existed booking 
CALL CreateBooking(18, "2020-03-29 09:27:18", "2020-04-11", "9:00:00" , "10:00:00" , 'HCM', 'HCM_quan2', 'Court#2', 'Customer#B');
-- overlapping booking
CALL CreateBooking(18, "2020-03-29 09:27:18", "2020-04-11", "8:00:00" , "9:30:00" , 'HCM', 'HCM_quan2', 'Court#2', 'Customer#B');
CALL CreateBooking(18, "2020-03-29 09:27:18", "2020-04-11", "9:30:00" , "10:30:00" , 'HCM', 'HCM_quan2', 'Court#2', 'Customer#B');
CALL CreateBooking(18, "2020-03-29 09:27:18", "2020-04-11", "9:15:00" , "10:00:00", 'HCM', 'HCM_quan2', 'Court#2', 'Customer#B');
CALL CreateBooking(18, "2020-03-29 09:27:18", "2020-04-11", "8:30:00" , "10:00:00" , 'HCM', 'HCM_quan2', 'Court#2', 'Customer#B');
/* error: CB-006 */

/* test 19 */
CALL CreateBooking(19,"2020-03-29 09:27:18",'2020-04-18', "9:00:00" , "10:00:00" ,'HCM','HCM_quan1','Court#2','Customer#B');
/* error: CB-007 */
  
 /* test 20 */   
CALL CreateBooking(20,"2020-03-29 09:27:18",'2020-04-19', "9:00:00" , "10:00:00" ,'HCM','HCM_quan5','Court#5','Customer#E');
/* error: CB-008 */

/* test 21 */
CALL CreateBooking(21,"2020-03-29 09:27:18",'2020-04-20', "9:00:00" , "10:00:00" ,'HCM','HCM_quan5','Court#5','Customer#X');
/* error: CB-009 */

/* test 22 */
CALL CreateBooking(22,"2020-03-29 09:27:18",'2020-04-19', "9:00:00" , "10:00:00" ,'HCM','HCM_quan5','Court#X','Customer#A');
/* error: CB-010 */

/* test 23 */
CALL CreateBooking(23,"2020-03-29 09:27:18",'2020-04-19', "9:00:00" , "10:00:00" ,'HCM1','HCM_quan5','Court#5','Customer#X');
/* error: CB-011 */

/* test 24 */
CALL CreateBooking(23,"2020-03-29 09:27:18",'2020-04-19', "9:00:00" , "10:00:00" ,'HCM','HCM_quan5','Court#X','Customer#X');
/* error: CB-012 */