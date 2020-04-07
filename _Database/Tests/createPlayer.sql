/* Test if createPlayer is accepted when cityId is valid */
CALL createPlayer("A");
/* expected no error code */
 
/* Test if createPlayer is rejected when cityId is invalid */
CALL createPlayer("$A");
/* expected error CPL-000 */

/* Test if createPlayer is rejected when cityId is existed */
CALL createPlayer("A");
CALL createPlayer("A");
/* expected error CPL-001 */