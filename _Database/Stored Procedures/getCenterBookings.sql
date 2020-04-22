DROP PROCEDURE IF EXISTS getCenterBookings;
DELIMITER //
CREATE PROCEDURE getCenterBookings(
  in centerId varchar(50),
  out resultCode varchar(50))
BEGIN

IF NOT centerId REGEXP '^[a-zA-Z0-9]*$'
THEN SET resultCode = 'GCB-000';
ELSEIF NOT EXISTS (SELECT * FROM booking WHERE center_id = centerId)
THEN SET resultCode ="GCB-001";
ELSE
	select * from booking where centerId = center_id;
  SET resultCode = '200';
END IF;
SELECT resultCode;
end//
DELIMITER ;

/* Test if getCenterBookings is valid and existed, then return the information */
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
CALL getCenterBookings('A', @code);

/* Test if getCenterBookings is rejected when centerId is invalid */
call getCenterBookings('#', @code);
/* expected error code GCB-000 */

/* Test if getCenterBookings is rejected when centerId is not existed */
call getCenterBookings('B', @code);
/* expected error code GCB-001 */
