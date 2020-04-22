/* 
[English]
Test guide for: getCityCenters(cityId)
- Ensure the database is empty when doing the tests (clean up city, center will let database empty)
- Every test has n SQL statements, the (n - 1) first SQL statements are the scenario for the test.
- The last SQL statements is the actual test.
[Tiếng Việt]
Hướng dẫn kiểm thử cho: getCityCenters(cityId)
- Đảm bảo rằng database hoàn toàn trống (xoá dữ liệu trong city, center sẽ làm toàn bộ database trống)
- Mỗi test có n câu lệnh SQL, (n - 1) câu lệnh đầu tiên dùng để tạo dữ liệu mẫu.
- Câu lệnh SQL cuối cùng là câu lệnh dùng để test.
*/ 

/* Tests if getCityCenters is valid then return the table center*/
delete from city where city_id = "HCM";
delete from center where center_id = "A" and city_id = "HCM";
CALL createCity("HCM", @code);
CALL createCityCenter("A", "HCM", @code);
CALL getCityCenters("HCM",@code);
/* expected: the Center table*/

/* Tests if getCityCenters is rejected when cityId is invalid */
CALL getCityCenters("#",@code);
/* expected error GCC-000 */

/* Tests if getCityCenters is rejected when cityId is not existed */
delete from center where center_id = "A";
CALL getCityCenters("A",@code);
/* expected error GCC-001 */
