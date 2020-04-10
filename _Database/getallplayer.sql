DROP PROCEDURE IF EXISTS getplayer;
DELIMITER //
CREATE PROCEDURE getplayer()
BEGIN
-- select all players --
select * from player;
END //
DELIMITER ;
call getplayer();