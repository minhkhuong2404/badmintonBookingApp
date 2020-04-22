DROP PROCEDURE IF EXISTS getStaffs;
DELIMITER //
CREATE PROCEDURE getStaffs(out resultCode varchar(50))
BEGIN
-- select all staffs --
select * from staff;
SET resultCode = '200';
SELECT resultCode;
END //
DELIMITER ;
delete from staff where staff_id = "S";
CALL createStaff("S", "HCM", "A", @code);
CALL getStaffs(@code);