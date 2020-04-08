CREATE DATABASE booking_app;
USE booking_app;

DROP TABLE IF EXISTS city;
CREATE TABLE city(
	city_ID varchar(50) NOT NULL PRIMARY KEY,
    PRIMARY KEY(city_ID)
);

DROP TABLE IF EXISTS center;
CREATE TABLE center(
	center_ID varchar(50) NOT NULL PRIMARY KEY,
    PRIMARY KEY(center_ID)
);

DROP TABLE IF EXISTS court;
CREATE TABLE court(
  court_ID varchar(50) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  PRIMARY KEY(court_ID)
);

DROP TABLE IF EXISTS player;
CREATE TABLE player(
	player_ID varchar(50) NOT NULL PRIMARY KEY
);

DROP TABLE IF EXISTS staff;
CREATE TABLE staff(
	staff_ID varchar(50) NOT NULL PRIMARY KEY
);

DROP TABLE IF EXISTS booking;
CREATE TABLE booking(
  booking_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  timestamp date NOT NULL,
  date date NOT NULL,
  startHour int NOT NULL,
  endHour int NOT NULL,
  startMin int NOT NULL,
  endMin int NOT NULL,
  court int,
  player int,
  booking_status varchar(50),
  timestamp date NOT NULL,
  CONSTRAINT court_fk
     FOREIGN KEY (court) REFERENCES court (court_id),
  CONSTRAINT customer_fk
     FOREIGN KEY (customer) REFERENCES customer (customer_id)	 
);


DROP PROCEDURE IF EXISTS CreateBooking;
DELIMITER //
CREATE PROCEDURE CreateBooking(
in booking_id varchar(50),
in timestamp date,
in pdate date, 
in pstartHour int, in pstartMin int, 
in pendHour int, in pendMin int,
in city_ID varchar(50),
in center_ID varchar(50),
in court_ID varchar(50),
in player_ID timestamp)
BEGIN
DECLARE TotalBookings int;
DECLARE openTime datetime;
DECLARE closeTime datetime;
DECLARE startTime datetime;
DECLARE endTime datetime;
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;

SELECT 
  date_add(date_add(pdate, INTERVAL 7 HOUR), INTERVAL 0 MINUTE)
  into openTime;
SELECT 
  date_add(date_add(pdate, INTERVAL 21 HOUR), INTERVAL 0 MINUTE)
  into closeTime;  
SELECT 
  date_add(date_add(pdate, INTERVAL pstartHour HOUR), INTERVAL pstartMin MINUTE)
  into startTime;
SELECT 
  date_add(date_add(pdate, INTERVAL pendHour HOUR), INTERVAL pendMin MINUTE)
  into endTime;
SELECT 
  count(*) 
FROM booking_app.booking as B 
	JOIN booking_app.player as C ON B.player = C.player_ID
WHERE C.name = procedureplayer and B.booking_status like upper("UNPAID")
  into TotalBookings;  
START TRANSACTION;
IF startTime < DATE(NOW())
THEN 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT = 'Please do not booking in the past';
END IF;
IF  startTime < openTime 
THEN 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT = 'Our center remaining closing';
END IF;
IF  endTime > closeTime 
THEN 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT = 'Our Center closed!';
END IF;

IF endTime < startTime 
THEN 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT = 'Please fix your end time';
END IF;

IF timestampdiff(MINUTE,startTime,endTime) < 45
THEN 
	SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'Your court have to be more than 45 minutes';
END IF;

IF TotalBookings >= 3
THEN
	SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'You had 3 unpaid booking';
ELSEIF TotalBookings = 1 and EXISTS (
									SELECT * 
                                    FROM booking_app.booking as B 
										JOIN booking_app.player as C ON B.pleyer = C.player_ID
                                    WHERE C.name = procedureplayer 
                                    AND date_add(date_add(B.date, INTERVAL B.startHour HOUR), INTERVAL B.startMin MINUTE) < date(now()))
THEN 
	SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'You had 1 unpaid booking';
END IF;
END //
DELIMITER ;
use booking_app;
DROP PROCEDURE IF EXISTS cancelbooking;
DELIMITER //

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



DROP PROCEDURE IF EXISTS createCityCenter;
DELIMITER //
CREATE PROCEDURE createCityCenter(
in pcenter varchar(50), in pcity varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
  
START TRANSACTION;
IF pcity IN ( select city_ID from city) AND pcenter IN ( select center_ID from center)
THEN 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT ="Center existed";
END IF;

END //
DELIMITER ;

DROP PROCEDURE IF EXISTS createCityCenterCourt;
DELIMITER //
CREATE PROCEDURE createCityCenterCourt(
in pcenter varchar(50), in pcity varchar(50), in pcourt varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
  
START TRANSACTION;
IF pcity IN ( select city_ID from city) AND pcenter IN ( select center_ID from center) AND pcourt IN (select court_ID from court)
THEN 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT ="Center existed";
END IF;

END //
DELIMITER ;

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
IF pplayer IN ( select player_ID from player)
THEN 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT ="Same player";
END IF;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS createStaff;
DELIMITER //
CREATE PROCEDURE createStaff(
in pstaff varchar(50), in pcity varchar(50), in pcenter varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
  
START TRANSACTION;
IF pcity IN ( SELECT city_ID FROM city) and pcenter IN (Select center_ID FROM center) and pstaff in ( select staff_ID from staff)
THEN
	SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = "WE DONT HAVE THIS STAFF";
END IF;
END //
DELIMITER ;
DROP PROCEDURE IF EXISTS cancelbooking;
DELIMITER //
CREATE PROCEDURE cancelbooking(
in booking_id varchar(50),
in player_ID varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
  
START TRANSACTION;
IF player_ID IN ( select player_ID from player, booking where p.player_ID != b.booking_id)
THEN 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT ="Your information hasnt saved in system";
END IF;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS update BookingStatus,
DELIMITER //
CREATE PROCEDURE BookingStatus(
in status int,
in booking_id varchar(50),
in city_ID varchar(50),
in center_ID varchar(50),
in staff_ID varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
  
START TRANSACTION;
IF player_ID IN ( select player_ID from player, booking where p.player_ID != b.booking_id)
THEN 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT ="Your information hasnt saved in system";
END IF;
END //
DELIMITER ;




DELIMITER ;




/* scenario */
INSERT INTO court (name) values ("Court#1");
INSERT INTO court (name) values ("Court#2");
INSERT INTO court (name) values ("Court#3");


INSERT INTO customer (name) values ("Customer#A");
INSERT INTO customer (name) values ("Customer#B");
INSERT INTO customer (name) values ("Customer#C");

/* tests */

call CreateBooking("2020-03-29", 7, 0, 8, 0, "Court#1", "Customer#A", "2020-03-29 09:29:18");
	/* error: Please do not booking in the past*/ 
call CreateBooking("2020-04-01", 6, 0, 8, 0, "Court#1", "Customer#A", "2020-03-29 09:27:18");
   /* expected: Our center remaining closing */    
call CreateBooking("2020-04-01", 7, 0, 21, 15, "Court#1", "Customer#A", "2020-03-29 09:27:18");
   /* expected:  Our Center closed*/
call CreateBooking("2020-04-01", 8, 0, 7, 0, "Court#1", "Customer#A", "2020-03-29 09:27:18");
   /* expected:  Please fix your end time*/
call CreateBooking("2020-04-01", 20, 0, 21, 0, "Court#1", "Customer#A","2020-03-29 09:27:18"); 
    /*expected:  You had 3 unpaid booking */
Call cancelbooking(1,7);
/* expected: You can not cancel before 24 hours  */
/* test 1 */

CALL createCity("Hanoi");

CALL createCity("Hanoi");

/* error: CC001 */