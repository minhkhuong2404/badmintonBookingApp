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
  * Main concern of customer management system is customer related data. This data consists of individual information such as customer’s name, email, customer ID, telephone number, and order related information like booking amount. This module has a capability of identifying customers by the staff of the sport center. If the customer want to change the profile information, they can confirm this by editing account setting. The management module will also show the pending booking which the customer has not paid yet, improve the way of manage the booking list and available slot list.  
### 1.2. Software Process Model and Team Organization  
  * In order to complete our project in total of six weeks duration, what we should do exactly is following the requirement, analysis, design, and implementation and testing parts one by one and in each one of these parts returning the previous ones and do some modifications where necessary. we produced prototypes in some periods, and each prototype will have a certain part of the total project.  
  * Our project team is suitable for team organization. Decision on problems and approach are made by all of the group members. There is no hierarchy among the team members.

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
  * Staff is the user who can be also thought as the manager of the service. Also, the same as the customer, to be a member of the system, staff must have a Facebook account to login into the application.  
  * As a member of the system, s/he will have a right to access the booking list which shows all the slots reserved by the customers in his/her sport center. But first of all, the staff have to choose the date and the city so that they can click the “show bookings” button and then s/he can see the booking list of his/her sport center in that date and city.  

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
#### Staff Main Sceen 
![Staff Main Sceen ](https://github.com/manuelclavel/teamepe2020/blob/flexmng/Images/Diagrams/Mock-ups/NewUI/2.%20Staff%20Main%20Screen.png)  
***
#### Select Date
![Select Date](https://github.com/manuelclavel/teamepe2020/blob/flexmng/Images/Diagrams/Mock-ups/NewUI/2.1.%20Select%20Date.png)  
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
      - **createBooking:** this procedure is used to create a booking. The parameters are date, startTime, endTime, city_id,
      center_id, court_id, player_id. The input must be valid, startTime must be before endTime, all the booking must be satisfied all constraints, no overlap booking is allowed.  
      - **cancelBooking:** this procedure is used to cancel a booking. The parameter are booking_id, player_id. The input must be valid and satisfied all contraints.
### 5.2. Logic Tier
* The logic tier consists of algorithm, server and class.  
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
      - **getCitySlot:** this class is used to get the availble slot in the chosen city. The queries are city_id and date  
      - **getBooking:** used to get the information of the customer's booking from the database. The queries are date, city_id, booking_id   
      - **createBooking:** used to insert new data about the booking into the database  
      - **cancelBooking:** used to remove the chosen booking's data in the database  
### 5.3. Presentation Tier  
* The presentation tier includes :  user interface, class.     
  **User Interface:**  
    -This part is used to initialize the user interface og the application:  
  
  **Class:**  
    - To send a request to a server, we have created classes which are  
      - **postGetCitySlot:** used to call “getCitySlot” class of  logic tier in server. The queries are city_id and date         
      - **postGetBooking:** used to call “getBooking” class of the server. The queries are  date, city_id, booking_id   
      - **JSONObject (postCreateBooking):** this class is used to call a “createBooking” class of server. The queries are pdate, pstarttime, pendtime, pcityid, pcenterid, pcourtid, pplayerid   
      - **JSONObject (postCancelBooking):** this class is used to call a “cancelBooking” class of server. The queries are pplayerid, pcourtid  

## 6. Testing
### 6.1. Database and Store Procedures Test
### 6.2. Data-Logic Unit Test
### 6.3. Logic Tier Unit Test
### 6.4. Logic-Presentation Test
## 7. References  
## 8. Appendix  
