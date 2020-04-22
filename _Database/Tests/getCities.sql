/* 
[English]
Test guide for: getCities()
- Ensure the database is empty when doing the tests (clean up city will let database empty)
- Every test has n SQL statements, the (n - 1) first SQL statements are the scenario for the test.
- The last SQL statements is the actual test.
[Tiếng Việt]
Hướng dẫn kiểm thử cho: getCities()
- Đảm bảo rằng database hoàn toàn trống (xoá dữ liệu trong city sẽ làm toàn bộ database trống)
- Mỗi test có n câu lệnh SQL, (n - 1) câu lệnh đầu tiên dùng để tạo dữ liệu mẫu.
- Câu lệnh SQL cuối cùng là câu lệnh dùng để test.
*/ 

/* Test if getCities is accepted */
delete from city where city_id = "HCM";
call createCity("HCM", @code);
call getCities(@code);
/* expected no error code */
