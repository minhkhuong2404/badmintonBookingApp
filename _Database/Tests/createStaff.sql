/* 
[English]
Test guide for: createStaff(cityId)
- Ensure the database is empty when doing the tests (clean up city, player will let database empty)
- Every test has n SQL statements, the (n - 1) first SQL statements are the scenario for the test.
- The last SQL statements is the actual test.
[Tiếng Việt]
Hướng dẫn kiểm thử cho: createStaff(cityId)
- Đảm bảo rằng database hoàn toàn trống (xoá dữ liệu trong city, player sẽ làm toàn bộ database trống)
- Mỗi test có n câu lệnh SQL, (n - 1) câu lệnh đầu tiên dùng để tạo dữ liệu mẫu.
- Câu lệnh SQL cuối cùng là câu lệnh dùng để test.
*/ 

/* Test if createStaff is accepted when courtId, centerId and cityId are valid */
delete from city where city_id = "1";
delete from center where center_id = "2" and city_id = "1";
delete from staff where staff_id = "A" and city_id = "1" and center_id = "2";
CALL createCity("1", @code);
CALL createCityCenter("2", "1", @code);
CALL createStaff("A", "1", "2", @code);
/* expected no error code */

/* Test if createStaff is rejected when cityId is invalid */
CALL createStaff("A", "#", "2", @code);
/* expected error code CS-000 */

/* Test if createStaff is rejected when centerId is invalid */
CALL createStaff("A", "1", "#", @code);
/* expected error code CRT-001 */

/* Test if createStaff is rejected when staffId is invalid */
CALL createStaff("#", "1", "2", @code);
/* expected error code CS-002 */

/* Test if createStaff is rejected when centerId is existed but cityId is not existed */
delete from city where city_id = "CITY";
delete from center where center_id = "CENTER" and city_id = "CITY";
delete from staff where staff_id = "A" and city_id = "CITY2" and center_id = "CENTER";
CALL createCity("CITY", @code);
CALL createCityCenter("CENTER", "CITY", @code);
CALL createStaff("A", "CITY2", "CENTER", @code);
/* expected error code CS-003 */

/* Test if createStaff is rejected when centerId is not existed but cityId is existed*/
delete from city where city_id = "CITY";
delete from center where center_id = "CENTER" and city_id = "CITY";
delete from staff where staff_id = "A" and city_id = "CITY" and center_id = "CENTER2";
CALL createCity("CITY", @code);
CALL createCityCenter("CENTER", "CITY", @code);
CALL createStaff("A", "CITY", "CENTER2", @code);
/* expected error code CS-004 */

/* Test if createStaff is rejected when both centerId and cityId are not existed */
delete from city where city_id = "CITY";
delete from center where center_id = "CENTER" and city_id = "CITY";
delete from staff where staff_id = "A" and city_id = "CITY2" and center_id = "CENTER2";
CALL createCity("CITY", @code);
CALL createCityCenter("CENTER", "CITY", @code);
CALL createStaff("A", "CITY2", "CENTER2", @code);
/* expected error code CS-003 */

/* Test if createStaff is rejected when staffId is existed */
delete from city where city_id = "1";
delete from center where center_id = "2" and city_id = "1";
delete from staff where staff_id = "A" and city_id = "1" and center_id = "2";
CALL createCity("1", @code);
CALL createCityCenter("2", "1", @code);
CALL createStaff("A", "1", "2", @code);
CALL createStaff("A", "1", "2", @code);
/* expected error code CS-005 */
