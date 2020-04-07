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