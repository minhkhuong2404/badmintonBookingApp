# Homework 2 #  
## Team E ##  
### Design/describe interfaces [client-server] Presentation-Logic ###  
**GENERAL:**  
View booking. ```view_booking(user_id/staff_id)```  
View account information. view_acc(user_id/staff_id)  
**USER:**  
View all courts. view_court()  
View all courts in a sportcenter. view_court(sportcenter_id)  
View all sportcenter. view_ sportcenter()  
**STAFF:**  
View booking info. view_booking_info(booking_id)  
View pending booking. view_booking(staff_id, payment_stat)  
### Design/describe interfaces [client-server] Logic-Data ###  
**GENERAL:**  
Get all courts in the city for user to choose. get_all_court()  
Get all courts in the choosen sport center. get_court_sport(sportcenter_id)  
Get all courts with a given date. get_court_date(date)  
Get all courts with a given date in the choosen sport. get_court(sportcenter_id,date)  
Get credential information for logging. get_cred(user_id)  
**STAFF:**  
Get courts which are managed by a staff get_court(staff_id)  
Change booking status (pending or paid).  payment_update(court_id)  
**USER:**  
Get bookings which are booked by a user (included pending, paid and cancelled bookings). get_booking(user_id)  
Create new booking with user_id, court, start time, end time. create_booking(user_id, court_id, start, end)  
Delete booking with booking_id. delete_booking(booking_id)  
### Design database (Entity-Relationship Diagram) ###  
![ERDiagram](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Entity-Relationship%20Diagram/ER-Diagram%20for%20project.PNG)  
### Design UI (Activity diagram + mockups) ###  
#### Activity diagram ####  
**1. User**  
![UserActivity](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Activity%20Diagram/User.jpg)  


**2. Staff**  
![StaffActivity](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Activity%20Diagram/Staff.jpg)  
#### Mockups ####  
**1. User**  
* 1.1. Create an account  
![CreateAnAccount](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/User/Create%20an%20account.png)  
***

* 1.2. Login  
![Login](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/User/Log-in.png)  
***

* 1.3. List of place  
![ListOfPlace](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/User/List%20of%20place.png)  
***

* 1.4. Dashboard  
![Dashboard](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/User/Dashboard.png)  
***

* 1.5. Booking  
![Booking](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/User/Booking.png)  
***

* 1.6. Booking Time  
![Bookingtime](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/User/Booking%20time.png)  
***

* 1.7. Find Location  
![FindLocation](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/User/Find%20location.png) 
***

* 1.8. Notification Center  
![NotificationCenter](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/User/Notification%20center.png)  
***

* 1.9. Upcomming booking  
![UpcomingBooking](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/User/Upcoming%20booking_2.png) 
***

* 1.10. Turn on notification  
![TurnOnNotification](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/User/Turn%20on%20notification.png)  
***

* 1.11. My Account  
![MyAccount](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/User/My%20account.png)  
***

**2. Staff**
* 2.1. Login  
![Login](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/Staff/Login%20-%20Staff.png)  
***

* 2.2. Upcomming Booking  
![UpcommingBooking](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/Staff/Upcoming%20booking.png)  
***

* 2.3. Pending Booking  
![PendingBooking](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/Staff/Pending%20bookings.png)  
***

* 2.4. Paid Booking  
![Paid Booking](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/Staff/Paid%20bookings.png)  
***

* 2.5. Reminders  
![Reminders](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/Staff/Reminders.png)  
***

* 2.6. Staff Account  
![StaffAccount](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/Staff/Staff%20Account.png)  
***
