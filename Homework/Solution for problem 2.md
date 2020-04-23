# How system authorize the staff when they update booking ?
## Solution
* we put centerId in both booking and staff, so when authorize staff, the server will compare the centerId in both table, if they are the same, then the staff can change the status of booking, else it will reject it.  
* we can check if the staff has the right to change booking status or not by check the staff id of that staff in the database to see if it exists.  
* centerId and StaffId must be a primary key and unique.  
