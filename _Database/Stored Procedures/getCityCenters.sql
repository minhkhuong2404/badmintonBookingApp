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
  SET resultCode = '200';
END IF;

SELECT resultCode;
end//
DELIMITER ;

/* Tests if getCityCenters is valid then return the table center*/
delete from city where city_id = "HCM";
delete from center where center_id = "A" and city_id = "HCM";
CALL createCity("HCM", @code);
CALL createCityCenter("A", "HCM", @code);
CALL getCityCenters("HCM",@code);
/* expected: the Center table*/

/* Tests if getCityCenters is rejected when cityId is invalid */
CALL getCityCenters("#",@code);
/* expected error GCC-000 */

/* Tests if getCityCenters is rejected when cityId is not existed */
delete from center where center_id = "A";
CALL getCityCenters("A",@code);
/* expected error GCC-001 */


