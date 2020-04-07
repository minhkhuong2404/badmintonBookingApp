-- updateBookingStatus(status, bookingId, cityId, centerId, staffId)
DROP PROCEDURE IF EXISTS updateBookingStatus;
DELIMITER //
CREATE PROCEDURE updateBookingStatus(
in pstatus int,
in pbookingId varchar(50), 
in pcityId varchar(50), 
in pcenterId varchar(50),
in pstaffId varchar(50))
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
     SET MESSAGE_TEXT = 'UBS-000';
ELSEIF NOT pcityId REGEXP '^[a-zA-Z0-9]*$'
THEN 
     SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'UBS-001';
ELSEIF NOT pcenterId REGEXP '^[a-zA-Z0-9]*$'
THEN 
     SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'UBS-002';
ELSEIF NOT pstaffId REGEXP '^[a-zA-Z0-9]*$'
THEN 
     SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'UBS-003';
ELSEIF NOT EXISTS ( SELECT * 
					FROM booking
                    WHERE booking_id = pbookingId)
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'UBS-004';
ELSEIF NOT EXISTS ( SELECT * 
				FROM city
                    WHERE city_id = pcityId )
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'UBS-005';
ELSEIF NOT EXISTS ( SELECT * 
					FROM center
                    WHERE center_id = pcenterId )
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'UBS-006';
ELSEIF NOT EXISTS ( SELECT * 
					FROM staff
                    WHERE staff_id = pstaffId and
                    city_id = pcityId and
                    center_id = pcenterId )
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'UBS-007';
ELSEIF NOT EXISTS ( SELECT *
					FROM booking
                    WHERE booking_id = pbookingId and
                    city_id = pcityId and
                    center_id = pcenterId )
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'UBS-008';
ELSE 
	UPDATE booking
	SET status = pstatus
	WHERE booking_id = bookingId;
END IF;
END //
DELIMITER ;