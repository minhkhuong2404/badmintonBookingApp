DROP PROCEDURE IF EXISTS updateBookingStatus;
DELIMITER //
CREATE PROCEDURE updateBookingStatus(
in status int,
in bookingId varchar(50), 
in cityId varchar(50), 
in cenyterId varchar(50),
in staffId varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
START TRANSACTION;
-- CP-001: staff not exist
IF NOT EXISTS ( SELECT * 
				FROM staff
                WHERE staff_id = staffId )
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CP-001';
-- CP-002: booking not exist
ELSEIF NOT EXISTS ( SELECT * 
				FROM booking
                WHERE booking_id = bookingId )
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CP-002';
-- CP-003: this staff has no relationship with the booking (join booking - court - center - staff)
ELSEIF NOT EXISTS ( SELECT b.booking_id, co.court_id, s.staff_id
				FROM booking b, court co, center ce, staff s, city ci
				WHERE ( co.court_id = b.court_id and
						ce.center_id = co.center_id and
						s.center_id = ce.center_id and
                        s.city_id = ci.city_id and
						b.booking_id = bookingId and
						s.staff_id =  staffId) )
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CP-003';
-- change status
ELSE	UPDATE booking
		SET paymentstatus = status
        WHERE booking_id = bookingId;
END IF;
END //
DELIMITER ;