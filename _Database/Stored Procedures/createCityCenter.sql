-- createCityCenter(centerId, cityId)

DROP PROCEDURE IF EXISTS createCityCenter;
DELIMITER //
CREATE PROCEDURE createCityCenter(
in pcenterId varchar(50), in pcityId varchar(50),
OUT resultCode varchar(50))
BEGIN

IF NOT pcityId REGEXP '^[a-zA-Z0-9]*$'
THEN
    SET resultCode = 'CEN-000';
ELSEIF NOT pcenterId REGEXP '^[a-zA-Z0-9]*$'
THEN 
    SET resultCode = 'CEN-001';
ELSEIF NOT EXISTS (SELECT * FROM city WHERE city_id = pcityId)
THEN
	SET resultCode ="CEN-002";
ELSEIF
	EXISTS (SELECT * FROM center WHERE center_id = pcenterId)
THEN
	SET resultCode ="CEN-003";
ELSE 
	INSERT INTO center VALUES (pcenterId, pcityId);
	SET resultCode = '200';
END IF;

SELECT resultCode;

end//
DELIMITER ;
