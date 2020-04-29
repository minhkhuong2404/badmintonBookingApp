DROP PROCEDURE IF EXISTS getCenterCourts;
DELIMITER //
CREATE PROCEDURE getCenterCourts(
	in centerId varchar(50), 
	out resultCode varchar(50))
BEGIN

IF NOT centerId REGEXP '^[a-zA-Z0-9]*$'
THEN 
	SET resultCode = 'GCC-001';
ELSEIF NOT EXISTS (SELECT * FROM center WHERE center_id = centerId)
THEN
	SET resultCode = 'GCC-002';
ELSE 
	select * 
    from court 
    where centerId = center_id
	order by court_id;
	SET resultCode = '200';
END IF;
end//
DELIMITER ;

call getCenterCourts('C1', @code);