/* 
[English]
Test guide for: createCityCenter(centerId, cityId)
- Ensure the database is empty when doing the tests (clean up city, player will let database empty)
- Every test has n SQL statements, the (n - 1) first SQL statements are the scenario for the test.
- The last SQL statements is the actual test.
[Tiếng Việt]
Hướng dẫn kiểm thử cho: createCityCenter(centerId, cityId)
- Đảm bảo rằng database hoàn toàn trống (xoá dữ liệu trong city, player sẽ làm toàn bộ database trống)
- Mỗi test có n câu lệnh SQL, (n - 1) câu lệnh đầu tiên dùng để tạo dữ liệu mẫu.
- Câu lệnh SQL cuối cùng là câu lệnh dùng để test.
*/ 

/* Test if createCityCenter is accepted when centerId and cityId are valid */
delete from city where city_id = "1";
delete from center where center_id = "A" and city_id = "1";
CALL createCity("1", @code);
CALL createCityCenter("A", "1", @code);
/* expected no error code */

/* Tests if createCityCenter is rejected when cityId is invalid */
CALL createCityCenter("A", "#", @code);
/* expected error CEN-000 */

/* Tests if createCityCenter is rejected when centerId is invalid */
CALL createCityCenter("#", "A", @code);
/* expected error CEN-001 */

/* Tests if createCityCenter is rejected when cityId is not existed */
CALL createCityCenter("A", "A", @code);
/* expected error CEN-002 */

/* Tests if createCityCenter is rejected when centerId is existed */
delete from city where city_id = "1";
delete from center where center_id = "A" and city_id = "1";
CALL createCity("1", @code);
CALL createCityCenter("A", "1", @code);
CALL createCityCenter("A", "1", @code);
/* expected error CEN-003 */