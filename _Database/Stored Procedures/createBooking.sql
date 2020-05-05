-- createBooking(date, startTime, endTime, cityId, centerId, courtId, playerId)
DROP PROCEDURE IF EXISTS CreateBooking;
DELIMITER //
CREATE PROCEDURE CreateBooking(
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
DECLARE PLAYTIME_45M time;
DECLARE PLAYTIME_1H time;
DECLARE PLAYTIME_1H15 time;
DECLARE PLAYTIME_1H30 time;


/* Assign variables */
SELECT MAKETIME(7, 0, 0) into openTime;   --set open hour to 7am--
SELECT MAKETIME(21, 0, 0) into closeTime; --set end hour to 9pm-- 
SELECT pstartTime into startTime;
SELECT pendTime into endTime;
SELECT TIMEDIFF(endTime, startTime) into playTime;
SELECT MAKETIME(0,45,0) into PLAYTIME_45M; --set 45 minutes play-time-- 
SELECT MAKETIME(1,0,0) into PLAYTIME_1H;   --set 60 minutes play-time--
SELECT MAKETIME(1,15,0) into PLAYTIME_1H15;--set 75 minutes play-time--
SELECT MAKETIME(1,30,0) into PLAYTIME_1H30;--set 90 minutes play-time, 90 is max minutes for playing--


IF NOT EXISTS (SELECT * FROM city WHERE city_id = pcityId)
THEN
	SET resultCode = 'CB-001'; --check city existed or not --
ELSEIF NOT EXISTS (SELECT * FROM center WHERE center_id = pcenterId)
THEN
	SET resultCode = 'CB-002'; --check center existed or not --
ELSEIF NOT EXISTS (SELECT * FROM court WHERE court_id = pcourtId)
THEN
	SET resultCode = 'CB-003'; --check court existed or not --
ELSEIF ((date_add(pdate, INTERVAL TIME_TO_SEC(startTime) SECOND)) <=
       date_add(DATE(NOW()), INTERVAL TIME_TO_SEC(TIME(NOW())) SECOND))
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
ELSEIF (playTime <> PLAYTIME_45M and
		playTime <> PLAYTIME_1H and
		playTime <> PLAYTIME_1H15 and
		playTime <> PLAYTIME_1H30)
THEN 
    SET resultCode = 'CB-009'; 
ELSEIF EXISTS ( SELECT *
			FROM booking b
			WHERE ( date = pdate and court_id = pcourtId and
				    (b.startTime < pendTime) and (pstartTime < b.endTime) ) )
THEN 
    SET resultCode = 'CB-010'; 
ELSEIF EXISTS ( SELECT *
            FROM booking b
			WHERE ( b.player_id = pplayerId and
					b.status = 0 and
					date_add(b.date, INTERVAL TIME_TO_SEC(b.startTime) SECOND) <
					date_add(DATE(NOW()), INTERVAL TIME_TO_SEC(TIME(NOW())) SECOND)) )
THEN
    SET resultCode = 'CB-011';
ELSEIF 3 <= ( SELECT COUNT(*) FROM booking
			WHERE player_id = pplayerId)
THEN
    SET resultCode = 'CB-012'; 
ELSE
	INSERT INTO booking (timestamp, date, startTime, endTime, city_id, center_id, court_id, player_id)
    VALUES (NOW(), pdate, pstartTime, pendTime, pcityId, pcenterId, pcourtId, pplayerId);
	SET resultCode = '200';
END IF;

END //
DELIMITER ;

-- call CreateBooking(DATE("2021-04-23"),maketime(11, 30, 00),maketime(12, 30, 00),"A","A1", "A1C","player10",@code);
