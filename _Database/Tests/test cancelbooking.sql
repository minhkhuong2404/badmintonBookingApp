/* Test cases for cancelBooking 
CA-001 | customer does not exist 
CA-002 | booking does not exist 
CA-003 | this customer does not own the booking 
CA-004 | violates 24 hours before start time
*/

# CancelBooking (CA) parameters: booking_id, customer_id
/* test 6 */		   
Call CancelBooking('1', 'Customer#X');
/* error: CA-001 */
		   
/* test 7 */
Call CancelBooking('12', 'Customer#B');
/* error: CA-002 */

/* test 8 */
Call CancelBooking('1', 'Customer#B');
/* error: CA-003 */

/* test 9 */
Call CancelBooking('11', 'Customer#E');
/* error: CA-004 */

-- updateBookingStatus(status, bookingId, cityId, centerId, staffId)
