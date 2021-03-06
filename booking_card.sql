drop table if exists booking_card;
create table booking_card(
	card_id int auto_increment primary key,
    player_id varchar(50),
	remain_booking int ,
    time_bought datetime,
    expire_date datetime
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
in playerId varchar(50),
in cardId int)
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
SELECT MAKETIME(7, 0, 0) into openTime;
SELECT MAKETIME(21, 0, 0) into closeTime;  
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
UPDATE booking_card
SET
	remain_booking = remain_booking - 1 
where
	cardId = card_id;
END //
DELIMITER ;

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
if exists (select booking_id from booking_card where booking_id > 0)
then
UPDATE booking_card
SET
	remain_booking = remain_booking + 1 
where
	cardId = card_id;
    end if;
END //
DELIMITER ;

INSERT INTO `booking_card` (`card_id` , `card`,  `player_id` , `remain_booking` , `time_bought`, `expire_date` ) VALUES ( '1', 'player1' , '8' , '2020-05-05 20:19:18' , '2020-06-04 20:19:18' );  
INSERT INTO `booking_card` (`card_id` , `player_id` , `remain_booking` , `time_bought`, `expire_date` ) VALUES ( '2', 'player1' , '9' , '2020-05-03 14:12:24' , '2020-06-02 14:12:24'  );  
INSERT INTO `booking_card` (`card_id` , `player_id` , `remain_booking` , `time_bought`, `expire_date` ) VALUES ( '3', 'player3' , '3' , '2020-05-02 15:15:29' , '2020-06-01 15:15:29'  );  
INSERT INTO `booking_card` (`card_id` , `player_id` , `remain_booking` , `time_bought`, `expire_date` ) VALUES ( '4', 'player4' , '4' , '2020-05-01 03:30:13' ,  '2020-06-31 03:30:13' );  
INSERT INTO `booking_card` (`card_id` , `player_id` , `remain_booking` , `time_bought`, `expire_date` ) VALUES ( '5', 'player5' , '1' , '2020-04-30 06:39:13' , '2020-05-30 06:39:13' );  
INSERT INTO `booking_card` (`card_id` , `player_id` , `remain_booking` , `time_bought`, `expire_date` ) VALUES ( '6', 'player6' , '5' , '2020-04-22 07:13:09' ,  '2020-05-22 07:13:09' );  
INSERT INTO `booking_card` (`card_id` , `player_id` , `remain_booking` , `time_bought`, `expire_date` ) VALUES ( '7', 'player7' , '6' , '2020-04-24 11:11:11' , '2020-05-24 11:11:11' );  
INSERT INTO `booking_card` (`card_id` , `player_id` , `remain_booking` , `time_bought`, `expire_date` ) VALUES ( '8', 'player7' , '9' , '2020-04-25 01:00:00' , '2020-05-25 01:00:00' );  
INSERT INTO `booking_card` (`card_id` , `player_id` , `remain_booking` , `time_bought`, `expire_date` ) VALUES ( '9', 'player7' , '10' , '2020-04-28 12:11:15' , '2020-05-28 12:11:15' );  
INSERT INTO `booking_card` (`card_id` , `player_id` , `remain_booking` , `time_bought`, `expire_date` ) VALUES ( '10', 'player8' , '2' , '2020-04-27 16:17:18' , '2020-05-27 16:17:18');

