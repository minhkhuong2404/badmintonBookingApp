DROP PROCEDURE IF EXISTS getCityBookings;
DELIMITER //
CREATE PROCEDURE getCityBookings(
  in cityId varchar(50),
  in pdate date,
  out resultCode varchar(50))
BEGIN

IF NOT cityId REGEXP '^[a-zA-Z0-9]*$'
THEN SET resultCode = 'GCB-000';
ELSEIF NOT EXISTS (SELECT * FROM city WHERE city_id = cityId)
THEN SET resultCode ="GCB-001";
ELSEIF EXISTS (SELECT * 
				FROM holiday 
				WHERE city_id = cityId and 
                holiday_day = day(pdate) and
                holiday_month = month(pdate))
THEN SET resultCode ="GCB-HOL";
ELSE
	select * 
    from booking 
    where city_id = cityId and pdate = date
    order by startTime;
  SET resultCode = '200';
END IF;
end//
DELIMITER ;

-- call getCityBookings("A", date("2020-05-10"), @code);

call getCityBookings("A", date("2020-09-02"), @code);
select @code;
