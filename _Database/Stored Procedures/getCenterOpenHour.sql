DROP PROCEDURE IF EXISTS getCenterOpenHour;
DELIMITER //
CREATE PROCEDURE getCenterOpenHour(
  in centerId varchar(50),
  out resultCode varchar(50))
BEGIN

IF NOT EXISTS (SELECT * FROM center WHERE center_id = centerId)
THEN SET resultCode ="GCH-000";
ELSE
SELECT open_hour, close_hour, date_of_week
FROM openhour
WHERE center = centerId;
SET resultCode = '200';
END IF;
end//
DELIMITER ;

-- call getCityBookings("A", date("2020-05-10"), @code);

call getCenterOpenHour("A1", @code);