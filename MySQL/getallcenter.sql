DROP PROCEDURE IF EXISTS getallcenter;
DELIMITER //
CREATE PROCEDURE getallcenter(in cityId varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
  
START TRANSACTION;
IF NOT cityId REGEXP '^[a-zA-Z0-9]*$'
THEN 
	 SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CEN-000';
ELSEIF NOT EXISTS (SELECT * FROM center WHERE city_id = cityId)
THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT ="CEN-001";
ELSE
	select * from center where cityId = city_id;
END IF;
end//
DELIMITER ;


/* Tests if getallcenter is rejected when cityId is invalid */
CALL getallcenter("#");
/* expected error CEN-000 */

/* Tests if getallcenter is rejected when cityId is not existed */
delete from center where center_id = "A";
CALL getallcenter("A");
/* expected error CEN-001 */

/* Tests if getallcenter is valid then return the table center*/
call getallcenter("HCM");
/* expected: the Center table*/
