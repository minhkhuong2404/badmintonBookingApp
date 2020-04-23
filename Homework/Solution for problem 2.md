## How system authorize the staff when they update booking ?

Our scenario:
* Staff is assigned to a center
* In database, table `staff` contains `centerId`
* In database, table `booking` contains `centerId`

### Solution
* To authorize staff for making changes of a booking, the server will compare the `centerId` in both table `staff` and `booking`, if they are the same, then the staff can change the status of booking, else it will reject it.  
* To check staff existence, we can check the `staff id` of that staff in the database to see if it exists.  
