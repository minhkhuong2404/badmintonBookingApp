-- createCity
DROP PROCEDURE IF EXISTS createCity;
DELIMITER //
CREATE PROCEDURE createCity(
in pcity varchar(50))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
  GET STACKED DIAGNOSTICS CONDITION 1 @p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
  SELECT @p1, @p2;
  ROLLBACK;
END;
START TRANSACTION;
IF EXISTS ( SELECT * 
			FROM city
            WHERE city_id = cityId )
THEN SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'CITY-001';
     -- CITY-001: City existed   
ELSE 
    INSERT INTO city VALUES (cityId);
END IF;

END //
DELIMITER ;

/* Test cases for createCity */
# createCity parameter: cityId
/* test 1 */
Call createCity("Hanoi");
/* error: CC-001 */

# createCityCenter parameter: cityId, centerId
/* test 2 */
Call createCityCenter("Hanoi","HCM_quan2");
/* error: CCtr-001 */

# createCityCenterCourt parameter: cityId, centerId, courtId
/* test 3 */
call createCityCenterCourt("HCM","HCM_quan1","Court#1");
/* error: CCourt-001 */

# createplayer parameter: playerId
/* test 4 */
call createplayer("customer#A");
/* error: CPL-001 */

# createStaff parameter: staffId, centerId, cityId
/* test 5 */ 
call createStaff("Staff#1","HCM_quan1","HCM");
/* error: CS-001 */
