/* 
[English]
Test guide for: createCityCenterCourt(courtId, cityId, centerId)
- Ensure the database is empty when doing the tests (clean up city, player will let database empty)
- Every test has n SQL statements, the (n - 1) first SQL statements are the scenario for the test.
- The last SQL statements is the actual test.
[Tiếng Việt]
Hướng dẫn kiểm thử cho: createCityCenterCourt(courtId, cityId, centerId)
- Đảm bảo rằng database hoàn toàn trống (xoá dữ liệu trong city, player sẽ làm toàn bộ database trống)
- Mỗi test có n câu lệnh SQL, (n - 1) câu lệnh đầu tiên dùng để tạo dữ liệu mẫu.
- Câu lệnh SQL cuối cùng là câu lệnh dùng để test.
*/ 

/* Test if createCityCenterCourt is accepted when courtId, centerId and cityId are valid */
delete from city where city_id = "1";
delete from center where center_id = "2" and city_id = "1";
delete from court where court_id = "A" and city_id = "1" and center_id = "2";
CALL createCity("1");
CALL createCityCenter("2", "1");
CALL createCityCenterCourt("A", "1", "2");
/* expected no error code */

/* Test if createCityCenterCourt is rejected when cityId is invalid */
CALL createCityCenterCourt("A", "#", "2");
/* expected error code CRT-000 */

/* Test if createCityCenterCourt is rejected when centerId is invalid */
CALL createCityCenterCourt("A", "1", "#");
/* expected error code CRT-001 */

/* Test if createCityCenterCourt is rejected when courtId is invalid */
CALL createCityCenterCourt("#", "1", "2");
/* expected error code CRT-002 */

/* Test if createCityCenterCourt is rejected when centerId is existed but cityId is not existed */
delete from city where city_id = "CITY";
delete from center where center_id = "CENTER" and city_id = "CITY";
delete from court where court_id = "A" and city_id = "CITY2" and center_id = "CENTER";
CALL createCity("CITY");
CALL createCityCenter("CENTER", "CITY");
CALL createCityCenterCourt("A", "CITY2", "CENTER");
/* expected error code CRT-003 */

/* Test if createCityCenterCourt is rejected when centerId is not existed but cityId is existed*/
delete from city where city_id = "CITY";
delete from center where center_id = "CENTER" and city_id = "CITY";
delete from court where court_id = "A" and city_id = "CITY" and center_id = "CENTER2";
CALL createCity("CITY");
CALL createCityCenter("CENTER", "CITY");
CALL createCityCenterCourt("A", "CITY", "CENTER2");
/* expected error code CRT-004 */

/* Test if createCityCenterCourt is rejected when both centerId and cityId are not existed */
delete from city where city_id = "CITY";
delete from center where center_id = "CENTER" and city_id = "CITY";
delete from court where court_id = "A" and city_id = "CITY2" and center_id = "CENTER2";
CALL createCity("CITY");
CALL createCityCenter("CENTER", "CITY");
CALL createCityCenterCourt("A", "CITY2", "CENTER2");
/* expected error code CRT-003 */

/* Test if createCityCenterCourt is rejected when courtId is existed */
delete from city where city_id = "1";
delete from center where center_id = "2" and city_id = "1";
delete from court where court_id = "A" and city_id = "1" and center_id = "2";
CALL createCity("1");
CALL createCityCenter("2", "1");
CALL createCityCenterCourt("A", "1", "2");
CALL createCityCenterCourt("A", "1", "2");
/* expected error code CRT-005 */
