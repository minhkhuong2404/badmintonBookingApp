DROP TABLE IF EXISTS center;
CREATE TABLE center(
  center_id varchar(50) NOT NULL PRIMARY KEY,
  city_id varchar(50) ,
  min_length int,
  max_length int,
  CONSTRAINT city_fk
	 FOREIGN KEY (city_id) REFERENCES city (city_id) ON DELETE CASCADE
);

DROP PROCEDURE IF EXISTS CreateBooking;
DELIMITER //
CREATE PROCEDURE CreateBooking(
in booking_id varchar(50),
in timestamp datetime, 
in pdate date, 
in pstartTime TIME,
in pendTime TIME, 
in cityId varchar(50), 
in centerId varchar(50), 
in courtId varchar(50),
in playerId varchar(50))
BEGIN
DECLARE openTime time;
DECLARE closeTime time;
DECLARE startTime time;
DECLARE endTime time;
DECLARE playTime time;
DECLARE code CHAR(5) DEFAULT '00000';
DECLARE msg TEXT;
DECLARE nrows INT;
DECLARE result TEXT;
DECLARE minlength int;

-- Handler for Integrity Constraint
DECLARE IntegrityConstraintException CONDITION for 1452;
DECLARE EXIT HANDLER FOR  IntegrityConstraintException
	BEGIN
      GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE;
	  -- Check existence of Court / Customer
	  IF NOT EXISTS(SELECT * FROM court WHERE courtId = court_id)
	  THEN IF NOT EXISTS(SELECT * FROM player WHERE playerId = player_id)
		   THEN SELECT @p1, 'CB-111'; -- Court & Customer not existed.
           ELSE SELECT @p1, 'CB-110'; -- Court not existed.
           END IF;
	  ELSEIF NOT EXISTS(SELECT * FROM player WHERE playerId = player_id)
	  THEN SELECT @p1, 'CB-109'; -- Customer not existed.
      END IF;
	  ROLLBACK;
	END;
-- Handler for Start/End Time Constraint
DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
	  GET STACKED DIAGNOSTICS CONDITION 1 
		@p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
	  SELECT @p1, @p2;
	  ROLLBACK;
	END;
START TRANSACTION;
-- Convert to time format
SELECT open_hour from OpenHour where centerId = center and id = cityId and day_of_week = 'Monday'into openTime;
SELECT close_hour from OpenHour where centerId = center and id = cityId and day_of_week = 'Monday' into closeTime;


SELECT pstartTime into startTime;
SELECT pendTime into endTime;
SELECT TIMEDIFF(endTime, startTime) into playTime;
-- Throw exception for Start/End Time Constraint
-- CB-001: startTime < DATE(NOW())
IF date < DATE(NOW())
THEN 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT = 'CB-001'; 
END IF;
-- CB-002: startTime < openTime 
IF  startTime < openTime 
THEN 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT = 'CB-002';
END IF;
-- CB-003 endTime > closeTime 
IF  endTime > closeTime 
THEN 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT = 'CB-003';
END IF;
-- CB-004: endTime < startTime
IF endTime < startTime
THEN
	SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'CB-004'; 
END IF;

select min_length from center where center_id = centerId and cityId = city_id into minlength;
if minlength < playtime
then
	SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT = 'ERROR';
   end if;
If  exists (select* from center where center_id = 'HCMquan1' and min_length > 45)
then 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT = 'ERROR';
end if;
If  exists (select* from center where center_id = 'HCMquan4' and min_length > 60)
then 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT = 'ERROR';
end if;
If  exists (select* from center where center_id = 'HCMquan5' and min_length > 75)
then 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT = 'ERROR';
end if;
If  exists (select* from center where center_id = 'HCMquan2' and min_length > 90)
then 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT = 'ERROR';
end if;
   
if playTime > 90
then 
	SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT = 'ERROR';
end if;
-- CB-005: playtime invalid (valid: 45m, 1h, 1h15m, 1h30m)
IF (playTime <> MAKETIME(0,45,0) and
    playTime <> MAKETIME(1,0,0) and
    playTime <> MAKETIME(1,15,0) and
    playTime <> MAKETIME(1,30,0))
THEN 
	SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'CB-005'; 
END IF;
-- CB-006: overlapping booking

IF EXISTS ( SELECT *
			FROM booking
			WHERE ( date = pdate and
				    court_id = courtId and
				    startTime < pendTime and
				    endTime > pstartTime ) )
THEN 
	SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'CB-006'; 
END IF;

-- CB-007: have pending booking
IF EXISTS ( SELECT *
            FROM booking
			WHERE   player_id = playerId and
					paymentStatus = 0 and
					date_add(date, INTERVAL TIME_TO_SEC(startTime) SECOND) < DATE(NOW()) )
THEN
	SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'CB-007';
END IF;
-- CB-008: no more than 3 bookings
IF 3 <= ( SELECT COUNT(*) FROM booking
			WHERE player_id = playerId)
THEN
	SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'CB-008';
END IF;
END //
DELIMITER ;