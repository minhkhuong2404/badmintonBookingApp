DROP PROCEDURE IF EXISTS getCenterHoliday;
DELIMITER //
CREATE PROCEDURE getCenterHoliday(
  in centerId varchar(50),
  out resultCode varchar(50))
BEGIN

IF NOT EXISTS (SELECT * FROM center WHERE center_id = centerId)
THEN SET resultCode ="GCH-000";
ELSE
SELECT h.holiday_day, h.holiday_month
FROM holiday h, center c
WHERE h.city_id = c.city_id and c.center_id = centerId;
SET resultCode = '200';
END IF;
end//
DELIMITER ;

-- call getCityBookings("A", date("2020-05-10"), @code);

call getCenterHoliday("A1", @code);