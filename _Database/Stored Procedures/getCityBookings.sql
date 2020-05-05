--getCityBookings(cityId, pdate)-- 
DROP PROCEDURE IF EXISTS getCityBookings;
DELIMITER //
CREATE PROCEDURE getCityBookings(
  in cityId varchar(50),
  in pdate date,
  out resultCode varchar(50))
BEGIN

IF NOT cityId REGEXP '^[a-zA-Z0-9]*$'
THEN SET resultCode = 'GCB-000';
ELSEIF NOT EXISTS (SELECT * FROM booking WHERE city_id = cityId)
THEN SET resultCode = 'GCB-001';
ELSE
	select * 
    from booking 
    where city_id = cityId and pdate = date --check city id and the date --
    order by center_id, court_id, startTime;
  SET resultCode = '200'; 
END IF;
end//
DELIMITER ;

#  call getCityBookings('A', date('2020-05-10'), @code);
