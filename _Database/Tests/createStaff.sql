/* Test if createStaff is accepted when courtId, centerId and cityId are valid */
CALL createCity("1");
CALL createCityCenter("2", "1");
CALL createStaff("A", "1", "2");
/* expected no error code */

/* Test if createStaff is rejected when staffId is invalid */
CALL createStaff("#", "1", "2");
/* expected error code CRT-002 */

/* Test if createStaff is rejected when cityId is invalid */
CALL createStaff("A", "#", "2");
/* expected error code CRT-000 */

/* Test if createStaff is rejected when centerId is invalid */
CALL createStaff("A", "1", "#");
/* expected error code CRT-001 */

/* Test if createStaff is rejected when centerId is existed but cityId is not existed */
CALL createCity("CITY");
CALL createCityCenter("CENTER", "CITY");
CALL createStaff("A", "CITY2", "CENTER");
/* expected error code CRT-003 */

/* Test if createStaff is rejected when centerId is not existed but cityId is existed*/
CALL createCity("CITY");
CALL createCityCenter("CENTER", "CITY");
CALL createStaff("A", "CITY", "CENTER2");
/* expected error code CRT-003 */

/* Test if createStaff is rejected when both centerId and cityId are not existed */
CALL createCity("CITY");
CALL createCityCenter("CENTER", "CITY");
CALL createStaff("A", "CITY2", "CENTER2");
/* expected error code CRT-003 */

/* Test if createStaff is rejected when courtId is existed */
CALL createCity("1");
CALL createCityCenter("2", "1");
CALL createStaff("A", "1", "2");
CALL createStaff("A", "1", "2");
/* expected error code CRT-005 */