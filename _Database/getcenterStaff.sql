DROP PROCEDURE IF EXISTS getstaff;
DELIMITER //
CREATE PROCEDURE getstaff(in centerId varchar(50))
BEGIN
-- select all staffs --
select * from staff where centerId = center_id;
END //
DELIMITER ;

call getstaff('HCM_quan1');