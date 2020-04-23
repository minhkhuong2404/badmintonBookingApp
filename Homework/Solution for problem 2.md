## How can system authorize the staff when they update the booking ?    
### Solution
* we put centerId in both booking table and staff table, so when authorize staff, the server will compare the centerId in both table, if they are the same, then the staff can change the status of booking, else it will reject it.  
* we can check the staff id of that staff in the database to see if it is in database.  
