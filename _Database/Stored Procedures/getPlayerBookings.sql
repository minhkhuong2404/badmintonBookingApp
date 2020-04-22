DROP PROCEDURE IF EXISTS getPlayerBookings;
DELIMITER //
CREATE PROCEDURE getPlayerBookings(
IN playerId varchar(50),
OUT resultCode varchar(50))
BEGIN

START TRANSACTION;
IF NOT playerId REGEXP '^[a-zA-Z0-9]*$'
THEN SET resultCode = 'GPB-000';
ELSEIF NOT EXISTS (SELECT * FROM booking WHERE player_id = playerId)
THEN SET resultCode = "GPB-001";
ELSE select * 
     from booking 
     where playerId = player_id;
     SET resultCode = '200';
END IF;
SELECT resultCode;
end//
DELIMITER ;
