DROP PROCEDURE IF EXISTS getCityCenters;
DELIMITER //
CREATE PROCEDURE getCityCenters(
IN cityId varchar(50),
OUT resultCode varchar(50))
BEGIN
  
IF NOT cityId REGEXP '^[a-zA-Z0-9]*$'
THEN SET resultCode = 'GCC-000';
ELSEIF NOT EXISTS (SELECT * FROM center WHERE city_id = cityId)
THEN SET resultCode ="GCC-001";
ELSE
	select * from center where cityId = city_id;
  SET resultCode = "200";
END IF;
end//
DELIMITER ;

-- call getCityCenters("C", @code);