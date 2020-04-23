# Solution for authentication
* we put centerId in both booking and staff, so when authorize staff, the server will compare the centerId in both table, if they are the same, then the staff can change the status of booking, else it will reject it.  
* If we want to check if the staff has the right to change booking status or not, check the staff id of that staff in the database to see if it exists
