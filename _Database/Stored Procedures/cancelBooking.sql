-- cancelBooking(bookingId, playerId)
DROP PROCEDURE IF EXISTS CancelBooking;
DELIMITER //
CREATE PROCEDURE CancelBooking(
in pbookingId varchar(50), in pplayerId varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
START TRANSACTION;
IF NOT pbookingId REGEXP '^[a-zA-Z0-9]*$'
THEN 
	 SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CA-000'; -- CA-000: pbookingId invalid
ELSEIF NOT pplayerId REGEXP '^[a-zA-Z0-9]*$'
THEN 
	 SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CA-001'; -- CA-001: pplayerId invalid
ELSEIF NOT EXISTS ( SELECT * 
				FROM player
                WHERE player_id = pplayerId )
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CA-002'; -- CA-002: playerId not exist
ELSEIF NOT EXISTS ( SELECT * 
				FROM booking
                WHERE booking_id = pbookingId )
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CA-003';-- CA-003: bookingId not exist
ELSEIF NOT EXISTS ( SELECT * 
				FROM booking
                WHERE booking_id = pbookingId and player_id = pplayerId )
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CA-004';  -- CA-004: playerId not own the bookingId
ELSEIF date_add(date(now()), INTERVAL 24 HOUR) >
									( SELECT date_add(date, INTERVAL TIME_TO_SEC(startTime) SECOND) 
									  FROM booking 
									  WHERE booking_id = pbookingId)
THEN SIGNAL SQLSTATE '45000'
	 SET MESSAGE_TEXT = 'CA-005'; -- CA-004: violates 24 hours before start time
ELSE DELETE FROM booking WHERE booking_id = pbookingId;
END IF;
END //
DELIMITER ;
