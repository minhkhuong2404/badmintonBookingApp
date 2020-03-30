CREATE DATABASE booking_app;
USE booking_app;

DROP TABLE IF EXISTS customer;
CREATE TABLE customer(
  customer_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(50) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS court;
CREATE TABLE court(
  court_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(50) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS center;
CREATE TABLE center (
   centerId int NOT NULL PRIMARY KEY,
   center_name varchar(50) NOT NULL UNIQUE
);

CREATE TABLE city(
	city_ID int NOT NULL PRIMARY KEY,
    city_name varchar(50) NOT NULL UNIQUE
);



DROP TABLE IF EXISTS booking;
CREATE TABLE booking(
  booking_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  date date NOT NULL,
  startHour int NOT NULL,
  endHour int NOT NULL,
  startMin int NOT NULL,
  endMin int NOT NULL,
  court int,
  customer int,
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
in pdate date, 
in pstartHour int, in pstartMin int, 
in pendHour int, in pendMin int, 
in pcourt varchar(50), in pcustomer varchar(50), in ptimestamp timestamp)
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
	JOIN booking_app.customer as C ON B.customer = C.customer_id
WHERE C.name = pcustomer and B.booking_status like upper("UNPAID")
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
										JOIN booking_app.customer as C ON B.customer = C.customer_id
                                    WHERE C.name = pcustomer 
                                    AND date_add(date_add(B.date, INTERVAL B.startHour HOUR), INTERVAL B.startMin MINUTE) < date(now()))
THEN 
	SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'You had 1 unpaid booking';
END IF;
END //
DELIMITER ;
use booking_app;
DROP PROCEDURE IF EXISTS cancel;
DELIMITER //
CREATE PROCEDURE cancel(
in pcustomer int, in pbooking int)
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
START TRANSACTION;

IF date_add(date(now()),interval 24 hour) >
(select date_add(date_add(date, INTERVAL startHour HOUR), INTERVAL startMin MINUTE) 
from booking_app.booking 
where booking_id= pbooking)
THEN SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT = 'You can not cancel before 24 hours ';
END IF;
END //
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
Call cancel(2,6);
/* expected: You can not cancel before 24 hours  */