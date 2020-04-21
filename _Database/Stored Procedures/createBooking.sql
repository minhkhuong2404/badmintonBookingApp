-- createBooking(booking_id, timestamp, date, startTime, endTime, cityId, centerId, courtId, playerId)
DROP PROCEDURE IF EXISTS CreateBooking;
DELIMITER //
CREATE PROCEDURE CreateBooking(
in pbookingId varchar(50),
in ptimestamp datetime, 
in pdate date, 
in pstartTime TIME,
in pendTime TIME, 
in pcityId varchar(50), 
in pcenterId varchar(50), 
in pcourtId varchar(50),
in pplayerId varchar(50),
OUT resultCode varchar(50))
BEGIN
DECLARE openTime time;
DECLARE closeTime time;
DECLARE startTime time;
DECLARE endTime time;
DECLARE playTime time;

/* Assign variables */
SELECT MAKETIME(7, 0, 0) into openTime;
SELECT MAKETIME(21, 0, 0) into closeTime;  
SELECT pstartTime into startTime;
SELECT pendTime into endTime;
SELECT TIMEDIFF(endTime, startTime) into playTime;


IF NOT pbookingId REGEXP '^[a-zA-Z0-9]*$'
THEN
     SET resultCode = 'CB-000';
ELSEIF EXISTS (SELECT * FROM booking WHERE booking_id = pbookingId)
THEN
	SET resultCode ="CB-100";
ELSEIF NOT EXISTS (SELECT * FROM city WHERE city_id = pcityId)
THEN
	SET resultCode ="CB-001";
ELSEIF NOT EXISTS (SELECT * FROM center WHERE center_id = pcenterId)
THEN
	SET resultCode ="CB-002";
ELSEIF NOT EXISTS (SELECT * FROM court WHERE court_id = pcourtId)
THEN
	SET resultCode ="CB-003";
ELSEIF NOT EXISTS (SELECT * FROM player WHERE player_id = pplayerId)
THEN
	SET resultCode ="CB-004";
ELSEIF date_add(pdate, INTERVAL time_to_sec(startTime) SECOND) < DATE(NOW())
THEN 
   SET resultCode = 'CB-005';
ELSEIF  startTime < openTime 
THEN 
   SET resultCode = 'CB-006';
ELSEIF  endTime > closeTime 
THEN 
   SET resultCode = 'CB-007'; 
ELSEIF endTime < startTime
THEN
    SET resultCode = 'CB-008'; 
ELSEIF (playTime <> MAKETIME(0,45,0) and
		playTime <> MAKETIME(1,0,0) and
		playTime <> MAKETIME(1,15,0) and
		playTime <> MAKETIME(1,30,0))
THEN 
    SET resultCode = 'CB-009'; 
ELSEIF EXISTS ( SELECT *
			FROM booking
			WHERE ( date = pdate and
				    court_id = pcourtId and
				    startTime < pendTime and
				    endTime > pstartTime ) )
THEN 
    SET resultCode = 'CB-010'; 
ELSEIF EXISTS ( SELECT *
            FROM booking
			WHERE ( player_id = pplayerId and
					status = 0 and
					date_add(date, INTERVAL TIME_TO_SEC(startTime) SECOND) < DATE(NOW())) )
THEN
    SET resultCode = 'CB-011';
ELSEIF 3 <= ( SELECT COUNT(*) FROM booking
			WHERE player_id = pplayerId)
THEN
    SET resultCode = 'CB-012'; 
ELSE
	INSERT INTO booking 
    VALUES (pbookingId, ptimestamp, pdate, pstartTime, pendTime, pcityId, pcenterId, pcourtId, pplayerId, 0);
	SET resultCode = '200';
END IF;

SELECT resultCode;

END //
DELIMITER ;
