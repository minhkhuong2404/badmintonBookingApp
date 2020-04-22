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
	SET resultCode = '200';
END IF;
SELECT resultCode;
end//
DELIMITER ;

/* Test if getCityCenterCourts is accepted when centerId and cityId are valid */
delete from city where city_id = "HCM";
delete from center where center_id = "A" and city_id = "HCM";
delete from court where court_id = "1" and center_id = "A" and city_id = "HCM";
CALL createCity("HCM",@code);
CALL createCityCenter("A","HCM",@code);
CALL createCityCenterCourt("1", "HCM", "A", @code);
CALL getCityCenterCourts("A", "HCM", @code);
/* expected no error code */

/* Tests if getCityCenterCourts is rejected when cityId is invalid */
CALL getCityCenterCourts("A", "#", @code);
/* expected error GCCC-000 */

/* Tests if getCityCenterCourts is rejected when centerId is invalid */
CALL getCityCenterCourts("#", "A", @code);
/* expected error GCCC-001 */

/* Tests if getCityCenterCourts is rejected when cityId is not existed */
CALL getCityCenterCourts("A", "A", @code);
/* expected error GCCC-002 */

/* Tests if getCityCenterCourts is rejected when centerId is not existed */
CALL getCityCenterCourts("HCM", "HCM", @code);
/* expected error GCCC-003 */




