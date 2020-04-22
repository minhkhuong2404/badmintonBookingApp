/* 
[English]
Test guide for: getPlayerBookings(playerId)
- Ensure the database is empty when doing the tests (clean up city, center, court, player, booking will let database empty)
- Every test has n SQL statements, the (n - 1) first SQL statements are the scenario for the test.
- The last SQL statements is the actual test.
[Tiếng Việt]
Hướng dẫn kiểm thử cho: getPlayerBookings(playerId)
- Đảm bảo rằng database hoàn toàn trống (xoá dữ liệu trong city, center, court, player, booking sẽ làm toàn bộ database trống)
- Mỗi test có n câu lệnh SQL, (n - 1) câu lệnh đầu tiên dùng để tạo dữ liệu mẫu.
- Câu lệnh SQL cuối cùng là câu lệnh dùng để test.
*/ 

/* Test if getPlayerBookings is valid and existed, then return the information */
delete from city where city_id = "HCM";
delete from center where center_id = "A" and city_id = "HCM";
delete from court where court_id = "1" and center_id = "A" and city_id = "HCM";
delete from player where player_id = "P";
delete from booking where booking_id = "booking1";
CALL createCity("HCM",@code);
CALL createCityCenter("A","HCM",@code);
CALL createCityCenterCourt("1", "HCM", "A", @code);
CALL createPlayer("P", @code);
CALL createBooking("booking1", "2021-05-01", "10:00:00" , "10:45:00" , 'HCM', 'A', '1', 'P', @code);
call getPlayerBookings('P', @code);

/* Test if getPlayerBookings is rejected when centerId is invalid */
CALL getPlayerBookings('#', @code);
/* expected error code GPB-000 */

/* Test if getPlayerBookings is rejected when centerId is not existed */
CALL getPlayerBookings('A', @code);
/* expected error code GPB-001 */
