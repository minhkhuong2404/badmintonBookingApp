DROP PROCEDURE IF EXISTS getCityCenterCourts;
DELIMITER //
CREATE PROCEDURE getCityCenterCourts(
	in cityId varchar(50), 
	in centerId varchar(50),
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
	SET resultCode = '200';
END IF;
end//
DELIMITER ;

/* Test if getCityCenterCourts is accepted when centerId and cityId are valid */
delete from center where center_id = "A" and city_id = "1";
CALL getCityCenterCourts("A", "1", @code);
/* expected no error code */

/* Tests if getCityCenterCourts is rejected when cityId is invalid */
CALL getCityCenterCourts("A", "#", @code);
/* expected error CEN-000 */

/* Tests if getCityCenterCourts is rejected when centerId is invalid */
CALL getCityCenterCourts("#", "A", @code);
/* expected error CEN-001 */

/* Tests if getCityCenterCourts is rejected when cityId is not existed */
CALL getCityCenterCourts("A", "A", @code);
/* expected error CEN-002 */

/* Tests if getCityCenterCourts is rejected when centerId is not existed */
CALL getCityCenterCourts("HCM", "2", @code);
/* expected error CEN-002 */

/* Tests if getCityCenterCourts is valid and existed*/
CALL getCityCenterCourts("HCM", "HCMquan1", @code);



