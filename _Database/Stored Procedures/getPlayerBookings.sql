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

/* Test if getPlayerBookings is valid and existed, then return the information */
delete from city where city_id = "HCM";
delete from center where center_id = "A" and city_id = "HCM";
delete from court where court_id = "1" and center_id = "A" and city_id = "HCM";
delete from player where player_id = "P";
delete from booking where booking_id = "booking1";
CALL createCity("HCM",@code);
CALL createCityCenter("A","HCM",@code);
CALL createCityCenterCourt("1", "HCM", "A", @code);
CALL createPlayer("P", @code);
CALL createBooking("booking1", "2021-05-01", "10:00:00" , "10:45:00" , 'HCM', 'A', '1', 'P', @code);
call getPlayerBookings('P', @code);

/* Test if getPlayerBookings is rejected when centerId is invalid */
call getPlayerBookings('#', @code);
/* expected error code GPB-000 */

/* Test if getPlayerBookings is rejected when centerId is not existed */
call getPlayerBookings('A', @code);
/* expected error code GPB-001 */



