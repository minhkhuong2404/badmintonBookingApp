DROP PROCEDURE IF EXISTS getCenterBookings;
DELIMITER //
CREATE PROCEDURE getCenterBookings(
  in centerId varchar(50),
  out resultCode varchar(50))
BEGIN

IF NOT centerId REGEXP '^[a-zA-Z0-9]*$'
THEN SET resultCode = 'GCB-000';
ELSEIF NOT EXISTS (SELECT * FROM booking WHERE center_id = centerId)
THEN SET resultCode ="GCB-001";
ELSE
	select * from booking where centerId = center_id;
  SET resultCode = '200';
END IF;
SELECT resultCode;
end//
DELIMITER ;
