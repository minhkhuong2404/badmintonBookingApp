-- createPlayer(playerId)
DROP PROCEDURE IF EXISTS createPlayer;
DELIMITER //
CREATE PROCEDURE createPlayer(
in pplayerId varchar(50),
OUT resultCode varchar(50))
BEGIN

IF NOT pplayerId REGEXP '^[a-zA-Z0-9]*$'
THEN
     SET resultCode = 'CPL-000';
ELSEIF EXISTS ( SELECT * 
			FROM player
            WHERE player_id = pplayerId )
THEN
     SET resultCode = 'CPL-001';
ELSE 
    INSERT INTO player VALUES (pplayerId);
    SET resultCode = '200';
END IF;

SELECT resultCode;

END //
DELIMITER ;

/* Test cases for createPlayer */