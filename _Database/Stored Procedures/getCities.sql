DROP PROCEDURE IF EXISTS getCities;
DELIMITER //
CREATE PROCEDURE getCities(out resultCode varchar(50))
BEGIN
    select *
    from city;

    SET resultCode = '200';
END //
DELIMITER ;
