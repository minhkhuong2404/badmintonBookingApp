DROP PROCEDURE IF EXISTS getStaffs;
DELIMITER //
CREATE PROCEDURE getStaffs(out resultCode varchar(50))
BEGIN
-- select all staffs --
select * from staff;
SET resultCode = '200';
END //
DELIMITER ;
