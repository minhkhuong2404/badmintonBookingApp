DROP PROCEDURE IF EXISTS getcity;
DELIMITER //
CREATE PROCEDURE getcity()
BEGIN
-- select all cities --
select * from city;
END //
DELIMITER ;

call getcity();