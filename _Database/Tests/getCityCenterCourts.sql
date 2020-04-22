/* 
[English]
Test guide for: getCityCenterCourts(centerId, cityId)
- Ensure the database is empty when doing the tests (clean up city, center, court will let database empty)
- Every test has n SQL statements, the (n - 1) first SQL statements are the scenario for the test.
- The last SQL statements is the actual test.
[Tiếng Việt]
Hướng dẫn kiểm thử cho: getCityCenterCourts(centerId, cityId)
- Đảm bảo rằng database hoàn toàn trống (xoá dữ liệu trong city, center, court sẽ làm toàn bộ database trống)
- Mỗi test có n câu lệnh SQL, (n - 1) câu lệnh đầu tiên dùng để tạo dữ liệu mẫu.
- Câu lệnh SQL cuối cùng là câu lệnh dùng để test.
*/ 

/* Test if getCityCenterCourts is accepted when centerId and cityId are valid */
delete from city where city_id = "HCM";
delete from center where center_id = "A" and city_id = "HCM";
delete from court where court_id = "1" and center_id = "A" and city_id = "HCM";
CALL createCity("HCM",@code);
CALL createCityCenter("A","HCM",@code);
CALL createCityCenterCourt("1", "HCM", "A", @code);
CALL getCityCenterCourts("A", "HCM", @code);
/* expected no error code */

/* Tests if getCityCenterCourts is rejected when cityId is invalid */
CALL getCityCenterCourts("A", "#", @code);
/* expected error GCCC-000 */

/* Tests if getCityCenterCourts is rejected when centerId is invalid */
CALL getCityCenterCourts("#", "A", @code);
/* expected error GCCC-001 */

/* Tests if getCityCenterCourts is rejected when cityId is not existed */
CALL getCityCenterCourts("A", "A", @code);
/* expected error GCCC-002 */

/* Tests if getCityCenterCourts is rejected when centerId is not existed */
CALL getCityCenterCourts("HCM", "HCM", @code);
/* expected error GCCC-003 */
