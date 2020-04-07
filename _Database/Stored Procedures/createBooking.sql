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