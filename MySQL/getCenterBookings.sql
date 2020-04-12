DROP PROCEDURE IF EXISTS getCenterBookings;
DELIMITER //
CREATE PROCEDURE getCenterBookings(in centerId varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
  
START TRANSACTION;
IF NOT centerId REGEXP '^[a-zA-Z0-9]*$'
THEN 
	 SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CEN-000';
ELSEIF NOT EXISTS (SELECT * FROM booking WHERE center_id = centerId)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CEN-001";
ELSE
	select * from booking where centerId = center_id;
END IF;
end//
DELIMITER ;

/* Test if getCenterBookings is rejected when centerId is invalid */
call getCenterBookings('#');
/* expected error code CEN-000 */

/* Test if getCenterBookings is rejected when centerId is not existed */
call getCenterBookings('A');
/* expected error code CEN-001 */
/* Test if getCenterBookings is valid and existed, then return the information */
call getCenterBookings('HCMquan1');