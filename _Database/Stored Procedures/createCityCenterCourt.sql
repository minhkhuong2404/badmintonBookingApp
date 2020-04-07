-- createCityCenterCourt(courtId, cityId, centerId)

DROP PROCEDURE IF EXISTS createCityCenterCourt;
DELIMITER //
CREATE PROCEDURE createCityCenterCourt(
in pcity varchar(50), in pcenter varchar(50), in pcourt varchar(50))
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
	SET MESSAGE_TEXT ="CCourt-002";
ELSEIF NOT EXISTS (SELECT * FROM center WHERE center_id = pcenter)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CCourt-003";
ELSEIF
	EXISTS (SELECT * FROM court WHERE court_id = pcourt)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CCourt-001";
    -- CCourt-001: Court existed
ELSE INSERT INTO court VALUES (pcourt, pcenter, pcity);
END IF;

-- end if;
end//
DELIMITER ;


/* Test cases for createCityCourt */