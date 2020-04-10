DROP PROCEDURE IF EXISTS getallstaff;
DELIMITER //
CREATE PROCEDURE getallstaff()
BEGIN
-- select all staffs --
select * from staff ;
END //
DELIMITER ;

call getallstaff();