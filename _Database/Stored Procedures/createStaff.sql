-- createStaff(staffId, cityId, centerId)
DROP PROCEDURE IF EXISTS createStaff;
DELIMITER //
CREATE PROCEDURE createStaff(
in pstaffId varchar(50), in pcityId varchar(50), in pcenterId varchar(50),
OUT resultCode varchar(50))
BEGIN

IF NOT pcityId REGEXP '^[a-zA-Z0-9]*$'
THEN
    SET resultCode = 'CS-000';
ELSEIF NOT pcenterId REGEXP '^[a-zA-Z0-9]*$'
THEN
    SET resultCode = 'CS-001';
ELSEIF NOT pstaffId REGEXP '^[a-zA-Z0-9]*$'
THEN 
	SET resultCode = 'CS-002';
ELSEIF NOT EXISTS (SELECT * FROM city WHERE city_id = pcityId)
THEN
	SET resultCode ="CS-003";
ELSEIF
	NOT EXISTS (SELECT * FROM center WHERE center_id = pcenterId)
THEN
	SET resultCode ="CS-004";
ELSEIF
	EXISTS (SELECT * FROM staff WHERE staff_id = pstaffId)
THEN
	SET resultCode ="CS-005";
ELSE 
	INSERT INTO staff VALUES (pstaffId, pcityId, pcenterId);
	SET resultCode = '200';
END IF;

SELECT resultCode;

END //
DELIMITER ;