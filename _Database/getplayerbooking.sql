DROP PROCEDURE IF EXISTS getPlayerBookings;
DELIMITER //
CREATE PROCEDURE getPlayerBookings(in playerID varchar(50))
BEGIN
-- select players booking court --
select * from booking where playerID = player_id;
END //
DELIMITER ;

call getPlayerBookings('Customer#A');