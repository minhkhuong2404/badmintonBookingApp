## How can system authorize the staff when they update the booking ? 
## Our scenario:
* Staff is assigned to a center
* In database, table `staff` contains `centerId`
* In database, table `booking` contains `centerId`  

### Solution
* we put centerId in both booking table and staff table, so when authorize staff, the server will compare the centerId in both table, if they are the same, then the staff can change the status of booking, else it will reject it.  
* we can check the staff's account by staff id of that staff in the database to see if it is in database.  
