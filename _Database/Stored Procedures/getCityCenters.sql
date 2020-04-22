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
end//
DELIMITER ;


/* Tests if getCityCenters is rejected when cityId is invalid */
CALL getCityCenters("#");
/* expected error CEN-000 */

/* Tests if getCityCenters is rejected when cityId is not existed */
delete from center where center_id = "A";
CALL getCityCenters("A");
/* expected error CEN-001 */

/* Tests if getCityCenters is valid then return the table center*/
call getCityCenters("HCM");
/* expected: the Center table*/
