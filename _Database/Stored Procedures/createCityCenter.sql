-- createCityCenter(centerId, cityId)

DROP PROCEDURE IF EXISTS createCityCenter;
DELIMITER //
CREATE PROCEDURE createCityCenter(
in pcity varchar(50), in pcenter varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
  
START TRANSACTION;
IF NOT EXISTS (SELECT * FROM city WHERE city_id = pcenter)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CCtr-002";
    -- CCtr-002: City not existed
ELSEIF
	EXISTS (SELECT * FROM center WHERE center_id = pcenter)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CCtr-001";
    -- CCtr-001: Center existed
ELSE INSERT INTO court VALUES (pcourt, pcenter, pcity);
END IF;
end//
DELIMITER ;


/* Test cases for createCityCenter */