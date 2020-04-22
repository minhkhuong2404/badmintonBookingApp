DROP PROCEDURE IF EXISTS getCities;
DELIMITER //
CREATE PROCEDURE getCities(out resultCode varchar(50))
BEGIN
-- select all cities --
select * from city;
SET resultCode = '200';

SELECT resultCode;
END //
DELIMITER ;
delete from city where city_id = "HCM";
call createCity("HCM", @code);
call getCities(@code);