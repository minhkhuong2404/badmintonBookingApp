/* 
[English]
Test guide for: createPlayer(playerId)
- Ensure the database is empty when doing the tests (clean up city, player will let database empty)
- Every test has n SQL statements, the (n - 1) first SQL statements are the scenario for the test.
- The last SQL statements is the actual test.
[Tiếng Việt]
Hướng dẫn kiểm thử cho: createPlayer(playerId)
- Đảm bảo rằng database hoàn toàn trống (xoá dữ liệu trong city, player sẽ làm toàn bộ database trống)
- Mỗi test có n câu lệnh SQL, (n - 1) câu lệnh đầu tiên dùng để tạo dữ liệu mẫu.
- Câu lệnh SQL cuối cùng là câu lệnh dùng để test.
*/ 

/* Test if createPlayer is accepted when cityId is valid */
delete from player where player_id ="A";
CALL createPlayer("A");
/* expected no error code */
 
/* Test if createPlayer is rejected when cityId is invalid */
CALL createPlayer("$A");
/* expected error CPL-000 */

/* Test if createPlayer is rejected when cityId is existed */
delete from player where player_id ="A";
CALL createPlayer("A");
CALL createPlayer("A");
/* expected error CPL-001 */