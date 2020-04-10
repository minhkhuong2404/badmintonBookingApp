-- createCityCenterCourt(courtId, cityId, centerId)

DROP PROCEDURE IF EXISTS createCityCenterCourt;
DELIMITER //
CREATE PROCEDURE createCityCenterCourt(
in pcourtId varchar(50), in pcityId varchar(50), in pcenterId varchar(50),
OUT resultCode varchar(50))
BEGIN

IF NOT pcityId REGEXP '^[a-zA-Z0-9]*$'
THEN 
	SET resultCode = 'CRT-000';
ELSEIF NOT pcenterId REGEXP '^[a-zA-Z0-9]*$'
THEN
    SET resultCode = 'CRT-001';
ELSEIF NOT pcourtId REGEXP '^[a-zA-Z0-9]*$'
THEN
    SET resultCode = 'CRT-002';
ELSEIF NOT EXISTS ( SELECT * FROM city WHERE city_id = pcityId)
THEN
	SET resultCode ="CRT-003";
ELSEIF NOT EXISTS (SELECT * FROM center WHERE center_id = pcenterId)
THEN
	SET resultCode ="CRT-004";
ELSEIF
	EXISTS (SELECT * FROM court WHERE court_id = pcourtId)
THEN
	SET resultCode ="CRT-005";
ELSE 
	INSERT INTO court VALUES (pcourtId, pcityId, pcenterId);
	SET resultCode = '200';
END IF;

SELECT resultCode;

END //
DELIMITER ;
