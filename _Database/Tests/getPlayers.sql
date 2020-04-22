/* 
[English]
Test guide for: getPlayers()
- Ensure the database is empty when doing the tests (clean up player will let database empty)
- Every test has n SQL statements, the (n - 1) first SQL statements are the scenario for the test.
- The last SQL statements is the actual test.
[Tiếng Việt]
Hướng dẫn kiểm thử cho: getPlayers()
- Đảm bảo rằng database hoàn toàn trống (xoá dữ liệu trong player sẽ làm toàn bộ database trống)
- Mỗi test có n câu lệnh SQL, (n - 1) câu lệnh đầu tiên dùng để tạo dữ liệu mẫu.
- Câu lệnh SQL cuối cùng là câu lệnh dùng để test.
*/ 

/* Test if getPlayer is accepted */
delete from player where player_id = "P";
CALL createPlayer("P", @code);
CALL getPlayers(@code);
/* expected table player and no error code */
