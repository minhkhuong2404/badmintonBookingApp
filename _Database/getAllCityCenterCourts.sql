DROP PROCEDURE IF EXISTS getAllCityCenterCourts;
DELIMITER //
CREATE PROCEDURE getAllCityCenterCourts(in cityId varchar(50), in centerId varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
  
START TRANSACTION;
IF NOT cityId REGEXP '^[a-zA-Z0-9]*$'
THEN 
	 SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CEN-000';
ELSEIF NOT centerId REGEXP '^[a-zA-Z0-9]*$'
THEN 
	 SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CEN-001';
ELSEIF NOT EXISTS (SELECT * FROM city WHERE city_id = cityId)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CEN-002";
ELSEIF NOT EXISTS (SELECT * FROM center WHERE center_id = centerId)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CEN-003";
ELSE 
	select * from court where cityId = city_id and centerId = center_id;
END IF;
end//
DELIMITER ;

/* Test if getAllCityCenterCourts is accepted when centerId and cityId are valid */
delete from center where center_id = "A" and city_id = "1";
CALL getAllCityCenterCourts("A", "1");
/* expected no error code */

/* Tests if getAllCityCenterCourts is rejected when cityId is invalid */
CALL getAllCityCenterCourts("A", "#");
/* expected error CEN-000 */

/* Tests if getAllCityCenterCourts is rejected when centerId is invalid */
CALL getAllCityCenterCourts("#", "A");
/* expected error CEN-001 */

/* Tests if getAllCityCenterCourts is rejected when cityId is not existed */
CALL getAllCityCenterCourts("A", "A");
/* expected error CEN-002 */

/* Tests if getAllCityCenterCourts is rejected when centerId is not existed */
CALL getAllCityCenterCourts("HCM", "2");
/* expected error CEN-002 */

/* Tests if getAllCityCenterCourts is valid and existed*/
CALL getAllCityCenterCourts("HCM", "HCMquan1");



