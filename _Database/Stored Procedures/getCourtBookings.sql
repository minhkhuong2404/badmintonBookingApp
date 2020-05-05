--getCourtBookings(courtId,pdate)--
DROP PROCEDURE IF EXISTS getCourtBookings;
DELIMITER //
CREATE PROCEDURE getCourtBookings(
  in courtId varchar(50),
  in pdate date,
  out resultCode varchar(50))
BEGIN

IF NOT courtId REGEXP '^[a-zA-Z0-9]*$'
THEN SET resultCode = 'GCB-000';
ELSEIF NOT EXISTS (SELECT * FROM booking WHERE court_id = courtId)
THEN SET resultCode = 'GCB-001';
ELSE
	select * from booking 
    where court_id = courtId and pdate = date --check court and date whether valid or not--
    order by startTime;
  SET resultCode = '200';
END IF;
end//
DELIMITER ;


call getCourtBookings('A1C', date('2020-05-10'), @code);
