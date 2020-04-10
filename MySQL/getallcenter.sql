DROP PROCEDURE IF EXISTS getcenter;
DELIMITER //
CREATE PROCEDURE getcenter(in cityId varchar(50))
BEGIN
-- select all cities --
select * from center where cityId = city_id;
END //
DELIMITER ;

call getcenter('Hanoi');