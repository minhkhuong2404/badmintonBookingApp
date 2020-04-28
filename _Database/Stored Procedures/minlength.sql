DROP PROCEDURE IF EXISTS getCenterMinLength;
DELIMITER //
CREATE PROCEDURE getCenterMinLength(
  in centerId varchar(50),
  out resultCode varchar(50))
BEGIN

IF NOT EXISTS (SELECT * FROM center WHERE center_id = centerId)
THEN SET resultCode ="GCM-000";
ELSE
SELECT min_length
FROM center
WHERE center_id = centerId;
SET resultCode = '200';
END IF;
end//
DELIMITER ;


call getCenterMinLength("A1", @out);