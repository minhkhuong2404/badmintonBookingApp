DROP PROCEDURE IF EXISTS getstaff;
DELIMITER //
CREATE PROCEDURE getstaff(in centerId varchar(50))
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
ELSEIF NOT EXISTS (SELECT * FROM center WHERE centerId = center_id)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CEN-001";
ELSE
	select * from staff where centerId = center_id;
END IF;
end//
DELIMITER ;



/* Test if getstaff is rejected when centerId is invalid */
CALL getstaff("#");
/* expected error code CEN-000 */


/* Test if getstaff is rejected when centerId is not existed */
CALL getstaff("B");
/* expected error code CEN-001 */

/* Test if getstaff is valid and existed */
call getstaff('HCMquan1');


