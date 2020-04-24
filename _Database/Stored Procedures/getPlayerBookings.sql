DROP PROCEDURE IF EXISTS getPlayerBookings;
DELIMITER //
CREATE PROCEDURE getPlayerBookings(
IN playerId varchar(50),
IN pdate date,
OUT resultCode varchar(50))
BEGIN

START TRANSACTION;
IF NOT playerId REGEXP '^[a-zA-Z0-9]*$'
THEN SET resultCode = 'GPB-000';
ELSEIF NOT EXISTS (SELECT * FROM booking WHERE player_id = playerId)
THEN SET resultCode = "GPB-001";
ELSE select * 
     from booking 
     where playerId = player_id and date = pdate;
     SET resultCode = '200';
END IF;
end//
DELIMITER ;

call getPlayerBookings("player1", DATE("2020-05-10"), @code);