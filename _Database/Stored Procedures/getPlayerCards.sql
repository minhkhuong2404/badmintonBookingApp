DROP PROCEDURE IF EXISTS getPlayerCards;
DELIMITER //
CREATE PROCEDURE getPlayerCards(
IN playerId varchar(50))
BEGIN

select *
from booking_card
where player_id = playerId
order by time_bought;
end //
DELIMITER ;

call getPlayerCards('player1');