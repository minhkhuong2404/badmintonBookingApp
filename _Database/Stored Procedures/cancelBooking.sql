-- cancelBooking(bookingId, playerId)
DROP PROCEDURE IF EXISTS CancelBooking;
DELIMITER //
CREATE PROCEDURE CancelBooking(
in pbooking varchar(50), in pplayer varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
START TRANSACTION;

-- CA-001: customer not exist
IF NOT EXISTS ( SELECT * 
				FROM player
                WHERE player_id = pplayer )
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CA-001';
-- CA-002: booking not exist
ELSEIF NOT EXISTS ( SELECT * 
				FROM booking
                WHERE booking_id = pbooking )
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CA-002';
-- CA-003: this customer not own the booking
ELSEIF NOT EXISTS ( SELECT * 
				FROM booking
                WHERE booking_id = pbooking and player_id = pplayer )
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CA-003';
-- CA-004: violates 24 hours before start time
ELSEIF date_add(date(now()),interval 24 hour) >
									( SELECT date_add(date_add(date_add(date, INTERVAL startHour HOUR), INTERVAL startMin MINUTE), INTERVAL startSec SECOND) 
									  FROM b_app.booking 
									  WHERE booking_id = pbooking)
    THEN SIGNAL SQLSTATE '45000'
         SET MESSAGE_TEXT = 'CA-004';
END IF;
-- Delete the booking
DELETE FROM booking WHERE booking_id = pbooking;
END //
DELIMITER ;


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