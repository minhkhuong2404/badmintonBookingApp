DROP PROCEDURE IF EXISTS getCityCenterStaffs;
DELIMITER //
CREATE PROCEDURE getCityCenterStaffs(
  in cityId varchar(50),
  in centerId varchar(50),
  out resultCode varchar(50))
BEGIN

IF NOT EXISTS (SELECT * FROM city WHERE cityId = city_id)
THEN SET resultCode ="GCS-000";
ELSEIF NOT EXISTS (SELECT * FROM center WHERE centerId = center_id)
THEN SET resultCode ="GCS-001";
ELSE
	select * from staff where centerId = center_id;
  SET resultCode = '200';
END IF;
end//
DELIMITER ;



/* Test if getCenterStaffs is rejected when centerId is invalid */
CALL getCityCenterStaffs("A", "#", @code);
/* expected error code CEN-000 */


/* Test if getCenterStaffs is rejected when centerId is not existed */
CALL getCityCenterStaffs("A", "B", @code);
/* expected error code CEN-001 */

/* Test if getCenterStaffs is valid and existed */
call getCityCenterStaffs("A", 'HCMquan1', @code);


