DROP DATABASE IF EXISTS b_app;
CREATE DATABASE b_app;
USE b_app;

DROP TABLE IF EXISTS city;
CREATE TABLE city(
  city_id varchar(50) NOT NULL PRIMARY KEY
);

DROP TABLE IF EXISTS center;
CREATE TABLE center(
  center_id varchar(50) NOT NULL PRIMARY KEY,
  city_id varchar(50) ,
  CONSTRAINT city_fk
	 FOREIGN KEY (city_id) REFERENCES city (city_id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS court;
CREATE TABLE court(
  court_id varchar(50) NOT NULL PRIMARY KEY,
  city_id varchar(50) ,
  center_id varchar(50) ,
  CONSTRAINT court_center_fk
	 FOREIGN KEY (center_id) REFERENCES center (center_id) ON DELETE CASCADE,
  CONSTRAINT court_city_fk   
     FOREIGN KEY (city_id) REFERENCES city (city_id) ON DELETE CASCADE
     
);

DROP TABLE IF EXISTS staff;
CREATE TABLE staff(
  staff_id varchar(50) NOT NULl PRIMARY KEY,
  city_id varchar(50),
  center_id varchar(50),
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
  timestamp datetime NOT NULL,
  date date NOT NULL,
  startTime TIME NOT NULL,
  endTime TIME NOT NULL,
  city_id varchar(50) NOT NULL,
  center_id varchar(50) NOT NULL,
  court_id varchar(50),
  player_id varchar(50),
  status int default 0,
  CONSTRAINT booking_city_fk
     FOREIGN KEY (city_id) REFERENCES city (city_id) ON DELETE CASCADE,
  CONSTRAINT booking_center_fk
     FOREIGN KEY (center_id) REFERENCES center (center_id) ON DELETE CASCADE,
  CONSTRAINT booking_court_fk
     FOREIGN KEY (court_id) REFERENCES court (court_id) ON DELETE CASCADE,
  CONSTRAINT booking_player_fk
     FOREIGN KEY (player_id) REFERENCES player (player_id) ON DELETE CASCADE	 
);

-- city
INSERT INTO `city` (`city_id`) VALUES ('Can_Tho');
INSERT INTO `city` (`city_id`) VALUES ('HCM');
INSERT INTO `city` (`city_id`) VALUES ('Hanoi');
INSERT INTO `city` (`city_id`) VALUES ('CityA');

-- center
INSERT INTO `center` (`center_id`) VALUES ('HCM_quan1');
INSERT INTO `center` (`center_id`) VALUES ('HCM_quan2');
INSERT INTO `center` (`center_id`) VALUES ('HCM_quan3');
INSERT INTO `center` (`center_id`) VALUES ('HCM_quan4');
INSERT INTO `center` (`center_id`) VALUES ('HCM_quan5');
INSERT INTO `center` (`center_id`) VALUES ('CenterA');


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
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) VALUES (1,"2020-03-29 09:27:18",'2020-03-29','10:10:00','18:35:00','HCM','HCM_quan1','Court#1','Customer#A',0);
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) VALUES (2,'2020-03-29 09:27:18','2020-03-29','9:10:00','11:35:00','Hanoi','HCM_quan2','Court#2','Customer#B',0);
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) VALUES (3,'2020-03-29 09:27:18','2020-03-29','15:10:00','17:35:00','Hanoi','HCM_quan2','Court#2','Customer#A',0);
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) VALUES (4,'2020-03-29 09:27:18','2020-03-29','17:10:00','20:35:00','HCM','HCM_quan4','Court#4','Customer#C',1);
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) VALUES (6,'2020-03-29 09:27:18','2020-03-29','18:00:00','19:35:00','Can_Tho','HCM_quan3','Court#5','D',0);
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) VALUES (7,'2020-03-29 09:27:18','2020-03-29','9:10:00','11:35:00','HCM','HCM_quan5','Court#5','E',1);
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) VALUES (8,'2020-03-29 09:27:18','2020-03-29','12:10:00','13:35:00','Can_Tho','HCM_quan3','Court#1','E',0);
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) VALUES (9,'2020-03-29 09:27:18','2020-03-29','10:10:00','11:35:00','Hanoi','HCM_quan2','Court#1','E',0);
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) VALUES (10,'2020-03-29 09:27:18','2020-03-29','17:10:00','18:35:00','HCM','HCM_quan1','Court#1','F',0);
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) VALUES (11,'2020-03-29 09:27:18','2020-03-30','20:10:00','21:00:00','Can_Tho','HCM_quan3','Court#1','E',0);
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) VALUES (12,'2020-04-17 18:04:00','2020-04-15','09:00:00','10:00:00','CityA','CenterA','Court#1','Customer#A',0);
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) VALUES (14,'2020-04-17 18:04:00','2020-04-15','09:00:00','10:00:00','CityA','CenterA','Court#1','E',0);



-- createCity
DROP PROCEDURE IF EXISTS createCity;
DELIMITER //
CREATE PROCEDURE createCity(
in pcityId varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
START TRANSACTION;
IF NOT pcityId REGEXP '^[a-zA-Z0-9]*$'
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CITY-000';
ELSEIF EXISTS ( SELECT * 
			FROM city
            WHERE city_id = pcityId )
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CITY-001';
ELSE 
    INSERT INTO city VALUES (pcityId);
END IF;

END //
DELIMITER ;

-- createCityCenter(centerId, cityId)

DROP PROCEDURE IF EXISTS createCityCenter;
DELIMITER //
CREATE PROCEDURE createCityCenter(
in pcenterId varchar(50), in pcityId varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
  
START TRANSACTION;
IF NOT pcityId REGEXP '^[a-zA-Z0-9]*$'
THEN 
	 SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CEN-000';
ELSEIF NOT pcenterId REGEXP '^[a-zA-Z0-9]*$'
THEN 
	 SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CEN-001';
ELSEIF NOT EXISTS (SELECT * FROM city WHERE city_id = pcityId)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CEN-002";
ELSEIF
	EXISTS (SELECT * FROM center WHERE center_id = pcenterId)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CEN-003";
ELSE INSERT INTO center VALUES (pcenterId, pcityId);
END IF;
end//
DELIMITER ;

-- createCityCenterCourt(courtId, cityId, centerId)

DROP PROCEDURE IF EXISTS createCityCenterCourt;
DELIMITER //
CREATE PROCEDURE createCityCenterCourt(
in pcourtId varchar(50), in pcityId varchar(50), in pcenterId varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
  
START TRANSACTION;

IF NOT pcityId REGEXP '^[a-zA-Z0-9]*$'
THEN 
	 SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CRT-000';
ELSEIF NOT pcenterId REGEXP '^[a-zA-Z0-9]*$'
THEN 
	 SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CRT-001';
ELSEIF NOT pcourtId REGEXP '^[a-zA-Z0-9]*$'
THEN 
	 SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CRT-002';
ELSEIF NOT EXISTS ( SELECT * FROM city WHERE city_id = pcityId)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CRT-003";
ELSEIF NOT EXISTS (SELECT * FROM center WHERE center_id = pcenterId)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CRT-004";
ELSEIF
	EXISTS (SELECT * FROM court WHERE court_id = pcourtId)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CRT-005";
ELSE INSERT INTO court VALUES (pcourtId, pcityId, pcenterId);
END IF;
END //
DELIMITER ;

-- createPlayer(playerId)
DROP PROCEDURE IF EXISTS createPlayer;
DELIMITER //
CREATE PROCEDURE createPlayer(
in pplayerId varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
START TRANSACTION;

IF NOT pplayerId REGEXP '^[a-zA-Z0-9]*$'
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CPL-000';
ELSEIF EXISTS ( SELECT * 
			FROM player
            WHERE player_id = pplayerId )
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CPL-001';
ELSE 
    INSERT INTO player VALUES (pplayerId);
END IF;
END //
DELIMITER ;

/* Test cases for createPlayer */

-- createStaff(staffId, cityId, centerId)
DROP PROCEDURE IF EXISTS createStaff;
DELIMITER //
CREATE PROCEDURE createStaff(
in pstaffId varchar(50), in pcityId varchar(50), in pcenterId varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
  
START TRANSACTION;
IF NOT pcityId REGEXP '^[a-zA-Z0-9]*$'
THEN 
	 SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CS-000';
ELSEIF NOT pcenterId REGEXP '^[a-zA-Z0-9]*$'
THEN 
	 SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CS-001';
ELSEIF NOT pstaffId REGEXP '^[a-zA-Z0-9]*$'
THEN 
	 SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CS-002';
ELSEIF NOT EXISTS (SELECT * FROM city WHERE city_id = pcityId)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CS-003";
ELSEIF
	NOT EXISTS (SELECT * FROM center WHERE center_id = pcenterId)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CS-004";
ELSEIF
	EXISTS (SELECT * FROM staff WHERE staff_id = pstaffId)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CS-005";
ELSE INSERT INTO staff VALUES (pstaffId, pcityId, pcenterId);
END IF;
END //
DELIMITER ;
DELIMITER ;

-- createBooking(booking_id, timestamp, date, startTime, endTime, cityId, centerId, courtId, playerId)
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

/* Test if createCity is accepted when cityId is valid */
CALL createCity("A");
/* expected no error code */
 
/* Test if createCity is rejected when cityId is invalid */
CALL createCity("$A");
/* expected error CITY-000 */

/* Test if createCity is rejected when cityId is existed */
CALL createCity("A");
CALL createCity("A");
/* expected error CITY-001 */

/* Test if createCityCenter is accepted when centerId and cityId are valid */
CALL createCity("1");
CALL createCityCenter("A", "1");
/* expected no error code */

/* Tests if createCityCenter is rejected when cityId is invalid */
CALL createCityCenter("A", "#");
/* expected error CEN-000 */

/* Tests if createCityCenter is rejected when centerId is invalid */
CALL createCityCenter("#", "A");
/* expected error CEN-001 */

/* Tests if createCityCenter is rejected when cityId is not existed */
CALL createCityCenter("1", "A");
/* expected error CEN-002 */

/* Tests if createCityCenter is rejected when centerId is existed */
CALL createCity("1");
CALL createCityCenter("A", "1");
CALL createCityCenter("A", "1");
/* expected error CEN-003 */

/* Test if createCityCenterCourt is accepted when courtId, centerId and cityId are valid */
CALL createCity("1");
CALL createCityCenter("2", "1");
CALL createCityCenterCourt("A", "1", "2");
/* expected no error code */

/* Test if createCityCenterCourt is rejected when courtId is invalid */
CALL createCityCenterCourt("#", "1", "2");
/* expected error code CRT-002 */

/* Test if createCityCenterCourt is rejected when cityId is invalid */
CALL createCityCenterCourt("A", "#", "2");
/* expected error code CRT-000 */

/* Test if createCityCenterCourt is rejected when centerId is invalid */
CALL createCityCenterCourt("A", "1", "#");
/* expected error code CRT-001 */

/* Test if createCityCenterCourt is rejected when centerId is existed but cityId is not existed */
CALL createCity("CITY");
CALL createCityCenter("CENTER", "CITY");
CALL createCityCenterCourt("A", "CITY2", "CENTER");
/* expected error code CRT-003 */

/* Test if createCityCenterCourt is rejected when centerId is not existed but cityId is existed*/
CALL createCity("CITY");
CALL createCityCenter("CENTER", "CITY");
CALL createCityCenterCourt("A", "CITY", "CENTER2");
/* expected error code CRT-003 */

/* Test if createCityCenterCourt is rejected when both centerId and cityId are not existed */
CALL createCity("CITY");
CALL createCityCenter("CENTER", "CITY");
CALL createCityCenterCourt("A", "CITY2", "CENTER2");
/* expected error code CRT-003 */

/* Test if createCityCenterCourt is rejected when courtId is existed */
CALL createCity("1");
CALL createCityCenter("2", "1");
CALL createCityCenterCourt("A", "1", "2");
CALL createCityCenterCourt("A", "1", "2");
/* expected error code CRT-005 */

/* Test if createPlayer is accepted when cityId is valid */
CALL createPlayer("A");
/* expected no error code */
 
/* Test if createPlayer is rejected when cityId is invalid */
CALL createPlayer("$A");
/* expected error CPL-000 */

/* Test if createPlayer is rejected when cityId is existed */
CALL createPlayer("A");
CALL createPlayer("A");
/* expected error CPL-001 */

/* Test if createStaff is accepted when courtId, centerId and cityId are valid */
CALL createCity("1");
CALL createCityCenter("2", "1");
CALL createStaff("A", "1", "2");
/* expected no error code */

/* Test if createStaff is rejected when staffId is invalid */
CALL createStaff("#", "1", "2");
/* expected error code CRT-002 */

/* Test if createStaff is rejected when cityId is invalid */
CALL createStaff("A", "#", "2");
/* expected error code CRT-000 */

/* Test if createStaff is rejected when centerId is invalid */
CALL createStaff("A", "1", "#");
/* expected error code CRT-001 */

/* Test if createStaff is rejected when centerId is existed but cityId is not existed */
CALL createCity("CITY");
CALL createCityCenter("CENTER", "CITY");
CALL createStaff("A", "CITY2", "CENTER");
/* expected error code CRT-003 */

/* Test if createStaff is rejected when centerId is not existed but cityId is existed*/
CALL createCity("CITY");
CALL createCityCenter("CENTER", "CITY");
CALL createStaff("A", "CITY", "CENTER2");
/* expected error code CRT-003 */

/* Test if createStaff is rejected when both centerId and cityId are not existed */
CALL createCity("CITY");
CALL createCityCenter("CENTER", "CITY");
CALL createStaff("A", "CITY2", "CENTER2");
/* expected error code CRT-003 */

/* Test if createStaff is rejected when courtId is existed */
CALL createCity("1");
CALL createCityCenter("2", "1");
CALL createStaff("A", "1", "2");
CALL createStaff("A", "1", "2");
/* expected error code CRT-005 */

-- updateBookingStatus(status, bookingId, cityId, centerId, staffId)

/* Test if updateBookingStatus is rejected when bookingId is invalid */
CALL updateBookingStatus(1, "#BOOKING", "CITY", "CENTER", "STAFF");
/* expected error code UBS-000 */

/* Test if updateBookingStatus is rejected when cityId is invalid */
CALL updateBookingStatus(1, "BOOKING", "#CITY", "CENTER", "STAFF");
/* expected error code UBS-001 */

/* Test if updateBookingStatus is rejected when centerId is invalid */
CALL updateBookingStatus(1, "BOOKING", "CITY", "#CENTER", "STAFF");
/* expected error code UBS-002 */

/* Test if updateBookingStatus is rejected when staffId is invalid */
CALL updateBookingStatus(1, "BOOKING", "CITY", "CENTER", "#STAFF");
/* expected error code UBS-003 */

/* Test if updateBookingStatus is rejected when bookingId is not existed */
CALL updateBookingStatus(1, "BOOKING12", "CITY", "CENTER", "STAFF");
/* expected error code UBS-004 */

/* Test if updateBookingStatus is rejected when cityId is not existed */
CALL createCity("CityA");
CALL createCityCenter("CenterA", "CityA");
CALL createCityCenterCourt("Court1", "CityA", "CenterA");
CALL createStaff("Staff", "CityA", "CenterA");
CALL updateBookingStatus(1, 12, "CITY", "CENTER", "STAFF");
/* expected error code UBS-005 */

/* Test if updateBookingStatus is rejected when centerId is not existed */
CALL createCity("CityA");
CALL createCityCenter("CenterA", "CityA");
CALL createCityCenterCourt("Court1", "CityA", "CenterA");
CALL createStaff("Staff", "CityA", "CenterA");
CALL updateBookingStatus(1, 13, "CityA", "CENTER", "STAFF");
/* expected error code UBS-006 */

/* Test if updateBookingStatus is rejected when staffId does not manage in cityId, courtId*/
CALL createCity("CityA");
CALL createCityCenter("CenterA", "CityA");
CALL createCityCenterCourt("Court1", "CityA", "CenterA");
CALL createStaff("Staff", "CityA", "CenterA");
CALL createPlayer("Player");
CALL updateBookingStatus(1, 14, "CityA", "CenterA", "STAFF1");
/* expected error code UBS-007 */

/* Test if updateBookingStatus is rejected when bookingId does not belong to cityId, centerId */
CALL createCity("CityA");
CALL createCity("CityB");
CALL createCityCenter("CenterA", "CityA");
CALL createCityCenter("CenterB", "CityB");
CALL createCityCenterCourt("Court1", "CityA", "CenterA");
CALL createStaff("Staff", "CityA", "CenterA");
CALL createStaff("Staff2", "CityB", "CenterB");
CALL createPlayer("Player");
CALL updateBookingStatus(1, "Booking1", "CityB", "CenterB", "Staff2");
/* expected error code UBS-008 */

/* Test if updateBookingStatus is accepted when all above constraints are passed */
CALL createCity("CityA");
CALL createCityCenter("CenterA", "CityA");
CALL createCityCenterCourt("Court1", "CityA", "CenterA");
CALL createStaff("Staff", "CityA", "CenterA");
CALL createPlayer("Player");
CALL updateBookingStatus(1, "Booking1", "CityA", "CenterA", "Staff");
/* expected no error code */

/* Test cases for createBooking 
CB-000 | booking_id not alphanumeric
CB-001 | startTime < DATE(NOW())
CB-002 | startTime < openTime 
CB-003 | endTime > closeTime 
CB-004 | endTime < startTime   
CB-005 | playtime invalid (valid: 45m, 1h, 1h15m, 1h30m)  
CB-006 | overlapping booking  
CB-007 | Player has pending booking in past 
CB-008 | Player can book no more than 3 bookings 
CB-109 | Customer does not existed 
CB-110 | Court does not existed
CB-111 | Court & Customer do not existed
*/





/* CreateBooking(booking_id : alphanumeric, 
				timestamp : DATETIME, date, startTime, endTime, cityId, centerId, courtId, playerId
/* test 13 */
CALL CreateBooking(13, "2020-03-29 09:27:18", "2020-02-01", 10, 10, 00, 18, 35, 00, 'HCM', 'HCM_quan1', 'Court#1', 'Customer#A');
/* error: CB-001 */

/* test 14 */	
CALL CreateBooking(14, "2020-03-29 09:27:18", "2020-05-01", 6, 10, 00, 18, 35, 00, 'HCM', 'HCM_quan1', 'Court#1', 'Customer#A');
/* error: CB-002 */

/* test 15 */
CALL CreateBooking(15, "2020-03-29 09:27:18", "2020-04-15", 9, 00, 00, 23, 00, 00, 'HCM', 'HCM_quan1', 'Court#1', 'Customer#A');
/* error: CB-003 */

/* test 16 */
CALL CreateBooking(16, "2020-03-29 09:27:18", "2020-04-16", 9, 00, 00, 8, 00, 00, 'HCM', 'HCM_quan1', 'Court#1', 'Customer#A');
/* error: CB-004 */

/* test 17 */
CALL CreateBooking(17, "2020-03-29 09:27:18", "2020-04-17", 9, 00, 00, 9, 35, 00, 'HCM', 'HCM_quan1', 'Court#1', 'Customer#A');
/* error: CB-005 */

/* test 18 */
--       9:00===========10:00  existed booking
--    8:00========9:30
--           9:30==============10:30
--         9:15=========10:00
--     8:30=============10:00

CALL CreateBooking(18, "2020-03-29 09:27:18", "2020-04-11", 8, 0, 0, 9, 30, 0, 'HCM', 'HCM_quan2', 'Court#2', 'Customer#B');
CALL CreateBooking(18, "2020-03-29 09:27:18", "2020-04-11", 9, 30, 0, 10, 30, 0, 'HCM', 'HCM_quan2', 'Court#2', 'Customer#B');
CALL CreateBooking(18, "2020-03-29 09:27:18", "2020-04-11", 9, 15, 0, 10, 0, 0, 'HCM', 'HCM_quan2', 'Court#2', 'Customer#B');
CALL CreateBooking(18, "2020-03-29 09:27:18", "2020-04-11", 8, 30, 0, 10, 0, 0, 'HCM', 'HCM_quan2', 'Court#2', 'Customer#B');
/* error: CB-006 */

/* test 19 */
CALL CreateBooking(19,"2020-03-29 09:27:18",'2020-04-18',9,00,00,10,00,00,'HCM','HCM_quan1','Court#2','Customer#B');
/* error: CB-007 */
    
 /* test 20 */   
CALL CreateBooking(20,"2020-03-29 09:27:18",'2020-04-19',9,00,00,10,00,00,'HCM','HCM_quan5','Court#5','Customer#E');
/* error: CB-008 */

/* test 21 */
CALL CreateBooking(21,"2020-03-29 09:27:18",'2020-04-20',9,00,00,10,00,00,'HCM','HCM_quan5','Court#5','Customer#X');
/* error: CB-109 */

/* test 22 */
CALL CreateBooking(22,"2020-03-29 09:27:18",'2020-04-19',9,00,00,10,00,00,'HCM','HCM_quan5','Court#X','Customer#A');
/* error: CB-110 */

/* test 23 */
CALL CreateBooking(23,"2020-03-29 09:27:18",'2020-04-19',9,00,00,10,00,00,'HCM','HCM_quan5','Court#X','Customer#X');
/* error: CB-111 */




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

-- updateBookingStatus(status, bookingId, cityId, centerId, staffId)







