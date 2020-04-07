/* Test if createCityCenter is accepted when centerId and cityId are valid */
delete from city where city_id = "1";
delete from center where center_id = "A" and city_id = "1";
CALL createCity("1");
CALL createCityCenter("A", "1");
/* expected no error code */

/* Tests if createCityCenter is rejected when cityId is invalid */
CALL createCityCenter("A", "#");
/* expected error CEN-000 */

/* Tests if createCityCenter is rejected when centerId is invalid */
CALL createCityCenter("#", "A");
/* expected error CEN-001 */

/* Tests if createCityCenter is rejected when cityId is not existed */
CALL createCityCenter("A", "A");
/* expected error CEN-002 */

/* Tests if createCityCenter is rejected when centerId is existed */
delete from city where city_id = "1";
delete from center where center_id = "A" and city_id = "1";
CALL createCity("1");
CALL createCityCenter("A", "1");
CALL createCityCenter("A", "1");
/* expected error CEN-003 */