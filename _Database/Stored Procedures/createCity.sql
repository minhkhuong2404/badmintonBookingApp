-- createCity
DROP PROCEDURE IF EXISTS createCity;
DELIMITER //
CREATE PROCEDURE createCity(
in pcityId varchar(50),
OUT resultCode varchar(50))
BEGIN

IF NOT pcityId REGEXP '^[a-zA-Z0-9]*$'
THEN
     SET resultCode = 'CITY-000';
ELSEIF EXISTS ( SELECT * 
			FROM city
            WHERE city_id = pcityId )
THEN
     SET resultCode = 'CITY-001';
ELSE 
    INSERT INTO city VALUES (pcityId);
    SET resultCode = '200';
END IF;

SELECT resultCode;

END //
DELIMITER ;

