DROP PROCEDURE IF EXISTS getPlayerBookings;
DELIMITER //
CREATE PROCEDURE getPlayerBookings(in playerId varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
  
START TRANSACTION;
IF NOT playerId REGEXP '^[a-zA-Z0-9]*$'
THEN 
	 SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CEN-000';
ELSEIF NOT EXISTS (SELECT * FROM booking WHERE player_id = playerId)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CEN-001";
ELSE
	select * from booking where playerId = player_id;
END IF;
end//
DELIMITER ;

/* Test if getPlayerBookings is rejected when centerId is invalid */
call getPlayerBookings('#');
/* expected error code CEN-000 */

/* Test if getPlayerBookings is rejected when centerId is not existed */
call getPlayerBookings('A');
/* expected error code CEN-001 */

/* Test if getPlayerBookings is valid and existed, then return the information */
call getPlayerBookings('CustomerA');

