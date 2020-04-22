DROP PROCEDURE IF EXISTS getCenterBookings;
DELIMITER //
CREATE PROCEDURE getCenterBookings(
  in centerId varchar(50),
  out resultCode varchar(50))
BEGIN

IF NOT centerId REGEXP '^[a-zA-Z0-9]*$'
THEN SET resultCode = 'GCB-000';
ELSEIF NOT EXISTS (SELECT * FROM booking WHERE center_id = centerId)
THEN SET resultCode ="GCB-001";
ELSE
	select * from booking where centerId = center_id;
  SET resultCode = '200';
END IF;
end//
DELIMITER ;

/* Test if getCenterBookings is rejected when centerId is invalid */
call getCenterBookings('#', @code);
/* expected error code CEN-000 */

/* Test if getCenterBookings is rejected when centerId is not existed */
call getCenterBookings('A', @code);
/* expected error code CEN-001 */
/* Test if getCenterBookings is valid and existed, then return the information */
call getCenterBookings('HCMquan1', @code);