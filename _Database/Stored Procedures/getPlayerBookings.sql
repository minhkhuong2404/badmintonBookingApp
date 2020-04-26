DROP PROCEDURE IF EXISTS getPlayerBookings;
DELIMITER //
CREATE PROCEDURE getPlayerBookings(
IN playerId varchar(50),
IN cityId varchar(50),
IN pdate date,
OUT resultCode varchar(50))
BEGIN

IF NOT playerId REGEXP '^[a-zA-Z0-9]*$'
THEN SET resultCode = 'GPB-000';
ELSEIF NOT EXISTS (SELECT * FROM booking WHERE city_id = cityId)
THEN SET resultCode = "GPB-001";
ELSEIF NOT EXISTS (SELECT * FROM booking WHERE player_id = playerId)
THEN SET resultCode = "GPB-001";
ELSE select * 
     from booking 
     where city_id = cityId and playerId = player_id and date = pdate ;
     SET resultCode = '200';
END IF;
end//
DELIMITER ;

call getPlayerBookings("player1", "B", date("2020-05-10"), @code);