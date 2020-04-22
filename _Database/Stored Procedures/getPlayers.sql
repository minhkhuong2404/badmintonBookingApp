DROP PROCEDURE IF EXISTS getPlayers;
DELIMITER //
CREATE PROCEDURE getPlayers(out resultCode varchar(50))
BEGIN
-- select all players --
select * from player;
SET resultCode = '200';
END //
DELIMITER ;
call getPlayers();