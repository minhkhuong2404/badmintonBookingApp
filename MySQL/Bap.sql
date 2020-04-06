DROP Database if exists b_app;
CREATE DATABASE b_app;
USE b_app;

DROP TABLE IF EXISTS city;
CREATE TABLE city(
  city_id varchar(50) NOT NULL PRIMARY KEY
);

DROP TABLE IF EXISTS center;
CREATE TABLE center(
  center_id varchar(50) NOT NULL PRIMARY KEY,
  city_id varchar(50),
  CONSTRAINT city_fk
	 FOREIGN KEY (city_id) REFERENCES city (city_id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS court;
CREATE TABLE court(
  court_id varchar(50) NOT NULL PRIMARY KEY,
  center_id varchar(50),
  city_id varchar(50),
  CONSTRAINT court_center_fk
	 FOREIGN KEY (center_id) REFERENCES center (center_id) ON DELETE CASCADE,
  CONSTRAINT court_city_fk   
     FOREIGN KEY (city_id) REFERENCES city (city_id) ON DELETE CASCADE
     
);

DROP TABLE IF EXISTS staff;
CREATE TABLE staff(
  staff_id varchar(50) NOT NULl PRIMARY KEY,
  center_id varchar(50) not null,
  city_id varchar(50) not null,
  CONSTRAINT staff_center_fk
	 FOREIGN KEY (center_id) REFERENCES center (center_id) ON DELETE CASCADE,
  CONSTRAINT staff_city_fk
     FOREIGN KEY (city_id) REFERENCES city (city_id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS player;
CREATE TABLE player(
  player_id varchar(50) NOT NULL PRIMARY KEY
);

DROP TABLE IF EXISTS booking;
CREATE TABLE booking(
  booking_id varchar(50) NOT NULL PRIMARY KEY,
  date date NOT NULL,
  startTime time NOT NULL,
  endTime time NOT NULL,
  city_id varchar(50) NOT NULL,
  center_id varchar(50) NOT NULL,
  court_id varchar(50),
  player_id varchar(50),
  timestamp datetime NOT NULL,
  paymentstatus int default 0,
  CONSTRAINT court_fk
     FOREIGN KEY (court_id) REFERENCES court (court_id) ON DELETE CASCADE,
  CONSTRAINT player_fk
     FOREIGN KEY (player_id) REFERENCES player (player_id) ON DELETE CASCADE	 
);

-- city
INSERT INTO `city` (`city_id`) VALUES ('Can_Tho');
INSERT INTO `city` (`city_id`) VALUES ('HCM');
INSERT INTO `city` (`city_id`) VALUES ('Hanoi');

-- center
INSERT INTO `center` (`center_id`) VALUES ('HCM_quan1');
INSERT INTO `center` (`center_id`) VALUES ('HCM_quan2');
INSERT INTO `center` (`center_id`) VALUES ('HCM_quan3');
INSERT INTO `center` (`center_id`) VALUES ('HCM_quan4');
INSERT INTO `center` (`center_id`) VALUES ('HCM_quan5');


-- court
INSERT INTO `court` (`court_id`) VALUES ('Court#1');
INSERT INTO `court` (`court_id`) VALUES ('Court#2');
INSERT INTO `court` (`court_id`) VALUES ('Court#3');
INSERT INTO `court` (`court_id`) VALUES ('Court#4');
INSERT INTO `court` (`court_id`) VALUES ('Court#5');

-- staff
INSERT INTO `staff` (`staff_id`,`center_id`,`city_id`) VALUES ('Staff#1','HCM_quan1','HCM');
INSERT INTO `staff` (`staff_id`,`center_id`,`city_id`) VALUES ('Staff#2','HCM_quan2','Hanoi');
INSERT INTO `staff` (`staff_id`,`center_id`,`city_id`) VALUES ('Staff#3','HCM_quan3','Can_Tho');
INSERT INTO `staff` (`staff_id`,`center_id`,`city_id`) VALUES ('Staff#4','HCM_quan4','HCM');
INSERT INTO `staff` (`staff_id`,`center_id`,`city_id`) VALUES ('Staff#5','HCM_quan5','HCM');

-- player
INSERT INTO `player` (`player_id`) VALUES ('Customer#A');
INSERT INTO `player` (`player_id`) VALUES ('Customer#B');
INSERT INTO `player` (`player_id`) VALUES ('Customer#C');
INSERT INTO `player` (`player_id`) VALUES ('D');
INSERT INTO `player` (`player_id`) VALUES ('E');
INSERT INTO `player` (`player_id`) VALUES ('F');

-- booking booking_id, timestamp, date, startTime, endTime, cityId, centerId, courtId, playerId)
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`) VALUES (1,"2020-03-29 09:27:18",'2020-03-29',maketime(10,10,20),maketime(18,35,00),'HCM','HCM_quan1','Court#1','Customer#A');
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`) VALUES (2,'2020-03-29 09:27:18','2020-03-29',maketime(9,10,20),maketime(11,35,00),'Hanoi','HCM_quan2','Court#2','Customer#B');
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`) VALUES (3,'2020-03-29 09:27:18','2020-03-29',maketime(15,10,20),maketime(17,35,00),'Hanoi','HCM_quan2','Court#2','Customer#A');
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`) VALUES (4,'2020-03-29 09:27:18','2020-03-29',maketime(17,10,20),maketime(20,35,00),'HCM','HCM_quan4','Court#4','Customer#C');
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`) VALUES (6,'2020-03-29 09:27:18','2020-03-29',maketime(18,00,20),maketime(19,35,00),'Can_Tho','HCM_quan3','Court#5','D');
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`) VALUES (7,'2020-03-29 09:27:18','2020-03-29',maketime(9,10,20),maketime(11,35,00),'HCM','HCM_quan5','Court#5','E');
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`) VALUES (8,'2020-03-29 09:27:18','2020-03-29',maketime(12,10,20),maketime(13,35,00),'Can_Tho','HCM_quan3','Court#1','E');
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`) VALUES (9,'2020-03-29 09:27:18','2020-03-29',maketime(10,10,20),maketime(11,35,00),'Hanoi','HCM_quan2','Court#1','E');
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`) VALUES (10,'2020-03-29 09:27:18','2020-03-29',maketime(17,10,20),maketime(18,35,00),'HCM','HCM_quan1','Court#1','F');
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`) VALUES (11,'2020-03-29 09:27:18','2020-03-30',maketime(20,10,20),maketime(21,00,00),'Can_Tho','HCM_quan3','Court#1','E');

DROP PROCEDURE IF EXISTS createCity;
DELIMITER //
CREATE PROCEDURE createCity(
in pcity varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
  
START TRANSACTION;
IF pcity IN ( select city_id from city)
THEN 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT ="City existed";
END IF;
END //
DELIMITER ;

-- createCityCenter(centerId, cityId)

DROP PROCEDURE IF EXISTS createCityCenter;
DELIMITER //
CREATE PROCEDURE createCityCenter(
in pcity varchar(50), in pcenter varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
  
START TRANSACTION;
IF pcity IN ( select city_id from city) and pcenter IN ( select center_id from center)
THEN 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT ="Center existed";
end if;
end//
DELIMITER ;

-- createCityCenterCourt(courtId, cityId, centerId)

DROP PROCEDURE IF EXISTS createCityCenterCourt;
DELIMITER //
CREATE PROCEDURE createCityCenterCourt(
in pcity varchar(50), in pcenter varchar(50), in pcourt varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
  
START TRANSACTION;
IF pcity IN ( select city_id from city) and pcenter IN ( select center_id from center) and pcourt In (select court_id from court)
THEN 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT ="Court existed";
end if;
end//
DELIMITER ;

-- createPlayer(playerId)
DROP PROCEDURE IF EXISTS createPlayer;
DELIMITER //
CREATE PROCEDURE createPlayer(
in pplayer varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
  
START TRANSACTION;
if pplayer in ( select player_id from player )
then 
	SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT ="Player existed";
end if;
end//
DELIMITER ;

-- create Staff. createStaff(staffId, cityId, centerId)
DROP PROCEDURE IF EXISTS createStaff;
DELIMITER //
CREATE PROCEDURE createStaff(
in pstaff varchar(50), in pcenter varchar(50), in pcity varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
  
START TRANSACTION;
if pstaff in ( select staff_id from staff ) and pcity in (select city_id from city) and pcenter in ( select center_id from center)
then 
	SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT ="He/She is our stasff";
end if;
end//
DELIMITER ;

-- createBooking(booking_id, timestamp, date, startTime, endTime, cityId, centerId, courtId, playerId)
DROP PROCEDURE IF EXISTS CreateBooking;
DELIMITER //
CREATE PROCEDURE CreateBooking(
in booking_id varchar(50),
in timestamp datetime, 
in pdate date, 
in startTime datetime,
in startMin datetime,
in endTime datetime,
in endMin datetime, 
in cityId varchar(50), 
in centerId varchar(50), 
in courtId varchar(50),
in playerId varchar(50))
BEGIN
DECLARE openTime datetime;
DECLARE closeTime datetime;
DECLARE startTime datetime;
DECLARE endTime datetime;
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
SELECT 
  date_add(date_add(pdate, INTERVAL 7 HOUR), INTERVAL 0 MINUTE)
  into openTime;
SELECT 
  date_add(date_add(pdate, INTERVAL 21 HOUR), INTERVAL 0 MINUTE)
  into closeTime;  
SELECT 
  date_add(date_add(pdate, INTERVAL startTime HOUR), INTERVAL startMin MINUTE)
  into startTime;
SELECT 
  date_add(date_add(pdate, INTERVAL endTime HOUR), INTERVAL endMin MINUTE)
  into endTime;
SELECT TIMEDIFF(endTime, startTime) into playTime;
-- Throw exception for Start/End Time Constraint
-- CB-001: startTime < DATE(NOW())
IF startTime < DATE(NOW())
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
				    TIME(startTime) < MAKETIME(endTime, endMin, 0) and
				    TIME(endTime) > MAKETIME(startTime, startMin, 0) ) )
THEN 
	SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'CB-006'; 
END IF;
-- CB-007: have pending booking
IF EXISTS ( SELECT *
            FROM booking
			WHERE   player_id = playerId and
					paymentStatus = 0 and
					date_add(date_add(pdate, INTERVAL endTime HOUR), INTERVAL endMin MINUTE) < DATE(NOW()) )
THEN
	SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'CB-007';
END IF;
-- CB-008: no more than 3 bookings
IF 3 <= ( SELECT COUNT(*) FROM booking
			WHERE player_id = playerId and 
			date_add(date_add(pdate, INTERVAL endTime HOUR), INTERVAL endMin MINUTE) >= DATE(NOW()) )
THEN
	SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'CB-008';
END IF;
-- Add booking to table
SELECT 'Success!';
END //
DELIMITER ;

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
									( SELECT date_add(date_add(date, INTERVAL startTime HOUR), INTERVAL startMin MINUTE) 
									  FROM b_app.booking 
									  WHERE booking_id = pbooking)
    THEN SIGNAL SQLSTATE '45000'
         SET MESSAGE_TEXT = 'CA-004';
END IF;

-- Delete the booking
DELETE FROM booking WHERE booking_id = pbooking;
END //
DELIMITER ;

-- updateBookingStatus(status, bookingId, cityId, centerId, staffId)
DROP PROCEDURE IF EXISTS updateBookingStatus;
DELIMITER //
CREATE PROCEDURE updateBookingStatus(
in status int,
in bookingId varchar(50), 
in cityId varchar(50), 
in cenyterId varchar(50),
in staffId varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
START TRANSACTION;
-- CP-001: staff not exist
IF NOT EXISTS ( SELECT * 
				FROM staff
                WHERE staff_id = staffId )
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CP-001';
-- CP-002: booking not exist
ELSEIF NOT EXISTS ( SELECT * 
				FROM booking
                WHERE booking_id = bookingId )
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CP-002';
-- CP-003: this staff has no relationship with the booking (join booking - court - center - staff)
ELSEIF NOT EXISTS ( SELECT b.booking_id, co.court_id, s.staff_id
				FROM booking b, court co, center ce, staff s, city ci
				WHERE ( co.court_id = b.court_id and
						ce.center_id = co.center_id and
						s.center_id = ce.center_id and
                        s.city_id = ci.city_id and
						b.booking_id = bookingId and
						s.staff_id =  staffId) )
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CP-003';
-- change status
ELSE	UPDATE booking
		SET paymentstatus = status
        WHERE booking_id = bookingId;
END IF;
END //
DELIMITER ;








CALL createCity("Hanoi");
Call createCityCenter("Hanoi","HCM_quan2");
call createCityCenterCourt("HCM","HCM_quan1","Court#1");
call createplayer("customer#A");
call createStaff("Staff#1","HCM_quan1","HCM");
# CancelBooking (CA) parameters: booking_id, customer_id

-- CA-001: customer not exist
Call CancelBooking('1', 'Customer#A');
-- CA-002: booking not exist
Call CancelBooking('12', 'Customer#B');
-- CA-003: this customer not own the booking
Call CancelBooking('1', 'Customer#B');
-- CA-004: violates 24 hours before start time
Call CancelBooking('11', 'E');
-- CP-001: staff not exist
CALL updateBookingStatus(0 ,'1', 'HCM','HCM_quan1','Staff#6');
-- CP-002: booking not exist
CALL updateBookingStatus(0 ,'12', 'Hanoi','HCM_quan2','Staff#1');
-- CP-003: this staff has no relationship with the booking (join booking - court - center - staff)
CALL updateBookingStatus(0 ,'1', 'Can_tho','HCM_quan1','Staff#3' );
-- staff
-- INSERT INTO `staff` (`staff_id`,`center_id`,`city_id`) VALUES ('Staff#1','HCM_quan1','HCM');
-- INSERT INTO `staff` (`staff_id`,`center_id`,`city_id`) VALUES ('Staff#2','HCM_quan2','Hanoi');
-- INSERT INTO `staff` (`staff_id`,`center_id`,`city_id`) VALUES ('Staff#3','HCM_quan3','Can_Tho');
-- INSERT INTO `staff` (`staff_id`,`center_id`,`city_id`) VALUES ('Staff#4','HCM_quan4','HCM');
-- INSERT INTO `staff` (`staff_id`,`center_id`,`city_id`) VALUES ('Staff#5','HCM_quan5','HCM');


