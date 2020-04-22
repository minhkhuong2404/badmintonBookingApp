DROP PROCEDURE IF EXISTS getCenterStaffs;
DELIMITER //
CREATE PROCEDURE getCenterStaffs(
  in centerId varchar(50),
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
SELECT resultCode;
end//
DELIMITER ;

/* Test if getCenterStaffs is valid and existed */
delete from staff where staff_id = "S";
CALL createStaff("S", "HCM", "A", @code);
call getCenterStaffs('A', @code);

/* Test if getCenterStaffs is rejected when centerId is invalid */
CALL getCenterStaffs("#", @code);
/* expected error code GCS-000 */


/* Test if getCenterStaffs is rejected when centerId is not existed */
CALL getCenterStaffs("B", @code);
/* expected error code GCS-001 */




