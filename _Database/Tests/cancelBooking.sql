/* Test cases for cancelBooking 
CA-001 | customer does not exist 
CA-002 | booking does not exist 
CA-003 | this customer does not own the booking 
CA-004 | violates 24 hours before start time
*/

delete from city where city_id="HCM";
delete from center where center_id = "HCM_quan2" and city_id = "HCM";
delete from court where court_id = "Court#2" and city_id = "HCM" and center_id = "HCM_quan2";
delete from player where player_id = "Customer#B";
delete from booking where booking_id = "18" and timestamp = "2020-04-10 09:27:18" and date =  "2020-04-11" and startTime = "9:00:00"
		and endTime = "10:00:00" and city_id =  "HCM" and center_id = "HCM_quan2" and court_id = "Court#2" and player_id = "Customer#B";

CALL createCity("HCM");
CALL createCityCenter("HCM_quan2", "HCM");
CALL createCityCenterCourt("Court#2","HCM","HCM_quan2");
CALL createPlayer("Customer#B");
CALL CreateBooking(18, "2020-04-10 09:27:18", "2020-04-11", "9:00:00" , "10:00:00" , 'HCM', 'HCM_quan2', 'Court#2', 'Customer#B');

# CancelBooking (CA) parameters: booking_id, customer_id
/* test 6 */		   
Call CancelBooking(1, 'Customer#X');
/* error: CA-001 */
		   
/* test 7 */
Call CancelBooking(12, 'Customer#B');
/* error: CA-002 */

/* test 8 */
Call CancelBooking(1, 'Customer#B');
/* error: CA-003 */

/* test 9 */
Call CancelBooking(11, 'Customer#E');
/* error: CA-004 */