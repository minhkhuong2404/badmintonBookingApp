DROP PROCEDURE IF EXISTS getCenterStaffs;
DELIMITER //
CREATE PROCEDURE getCenterStaffs(
  in centerId varchar(50)
  out resultCode varchar(50))
BEGIN

IF NOT centerId REGEXP '^[a-zA-Z0-9]*$'
THEN SET resultCode = 'GCS-000';
ELSEIF NOT EXISTS (SELECT * FROM center WHERE centerId = center_id)
THEN SET resultCode ="GCS-001";
ELSE
	select * from staff where centerId = center_id;
  SET resultCode = '200';
END IF;
end//
DELIMITER ;



/* Test if getCenterStaffs is rejected when centerId is invalid */
CALL getCenterStaffs("#");
/* expected error code CEN-000 */


/* Test if getCenterStaffs is rejected when centerId is not existed */
CALL getCenterStaffs("B");
/* expected error code CEN-001 */

/* Test if getCenterStaffs is valid and existed */
call getCenterStaffs('HCMquan1');


