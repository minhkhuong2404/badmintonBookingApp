DROP PROCEDURE IF EXISTS getCityCenterCourts;
DELIMITER //
CREATE PROCEDURE getCityCenterCourts(
	in centerId varchar(50), 
	in cityId varchar(50),
	out resultCode varchar(50))
BEGIN

IF NOT cityId REGEXP '^[a-zA-Z0-9]*$'
THEN 
	SET resultCode = 'GCCC-000';
ELSEIF NOT centerId REGEXP '^[a-zA-Z0-9]*$'
THEN 
	SET resultCode = 'GCCC-001';
ELSEIF NOT EXISTS (SELECT * FROM city WHERE city_id = cityId)
THEN 
	SET resultCode ="GCCC-002";
ELSEIF NOT EXISTS (SELECT * FROM center WHERE center_id = centerId)
THEN
	SET resultCode ="GCCC-003";
ELSE 
	select * from court where cityId = city_id and centerId = center_id;
	SET resultCode = "200";
END IF;
SELECT resultCode;
end//
DELIMITER ;
