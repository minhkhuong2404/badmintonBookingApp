DROP PROCEDURE IF EXISTS getCenterBookings;
DELIMITER //
CREATE PROCEDURE getCenterBookings(in centerID varchar(50))
BEGIN
-- select players booking court --
select * from booking where centerID = center_id;
END //
DELIMITER ;

call getCenterBookings('HCM_quan1');