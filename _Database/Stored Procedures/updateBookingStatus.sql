-- updateBookingStatus(status, bookingId, cityId, centerId, staffId)
DROP PROCEDURE IF EXISTS updateBookingStatus;
DELIMITER //
CREATE PROCEDURE updateBookingStatus(
in pstatus int,
in pbookingId varchar(50), 
in pcityId varchar(50), 
in pcenterId varchar(50),
in pstaffId varchar(50),
OUT resultCode varchar(50))
BEGIN

IF NOT pbookingId REGEXP '^[0-9]*$'
THEN 
     SET resultCode = 'UBS-000';
ELSEIF NOT pcityId REGEXP '^[a-zA-Z0-9]*$'
THEN 
     SET resultCode = 'UBS-001';
ELSEIF NOT pcenterId REGEXP '^[a-zA-Z0-9]*$'
THEN 
     SET resultCode = 'UBS-002';
ELSEIF NOT pstaffId REGEXP '^[a-zA-Z0-9]*$'
THEN 
     SET resultCode = 'UBS-003';
ELSEIF NOT EXISTS ( SELECT * 
					FROM booking
                    WHERE booking_id = pbookingId)
THEN
     SET resultCode = 'UBS-004';
ELSEIF NOT EXISTS ( SELECT * 
				FROM city
                    WHERE city_id = pcityId )
THEN
     SET resultCode = 'UBS-005';
ELSEIF NOT EXISTS ( SELECT * 
					FROM center
                    WHERE center_id = pcenterId )
THEN
     SET resultCode = 'UBS-006';
ELSEIF NOT EXISTS ( SELECT * 
					FROM staff
                    WHERE staff_id = pstaffId and
                    city_id = pcityId and
                    center_id = pcenterId )
THEN
     SET resultCode = 'UBS-007';
ELSEIF NOT EXISTS ( SELECT *
					FROM booking
                    WHERE booking_id = pbookingId and
                    city_id = pcityId and
                    center_id = pcenterId )
THEN
     SET resultCode = 'UBS-008';
ELSE 
	UPDATE booking
	SET status = pstatus
	WHERE booking_id = pbookingId;
     SET resultCode = '200';
END IF;

SELECT resultCode;

END //
DELIMITER ;