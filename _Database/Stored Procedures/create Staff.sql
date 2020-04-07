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
IF NOT EXISTS ( SELECT * FROM city WHERE city_id = pcity)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CS002";
ELSEIF NOT EXISTS (SELECT * FROM center WHERE center_id = pcenter)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CS-003";
ELSEIF
	EXISTS (SELECT * FROM staff WHERE staff_id = pstaff)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CS-001";
    -- CS-001: Staff existed
ELSE INSERT INTO staff VALUES (pstaff, pcenter, pcity);
END IF;
END //
DELIMITER ;


/* Test cases for createStaff */