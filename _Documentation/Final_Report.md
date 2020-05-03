<div align="center"><h3> Vietnamese-German University  </h3>


  
  
# Programming Exercise Final Report   
### For   
# Booking App  
  
  
  
## Team E  

#### Dang Chi Cong - 13725
#### Lam Hai Son - 11946
#### Luu Nguyen Phat - 13322
#### Lu Minh Khuong - 13156
#### Ngo Minh Thong - 14077
#### Nguyen Tri Nguyen - 13421
#### Nguyen Hoang Quan - 13387 </div>

## 1. Introduction
### 1.1. Project Scope and Description
  * Booking Application is a software which is mainly composed of the following parts:  
    -Booking via handheld mobile phone  
    -Online booking and booking management over the Internet  
    -Customer management  
    
  * With the help of operated handhelds, booking of the customers in the application will directly be transferred to the database and inform to the sport center where the court are prepared. It is easy communication among the customer and sport center. Increasing service efficiency are the qualifications of this system which makes our automation much more effectively and practical for all users of the application.  
  *  Online booking and booking management module provides solutions for the online customers. By using the application constructed by the automation, customers will have list of any court provided by the sport centers in chosen city. In order to use this application, each customer has to have an Facebook account. This account will not only provide online booking but also be used by the users to keep track of all promotions, announcements prepared for registered customers.  
  * Main concern of customer management system is customer related data. This data consists of individual information such as customer’s name, email, customer ID, telephone number, and order related information like booking amount. This module has a capability of identifying customers by the staff of the sport center. If the customer want to change the profile information, they can confirm this by editing account setting. The management module will also show the pending booking which the customer has not paid yet, improve the way of manage the booking list and available slot list. There will be a simple user interface providing the user to select the necessary options on the screen of the handheld. This user interface belongs to the application embedded inside the handheld that communicates with the main computer of the server.  
### 1.2. Software Process Model and Team Organization  
  * In order to complete our project in total of six weeks duration, what we should do exactly is following the requirement, analysis, design, and implementation and testing parts one by one and in each one of these parts returning the previous ones and do some modifications where necessary. we produced prototypes in some periods, and each prototype will have a certain part of the total project.  
  * Our project team is suitable for team organization. Decision on problems and approach are made by all of the group members. There is no hierarchy among the team members.  

Name | Role   
:----: | :----:   
Nguyen Hoang Quan(Leader) | Testing and Doc   
Nguyen Tri Nguyen | Testing and Doc   
Lu Minh Khuong | Presentation Tier(Kotlin)   
Ngo Minh Thong | Presentation Tier(Kotlin)   
Lam Hai Son | Logic Tier(Java)  
Luu Nguyen Phat | Logic Tier(Java)   
Dang Chi Cong | Data Tier(MySQL)   

### 1.3. Definition, Acronyms, and Abbreviations
## 2. Detailed System Description
  * Booking Application is the mobile application software which has online booking, via handheld as the main functions. The system has two user categories which are customer and staff. Below, all of the rights and capabilities of each user will be explained in detail.
### 2.1. Customer
  * Customer is the user who participates in the system as a member of the service. To be a member, customers must login in the system by using their facebook accounts. Customers also have ability to logout. As a member, customer will have their right to access the main features of the application.  
  
  * The first feature is that customer can view the available slot at specific time and city. After login in to the application, customer can choose the city and the time of that court which they want to book. Then customer can click the button "Show available slot" to see the list of available slots at the city and time which they have chosen.  

  * The second feature is to book a court. After customers show all available slots, they can pick a court by clicking the time interval in the center they would like to play in. Then a window will pop-up and confirm the time and center which are chosen. By clicking "No", customer will be return to the main sreen. By clicking "Yes", customer can customize the play time. Finally, to create that booking, customer can click the button "Create".  
  
  * The third feature is showing customer's booking. To use this feature, at the main screen, customer have to click the button "Show my bookings". Then customer will be processed to their booking screen. In here, customer can see the detail list of their bookings which include booking ID, Date, Time, Payment Status, Court and created date. In addtion, the booking which is overdue will be highlight in red color.  
  
  * The last feature is to cancel their booking. First, customer must go to their booking list by using the third feature. Then they can cancel their book by clicking the button "Cancel this booking". A window will pop-up to confirm the cancellation. By clicking "No", customer will return to the booking list screen. By clicking "Yes", customer can cancel that booking if the cancellation is 24 hours before the start time.  
### 2.2. Staff  
  * This user was removed due to the new requirement.  

## 3. Requirements
### 3.1. Hardware and Software Requirements
   * **Hardware requirement:**  
       - Minimum: Android Pie x86 (API 28)  
       - Recommendation: Android Q x86 (API 29)  
       - RAM: 512MB  
       - Storage: at least 10MB for storing the application  
       - Internet connection required  
      
  * **Sofware requirement:**  
       - Android Studio  
       - IntelJ IDEA with JDK version 8 241
       - MySQL
### 3.2. Constraints
* General information/constraints:
  - Cities can have several public sport centres.
  - Each sport centre can have several badminton courts.
  - Sport centres have the same operating hours: 7am to 9pm.
  - Badminton courts can be booked  either for 45 minutes, 1 hour, 1 hour and 15 minutes
or 1 hour and 30 minutes, within the operating hours, 7-days a week, all year round.
  - A user cannot book more than 3 badminton courts (in total) in advance.
  - Payments are made at the sport centres. No online payment is available.
  - If there are past booking  pending of payment, no booking in advance is allowed.
  - A user can cancel any booking (at not cost), but it must be done 
at least 24 hours before the start-time of the booking.
### 3.3. Functionalities
* For users/badminton players:
    - Upon selecting a city (within the consortium) and a date, 
the user can see all the __slots__ 
(i.e., badminton-court; slot-start-time; slot-end-time)
in all the sport centres in the city. Then,  
upon selecting an available slot, the user can make a __booking__, indicating 
the start-time and end-time of the booking, 
within the start-time and end-time of the slot.
    - Upon selecting a city (within the consortium) and a date, 
the user can see all his/her bookings for that date, in all the sport centres
in the city. Then, upon selecting a booking, the user can cancel it (but 
it must be done at least 24 hours before the booking-start-time).

* For sport-centres/staff:
    - Upon selecting a date, the staff in charge can see all the bookings
for that date. Then, upon selecting a booking the staff can see:
the name of the user who made the booking, 
the booking's badminton-court, start-time, and end-time),
and the state of the booking
(paid/unpaid). Also, upon selecting a booking the staff can change
the state of the booking (from unpaid to paid and vice versa).

## 4. UML diagrams
### 4.1. Database
![ER-Diagram](https://github.com/manuelclavel/teamepe2020/blob/flexmng/Images/Diagrams/Entity-Relationship%20Diagram/ER-diagram.png)  
### 4.2. Logic
#### 4.2.1. Activity Diagram for Customer
![CusActDia](https://github.com/manuelclavel/teamepe2020/blob/flexmng/Images/Diagrams/Activity%20Diagram/User%20Application.jpg)  
***
#### 4.2.2. Activity Diagram for Staff
![StaffActDia](https://github.com/manuelclavel/teamepe2020/blob/flexmng/Images/Diagrams/Activity%20Diagram/Staff%20Application.jpg)  
***
### 4.3. Presentation
#### Select Customer/Staff
![Select Customer/Staff](https://github.com/manuelclavel/teamepe2020/blob/flexmng/Images/Diagrams/Mock-ups/NewUI/0.%20Select%20Customer_Staff.png)  
***
#### Custotmer Main Screen
![Custotmer Main Screen](https://github.com/manuelclavel/teamepe2020/blob/flexmng/Images/Diagrams/Mock-ups/NewUI/1.%20Customer%20Main%20Screen.png)  
***
#### Select City
![Select City](https://github.com/manuelclavel/teamepe2020/blob/flexmng/Images/Diagrams/Mock-ups/NewUI/1.1.%20Select%20City.png)  
***
#### Select Date
![Select Date](https://github.com/manuelclavel/teamepe2020/blob/flexmng/Images/Diagrams/Mock-ups/NewUI/1.2.%20Select%20Date.png) 
***
#### Show Available Slots
![Show Available Slots](https://github.com/manuelclavel/teamepe2020/blob/flexmng/Images/Diagrams/Mock-ups/NewUI/1.3.%20Show%20Available%20Slots.png)  
***
#### Slot Selected
![Slot Selected](https://github.com/manuelclavel/teamepe2020/blob/flexmng/Images/Diagrams/Mock-ups/NewUI/1.3.1.%20Slot%20Selected.png)  
***
#### Create Booking
![Create Booking](https://github.com/manuelclavel/teamepe2020/blob/flexmng/Images/Diagrams/Mock-ups/NewUI/1.3.1.1.%20Create%20Booking.png)  
***
#### Choose Start_End Time
![Choose Start_End Time](https://github.com/manuelclavel/teamepe2020/blob/flexmng/Images/Diagrams/Mock-ups/NewUI/1.3.1.1.1.%20Choose%20Start_End%20Time.png)  
***
#### Booking Created
![Booking Created](https://github.com/manuelclavel/teamepe2020/blob/flexmng/Images/Diagrams/Mock-ups/NewUI/1.3.1.2.%20Booking%20Created.png)  
***
#### Show My Bookings
![Show My Bookings](https://github.com/manuelclavel/teamepe2020/blob/flexmng/Images/Diagrams/Mock-ups/NewUI/1.4.%20Show%20My%20Bookings.png)  
***
#### Booking Details 
![Booking Details](https://github.com/manuelclavel/teamepe2020/blob/flexmng/Images/Diagrams/Mock-ups/NewUI/1.4.1.%20Booking%20Details.png)  
***
#### Cancel Alert 
![Cancel Alert ](https://github.com/manuelclavel/teamepe2020/blob/flexmng/Images/Diagrams/Mock-ups/NewUI/1.4.1.2.%20Cancel%20Alert.png)  
***
#### Cancel Confirmed
![Cancel Confirmed](https://github.com/manuelclavel/teamepe2020/blob/flexmng/Images/Diagrams/Mock-ups/NewUI/1.4.1.3.%20Cancel%20Confirmed.png)  
***

## 5. Technical Description  
* Base on the design, analysis and requirement, what we need is definitely the characteristics of the three-tier architecture. As done in the three-tier architecture, we divided the workload into three tasks, each task is reponsible for 1 tier, and each tier will be described in more detail below.  
### 5.1. Data Tier
* The database is composed of three parts: schema initialization, store procedures and data.  
  **Schema initialization:**  
      - This part is to initialize the basic schema for the database which include tables: city, center, court, 
      staff, player, booking  
      
        - Table city: 
          - city_id(primary key)  
          
        - Table center:  
          - center_id(primary key)  
          - city_id(foreign key)  
          
        - Table court:  
          - court_id(primary key)  
          - center_id(foreign key)  
          - city_id(foreign key)  
          
        - Table player:  
          - player_id(primary key)  
          
        - Table staff:  
          - staff_id(primary key)  
          - center_id(foreign key)  
          - city_id(foreign key)  
          
        - Table booking:  
          - booking_id(primary key)  
          - date  
          - startTime
          - endTime  
          - court_id(foreign key)  
          - center_id(foreign key)  
          - city_id(foreign key)  

  **Store procedures:**  
    - To insert and get data from the database, we have created store procedures which are:  
      - **createBooking:** this procedure is used to create a booking. The parameters are date, startTime, endTime, city_id, center_id, court_id, player_id. The inputs must be valid, startTime must be before endTime, all the booking must be satisfied all constraints, no overlap booking is allowed.   
      - **cancelBooking:** this procedure is used to cancel a booking. The parameters are booking_id, player_id. The inputs must be valid and satisfied all contraints.  
      - **getCenterBooking:** this procedure is used to get all the bookings of the center in the chosen city. The parameters are center_id and pdate. The inputs must be valid and satisfied all contraints.  
      - **getCenterCourt:** this procedure is used to get all the coursts of the center in the chosen city. The parameter is center_id and it must be valid and satisfied the constraints.  
      - **getCities:** this procedure is used to get all the cities in the database. The parameter is city_id and it must be validd and satisfied the constraints.  
      - **getCityBooking:** this procedure is uesd to get the bookings in the chosen city. The parameters are city_id and pdate. This inputs must be valid and satisfied the constraints.  
      - **getCityCenter:** this procedure is used to get the center of the chosen city. The parameter is city_id and it must be valid and satisfied the contraints.  
      - **getCourtBooking:** this procedure is used to get all the bookings of the court. The parameters are court_id and pdate. This inputs must be valid and satisfied the constraints.  
      - **getPlayerBooking:** this procedure is used to get all the bookings of the player. The parameters are player_id, city_id and pdate. This inputs must be valid anf satisfied the constraints.  
      
### 5.2. Logic Tier
* The logic tier consists of algorithm, server and method.  
  **Algorithm:**  
    -This part is used to calculate if the start time of booking is larger than the start time of slot about 45 minutes  
  ```  
    getCourtSlots(array `bookings`)  
    Initialize `slots` = [[7:00, 21:00]]  

    For each `b` in `bookings`:  
        `s` is the last item in `slots`  
        If `s_end - b_end ≥ 45 minutes`:  
            Insert [b_end, s_end] into `slots`  
        If `(b_start - s_start) ≥ 45 minutes`:  
            s_end = b_start  
        Else delete `s` from slots  
    Return slots  
  ```  
  
  **Server:**  
    -This part is used to initialize the server of the application:
  
  **Class:**  
    -To handle the request from the user interface and send reponse to user interface, we have created classes which are:  
      - **getCitySlot:** this class is used to get the available slot in the chosen city. The inputs are `city_id` and `date`.  
      - **getCenterSlot:** this class is used to get the available slot in the chosen center. The inputs are `center_id` and `date`.  
      - **getCourtSlot:** this class is used to get the availble slot in the chosen court. The inputs are `court_id` and `date`.  
      - **SQLStatement:** this method is used to call the store procedures from the database.  
      - **GetHandler:** this method is used to get the request from client.  
      - **PostHandler:**  this method is used to response to client.
### 5.3. Presentation Tier  
* The presentation tier includes :  user interface, method.     
  **User Interface:**  
    -This part is used to initialize the user interface of the application:  
  
  **Class:**  
    -To send a request to a server, we have created classes which are:  
      - **postGetCitySlot:** used to call “getCitySlot” class of  logic tier in server. The queries are `city_id` and `date`.         
      - **postGetBooking:** used to call “getBooking” class of the server. The queries are  `date`, `city_id`, `booking_id`.   
      - **JSONObject (postCreateBooking):** this class is used to call a “createBooking” class of server. The queries are `pdate`, `pstarttime`, `pendtime`, `pcityid`, `pcenterid`, `pcourtid`, `pplayerid`.   
      - **JSONObject (postCancelBooking):** this class is used to call a “cancelBooking” class of server. The queries are pplayerid, pcourtid  

## 6. Testing
* Below is the an assessment of how well the Testing is performed and a summary of test activities and final test results  
* The tables below are the final result for testing in which:
  - Test Case ID is the identical number for each test.  
  - Test Scenario is the scenario which have to be create before doing test.  
  - Test Data is the actual data which needs to be tested.    
  - Expected Results are the the result which is expected to be.  
  - Actual Results are the actual result which is returned after running test.  
  - Pass/Fail is the final conclusion for the test in which _"Pass"_ is when Expected Results matched Actual Results
  and _"Fail"_ is when Expected Results did not match Actual Results.  
### 6.1. Database and Store Procedures Test
* This is the first most test which is used to test store procedures of database. Overall, the each test will follow three steps: 
  - Clear up the database to ensure the database is empty when doing the test.  
  - Run SQL statements in "Scenario" to make scenario for the test.  
  - Every test has n SQL statements, the (n - 1) first SQL statements are the ADDITIONAL SCENARIO for the test.  
  - The last SQL statements is the actual test.  

### _createBooking_   
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
000 | Check ```createBooking``` with valid data | date, startTime, endTime, cityId, centerId, courtId, playerId | insert new booking in to data | As Expected | Pass
001 | Check ```createBooking``` with valid data but cityId is not existed | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-001 | As Expected | Pass
002 | Check ```createBooking``` with valid data but centerId is not existed | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-002 | As Expected | Pass
003 | Check ```createBooking``` with valid data but courtId is not existed | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-003 | As Expected | Pass
005 | Check ```createBooking``` with valid data but startTime < DATE(NOW()) | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-005 | As Expected | Pass
006 | Check ```createBooking``` with valid data but startTime < openTime | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-006 | As Expected | Pass
007 | Check ```createBooking``` with valid data but endTime > closeTime | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-007 | As Expected | Pass
008 | Check ```createBooking``` with valid data but endTime < startTime | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-008 | As Expected | Pass
009 | Check ```createBooking``` with valid data but playtime invalid (valid: 45m, 1h, 1h15m, 1h30m) | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-009 | As Expected | Pass
010 | Check ```createBooking``` with valid data but booking time is overlapped | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-010 | As Expected | Pass
011 | Check ```createBooking``` with valid data and pending booking | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-011 | As Expected | Pass
***
### _cancelBooking_  
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
012 | Check ```cancelBooking``` with valid data | bookingId, playerId | remove booking from database | As Expected | Pass
013 | Check ```cancelBooking``` with valid data but bookingId is not existed | bookingId, playerId | error code: CA-003 | As Expected | Pass
014 | Check ```cancelBooking``` with valid data but playerId does not own the bookingId | bookingId, playerId | error code:CA-004 | As Expected | Pass
015 | Check ```cancelBooking``` with valid data but  violating 24 hours before start time | bookingId, playerId | error code: CA-005 | As Expected | Pass
***

### _getCities_  
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
015 | Check ```getCities```  |  | return table city | As Expected | Pass
***  

### _getCityCenters_  
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
016 | Check ```getCityCenters``` with valid data | cityId | return table center | As Expected | Pass
017 | Check ```getCityCenters``` with invalid data | cityId | error code: GCC-000 | As Expected | Pass
018 | Check ```getCityCenters``` with valid data but cityId is not existed | cityId | error code: GCC-001 | As Expected | Pass
***

### _getCityCenterCourts_
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
019 | Check ```getCityCenterCourts``` with valid data | cityId, centerId | return table court | As Expected | Pass
020 | Check ```getCityCenterCourts``` with invalid cityId | cityId, centerId | error code: GCCC-000 | As Expected | Pass
021 | Check ```getCityCenterCourts``` with invalid centerId | cityId, centerId | error code: GCCC-001 | As Expected | Pass
022 | Check ```getCityCenterCourts``` with valid data but cityId is not existed | cityId, centerId | error code: GCCC-002 | As Expected | Pass
023 | Check ```getCityCenterCourts``` with valid data but centerId is not existed | cityId, centerId | error code: GCCC-003 | As Expected | Pass
***

### _getStaffs_
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
024 | Check ```getStaffs```  |  | return table staff | As Expected | Pass
***

### _getCenterStaffs_
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
025 | Check ```getCenterStaffs``` with valid data | centerId | return table staff | As Expected | Pass
026 | Check ```getCenterStaffs``` with invalid centerId | centerId | error code: GCS-000 | As Expected | Pass
027 | Check ```getCenterStaffs``` with valid data but centerId is not existed | centerId | error code: GCS-001 | As Expected | Pass
***

### _GetPlayerBookings_
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
028 | Check ```getPlayerBookings``` with valid data | playerId | return table player booking | As Expected | Pass
029 | Check ```getPlayerBookings``` with invalid playerId | playerId | GPB-000 | As Expected | Pass
030 | Check ```getPlayerBookings``` with valid data but playerId is not existed | playerId | GPB-001 | As Expected | Pass
***

### _GetCenterBookings_
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
031 | Check ```getPlayerBookings``` with valid data | centerId | return table player booking | As Expected | Pass
032 | Check ```getPlayerBookings``` with invalid playerId | centerId | GCB-000 | As Expected | Pass
030 | Check ```getPlayerBookings``` with valid data but centerId is not existed | centerId | GCB-001 | As Expected | Pass
***

### 6.2. Data-Logic Unit Test
* The purpose of this second test is used to test the connection between Database and Logic tier. The main activity of this test is 
to test the store procedures again but from the Logic Tier. In this test, we use Junit for automatic testing and the SQLStatement method which is declared at 5.2. to create scenario and call the store procedure. Furthermore, most of data in this test is resused from Store Procedures Test.  

Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
032 | Check ```createBooking_Success``` with valid data | centerId | return 200 | As Expected | Pass
033 | Check ```createBooking_WhencityIdIsNotexisted``` with scenario butcityId is not existed | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-001 | As Expected | Pass
034 | Check ```createBooking_WhencenterIdIsNotExisted``` with scenario but centerId is not existed | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-002 | As Expected | Pass
035 | Check ```createBooking_WhenCourtIdIsNotExisted``` with scenario but courtId is not existed | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-003 | As Expected | Pass
036 | Check ```createBooking_WhenStartTimeLessThanDateNow``` with scenario but startTime < DATE(NOW()) | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-005 | As Expected | Pass
037 | Check ```createBooking_WhenStartTimeLessThanOpenTime``` with scenario but startTime < openTime | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-006 | As Expected | Pass
038 | Check ```createBooking_WhenEndTimeLongerThanCloseTime``` with scenario but endTime > closeTime | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-007 | As Expected | Pass
039 | Check ```createBooking_WhenEndTimeLessThanStartTime``` with scenario but endTime < startTime | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-008 | As Expected | Pass
040 | Check ```createBooking_WhenPlayTimeIsInvalid``` with scenario but playtime invalid (valid: 45m, 1h, 1h15m, 1h30m) | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-009 | As Expected | Pass
041 | Check ```createBooking_WhenOverlappedWithOtherBookings``` with scenario but booking time is overlapped | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-010 | As Expected | Pass
042 | Check ```createBooking_WhenPlayerIdHavePendingBooking``` with scenario when player has pending booking | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-011 | As Expected | Pass
043 | Check ```createBooking_WhenPlayerIdHaveNoMoreThan3Booking``` with scenario when player has 3 bookings | date, startTime, endTime, cityId, centerId, courtId, playerId | error code: CB-012 | As Expected | Pass
044 | Check ```cancelBooking_Success``` with scenario | bookingId, playerId | remove booking from database | As Expected | Pass
045 | Check ```cancelBooking_WhenBookingIdIsNotExisted``` with scenario where bookingId is not existed | bookingId, playerId | error code: CA-003 | As Expected | Pass
046 | Check ```cancelBooking_WhenPlayerIdDoesNotOwnTheBooking``` with scenario when playerId does not own the bookingId | bookingId, playerId | error code: CA-004 | As Expected | Pass
047 | Check ```cancelBooking_WhenViolating24HoursBeforeStartTime``` with scenario when violating 24 hours before start time | bookingId, playerId | error code: CA-005 | As Expected | Pass
***

### 6.3. Logic Tier Unit Test
* This third test is to test the ```getAvailableSlot``` algorithm. In this test, we also resuse Junit and SQLStatement to run the test. We have three class to get available slot from the database:  
 - ```CitySlot```: get available slot of a specific city  
 - ```CenterSlot```: get available slot of a specific center  
 - ```CourtSlot```: get available slot of a specific court  
, and each class will have four different methods to retrieve the booking list from database:  
 - ```GivenDate```: retrieve bookings list on that date from database with default operation time.  
 - ```GivenDate_Arbitrary```: retrieve bookings list on that date from database with arbitrary operation time.  
 - ```GivenBooking```: retrieve the booking list of a specific city/center/court (they must be on the same day) 
 with default operation time.  
 - ```GivenBooking_Arbitrary```: retrieve the booking list of a specific city/center/court (they must be on the same day) 
 with arbitrary operation time.  
 The reason of those method is to reduce usage of database when user only need to retrieve the booking list from 
 a specific city/center/court.  
 
### _CenterSlotTest_
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
048 | Check ```Construct_GivenDate``` with scenario : date 2020-05-10, centerId: A1, time: 10:30:00-11:30:00, 12:31:00-13:31:00, court: A1C1, time: 13:31:00-14:31:00, court: A1C2, time: 08:31:00-09:31:00 | test data for city, center, court, booking table | available slot: court: A1C, time: 07:00:00-10:30:00, 11:30:00-12:31:00, 13:31:00-21:00:00, court: A1C1, time: 07:00:00-13:31:00, 14:31:00-21:00:00, court: A1C2, time: 07:00:00-08:31:00, 09:31:00-21:00:00 | As Expected | Pass
049 | Check ```Construct_GivenDate_Arbitrary``` with scenario : open time: 06:00:00, close time: 20:00:00, date 2020-05-10, centerId: A1, time: 10:30:00-11:30:00, 12:31:00-13:31:00, court: A1C1, time: 13:31:00-14:31:00, court: A1C2, time: 08:31:00-09:31:00 | test data for city, center, court, booking table | available slot: court: A1C, time: 06:00:00-10:30:00, 11:30:00-12:31:00, 13:31:00-20:00:00, court: A1C1, time: 06:00:00-13:31:00, 14:31:00-21:00:00, court: A1C2, time: 06:00:00-08:31:00, 09:31:00-20:00:00 | As Expected | Pass
050 | Check ```Construct_GivenBookings``` with scenario : date 2020-05-10, centerId: A1, time: 10:30:00-11:30:00, 12:31:00-13:31:00, court: A1C1, time: 13:31:00-14:31:00, court: A1C2, time: 08:31:00-09:31:00 | test data for city, center, court, booking table | return 200 | As Expected | Pass
051 | Check ```Construct_GivenBookings_Arbitrary``` with scenario : open time: 06:00:00, close time: 20:00:00, date 2020-05-10, centerId: A1, time: 10:30:00-11:30:00, 12:31:00-13:31:00, court: A1C1, time: 13:31:00-14:31:00, court: A1C2, time: 08:31:00-09:31:00 | test data for city, center, court, booking table | return 200 | As Expected | Pass
052 | Check ```Construct_Given_Date_EmptyBooking_Arbitrary``` with scenario : open time: 06:00:00, close time: 20:00:00, date 2020-05-10, centerId: A1, time: 10:30:00-11:30:00, 12:31:00-13:31:00, court: A1C1, time: 13:31:00-14:31:00, court: A1C2, time: 08:31:00-09:31:00 | test data for city, center, court, booking table | return empty booking list | As Expected | Pass
***

### _CitySlotTest_
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
053 | Check ```Construct_GivenDate``` with scenario : date 2020-05-10, cityId: A, time: 10:30:00-11:30:00, 12:31:00-13:31:00, court: A1C1, time: 13:31:00-14:31:00, court: A1C2, time: 08:31:00-09:31:00 | test data for city, center, court, booking table | available slot: court: A1C, time: 07:00:00-10:30:00, 11:30:00-12:31:00, 13:31:00-21:00:00, court: A1C1, time: 07:00:00-13:31:00, 14:31:00-21:00:00, court: A1C2, time: 07:00:00-08:31:00, 09:31:00-21:00:00 | As Expected | Pass
054 | Check ```Construct_GivenDate_Arbitrary``` with scenario : open time: 06:00:00, close time: 20:00:00, date 2020-05-10, cityId: A, time: 10:30:00-11:30:00, 12:31:00-13:31:00, court: A1C1, time: 13:31:00-14:31:00, court: A1C2, time: 08:31:00-09:31:00 | test data for city, center, court, booking table | available slot: court A1C, time: 06:00:00-10:30:00, 11:30:00-12:31:00, 13:31:00-20:00:00, court: A1C1, time: 06:00:00-13:31:00, 14:31:00-21:00:00, court: A1C2, time: 06:00:00-08:31:00, 09:31:00-20:00:00 | As Expected | Pass
***

### _CourtSlotTest_
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
055 | Check ```Construct_GivenDate``` with scenario : date 2020-05-10, courtId: A1C, time: 10:30:00-11:30:00, 12:31:00-13:31:00 | test data for city, center, court, booking table | available slot: court: A1C, time: 07:00:00-10:30:00, 11:30:00-12:31:00, 13:31:00-21:00:00 | As Expected | Pass
056 | Check ```Construct_GivenDate_EmptyBookingList``` with scenario : date 2020-05-10, courtId: A1C, no booking | test data for city, center, court, booking table | available slot: court: A1C, time: 07:00:00-21:00:00 | As Expected | Pass
057 | Check ```Construct_GivenDate_No_Slot``` with scenario : date 2020-05-10, courtId: A1C1, time: 07:00:00-21:00:00 | test data for city, center, court, booking table | no available slot | As Expected | Pass
058 | Check ```Construct_GivenDate_Continous_Bookings``` with scenario : date 2020-05-10, courtId: A1C, time: 13:31:00-16:00:00 | test data for city, center, court, booking table | available slot: court: A1C, time: 07:00:00-13:31:00, 16:00:00-21:00:00 | As Expected | Pass
059 | Check ```Construct_GivenDate_Given_Time_Constraint``` with scenario : date 2020-05-10, courtId: A1C, open time: 06:00:00, close time: 20:00:00, time: 13:31:00-14:31:00, 15:00:00-16:00:00 | test data for city, center, court, booking table | available slot: court: A1C, time: 06:00:00-13:31:00, 14:31:00-15:00:00, 16:00:00-20:00:00 | As Expected | Pass
060 | Check ```Construct_GivenBooking``` with scenario : date 2020-05-10, courtId: A1C, time: 10:30:00-11:30:00, 12:31:00-13:31:00 | test data for city, center, court, booking table | available slot: court: A1C, time: 07:00:00-10:30:00, 11:30:00-12:31:00, 13:31:00-21:00:00 | As Expected | Pass
061 | Check ```Construct_GivenBooking_EmptyBookingList``` with scenario : date 2020-05-10, courtId: A1C, no booking | test data for city, center, court, booking table | available slot: court: A1C, time: 07:00:00-21:00:00 | As Expected | Pass
062 | Check ```Construct_GivenBooking_No_Slot``` with scenario : date 2020-05-10, courtId: A1C1, time: 07:00:00-21:00:00 | test data for city, center, court, booking table | no available slot | As Expected | Pass
062 | Check ```Construct_GivenBooking_Continous_Bookings``` with scenario : date 2020-05-10, courtId: A1C, time: 13:31:00-16:00:00 | test data for city, center, court, booking table | available slot: court: A1C, time: 07:00:00-13:31:00, 16:00:00-21:00:00 | As Expected | Pass
063 | Check ```Construct_GivenDate_Given_Booking_Constraint``` with scenario : date 2020-05-10, courtId: A1C, open time: 06:00:00, close time: 20:00:00, time: 13:31:00-14:31:00, 15:00:00-16:00:00 | test data for city, center, court, booking table | available slot: court: A1C, time: 06:00:00-13:31:00, 14:31:00-15:00:00, 16:00:00-20:00:00 | As Expected | Pass
064 | Check ```Construct_GivenDate_BookingAtStartOfDay``` with scenario : date 2020-05-10, courtId: A1C, 07:00:00-07:45:00 | test data for city, center, court, booking table | available slot: court: A1C, time: 07:45:00-21:00:00 | As Expected | Pass
065 | Check ```Construct_GivenDate_BookingAtEndOfDay``` with scenario : date 2020-05-10, courtId: A1C, time: 20:15:00-21:00:00 | test data for city, center, court, booking table | available slot: court: A1C, time: 07:00:00-20:15:00 | As Expected | Pass
066 | Check ```Construct_GivenDate_BookingAtStartAndEndOfDay``` with scenario : date 2020-05-10, courtId: A1C, time: 07:00:00-07:45:00, 20:15:00-21:00:00 | test data for city, center, court, booking table | available slot: court: A1C, time: 07:45:00-20:15:00 | As Expected | Pass
067 | Check ```Construct_GivenDate_SlotAtStartOfDay``` with scenario : date 2020-05-10, courtId: A1C, 07:45:00-21:00:00 | test data for city, center, court, booking table | available slot: court: A1C, time: 07:00:00-07:45:00 | As Expected | Pass
068 | Check ```Construct_GivenDate_SlotAtEndOfDay``` with scenario : date 2020-05-10, courtId: A1C, 07:00:00-20:15:00 | test data for city, center, court, booking table | available slot: court: A1C, time: 20:15:00-21:00:00 | As Expected | Pass
069 | Check ```Construct_GivenDate_SlotAtStartAndEndOfDay``` with scenario : date 2020-05-10, courtId: A1C, time: 07:45:00-20:15:00 | test data for city, center, court, booking table | available slot: court: A1C, time: 07:00:00-07:45:00, 20:15:00-21:00:00 | As Expected | Pass
070 | Check ```Construct_GivenDate_IntervalSmallerThan45min``` with scenario : date 2020-05-10, courtId: A1C1, time: 07:30:00-21:00:00 | test data for city, center, court, booking table | no available slot | As Expected | Pass
***

### 6.4. Logic-Presentation Test
## 7. References  
Tool/Document | Link |   
------------- | ---- |  
Database      | https://www.w3schools.com/sql/ ; https://www.tutorialspoint.com/sql/index.htm |  
Java          | https://www.baeldung.com/ |  
MySQL Community Server 8.0.19 | https://dev.mysql.com/downloads/mysql/ |  
IntelliJ IDEA | https://www.jetbrains.com/idea/download/?gclid=Cj0KCQjw17n1BRDEARIsAFDHFex16EdMV2TvUukVPw--BpoCTIJ9Uif3RjiX9woX-HIN8bNU9_pi7fYaAgFVEALw_wcB#section=windows |  
Android Studio | https://developer.android.com/studio |  
### 7.1. Development Tools
### 7.2. Documentation
## 8. Appendix  
