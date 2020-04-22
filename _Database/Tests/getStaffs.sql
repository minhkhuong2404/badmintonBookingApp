/* 
[English]
Test guide for: getStaffs()
- Ensure the database is empty when doing the tests (clean up staff will let database empty)
- Every test has n SQL statements, the (n - 1) first SQL statements are the scenario for the test.
- The last SQL statements is the actual test.
[Tiếng Việt]
Hướng dẫn kiểm thử cho: getStaffs()
- Đảm bảo rằng database hoàn toàn trống (xoá dữ liệu trong staff sẽ làm toàn bộ database trống)
- Mỗi test có n câu lệnh SQL, (n - 1) câu lệnh đầu tiên dùng để tạo dữ liệu mẫu.
- Câu lệnh SQL cuối cùng là câu lệnh dùng để test.
*/ 

/* Test if getStaffs is accepted */
delete from staff where staff_id = "S";
delete from city where city_id = "HCM";
delete from center where center_id = "A" and city_id = "HCM";
CALL createCity("HCM",@code);
CALL createCityCenter("A","HCM",@code);
CALL createStaff("S", "HCM", "A", @code);
CALL getStaffs(@code);
/* expected table staff and no error code */
