/* Test if createCity is accepted when cityId is valid */
CALL createCity("A");
/* expected no error code */
 
/* Test if createCity is rejected when cityId is invalid */
CALL createCity("$A");
/* expected error CITY-000 */

/* Test if createCity is rejected when cityId is existed */
CALL createCity("A");
CALL createCity("A");
/* expected error CITY-001 */
