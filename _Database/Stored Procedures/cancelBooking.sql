-- cancelBooking(bookingId, playerId)
DROP PROCEDURE IF EXISTS CancelBooking;
DELIMITER //
CREATE PROCEDURE CancelBooking(  
in pbookingId varchar(50), 
in pplayerId varchar(50),
OUT resultCode varchar(50))
BEGIN

IF NOT EXISTS ( SELECT * 
				FROM booking
                WHERE booking_id = pbookingId )
THEN SET resultCode = 'CA-003';
ELSEIF NOT EXISTS ( SELECT * 
				FROM booking
                WHERE booking_id = pbookingId and player_id = pplayerId )
THEN SET resultCode = 'CA-004';
ELSEIF date_add(date(now()), INTERVAL 24 HOUR) >
									( SELECT date_add(date, INTERVAL TIME_TO_SEC(startTime) SECOND) 
									  FROM booking 
									  WHERE booking_id = pbookingId) --Players can not cancel after 24 hours-booking--
THEN SET resultCode = 'CA-005';
ELSE 
	DELETE FROM booking WHERE booking_id = pbookingId;
	SET resultCode = '200';
END IF;

SELECT resultCode;

END //
DELIMITER ;
