# Homework 2
## Team E
### **Notes**
* unique identifiers known/agreed upon by presentation/client and 
logic/server: cityId, venueId, courtId, userId/playerId, bookingId, statusId.
* success/errorCode known/agreed upon by client and server
* ordering (if any), when response is a list/array, known/agreed upon by client and server
* structure (if any), when response contains (potentially) structured data,
known/agreed upon by client and server
### **Design/describe interfaces [client-server] Presentation-Logic**  

#### _getAvailableSlots_
* __Description__: for a given day and cityId, get all the slots available:
* __security/caller__: anonymous
* __request__: getSlots(day, cityId)
* __response__: 
  * __success__: successCode + array of (centreId, venueSlots), where
venueSlots is an array of (venueId, courtSlots), where
courtSlots is an array of (courtId, slots), where
slots is an array of (startHour, endHour)
  * __error__: errorCode [Homework #3]

#### _getPlayerBookings_
* __Description__: for a given day, city, and playerId, 
get all the  bookings:
* __security/caller__: callerId
* __request__: getPlayerBookings(day, cityId, playerId)
* __response__: 
  * __success__: successCode + array of (centreId, venueBookings), where
venueBookings is an array of (venueId, courtBookings), where
courtBookings is an array of (courtId, bookings), where
bookings is an array of (bookingId, startHour, endHour)
  * __error__: errorCode [Homework #3]

#### _getVenueBookings_
* __Description__: for a given day and venueId
get all the bookings:
* __security/caller__: callerId
* __request__: getVenueBookings(day, venueId)
* __response__: 
  * __success__: successCode + array of (courtId, bookings), where
bookings is an array of (bookingId, startHour, endHour)
  * __error__: errorCode [Homework #3]

#### _createBooking_
* __Description__: for a given day, court, start, end, and playerId, 
create a new booking.
* __security/caller__: callerId
* __request__: createBooking(day, courtId, start, end, playerId)
* __response__: 
  * __success__: successCode
  * __error__: errorCode [Homework #3]

#### _cancelBooking_
* __Description__: for a given bookingId, cancel the booking
* __security/caller__: callerId
* __request__: cancelBooking(bookingId)
* __response__: 
  * __success__: successCode
  * __error__: errorCode [Homework #3]

#### _getBookingInfo_
* __Description__: for a given bookingId, get all the booking's
information: cityId, venueId, courtId, day, start, end, playerId,
statusId.
* __security/caller__: callerId
* __request__: getBookingInfo(bookingId)
* __response__: 
  * __success__: successCode
  * __error__: errorCode [Homework #3]

#### _updateBookingPaymentStatus_
* __Description__: for a given bookingId, update the booking's 
payment status
* __security/caller__: callerId
* __request__: updateBookingPaymentStatus(bookingId, statusId)
* __response__: 
  * __success__: successCode
  * __error__: errorCode [Homework #3]

#### _getNameCity_/_getNameVenue_/_getNameCourt_/_getNameUser_
* Description: for a given cityId/venueId/courtId/userId,
get the corresponding name (to display)
* security/caller: callerId
* request: getNameCity(cityId)/getNameVenue(venueId)/getNameCourt(courtId)/getNameUser(userId)
* response: 
  * success: successCode + name of the cityId/venueId/courtId/userId
  * error: errorCode [Homework #3]

### **Design/describe interfaces [server-database] Logic-Data**  
### Notes
* unique identifiers known/agreed upon by presentation/client and 
logic/server: cityId, venueId, courtId, userId/playerId, bookingId, statusId.
* success/errorCode known/agreed upon by client and server
* ordering (if any), when response is a list/array, known/agreed upon by client and server
* structure (if any), when response contains (potentially) structured data,
known/agreed upon by client and server

#### _getVenues_
* __Description__: for a given cityId, get all the venues of the city:
* __security/caller__: anonymous
* __request__: getSlots(day, cityId)
* __response__: 
  * __success__: successCode + array of (centreId, venueSlots), where
venueSlots is an array of (venueId, courtSlots), where
courtSlots is an array of (courtId, slots), where
slots is an array of (startHour, endHour)
  * __error__: errorCode [Homework #3]

#### _getVenueBookings_
* __Description__: for a given day and venueId
get all the bookings:
* __security/caller__: callerId
* __request__: getVenueBookings(day, venueId)
* __response__: 
  * __success__: successCode + array of (courtId, bookings), where
bookings is an array of (bookingId, startHour, endHour)
  * __error__: errorCode [Homework #3]

#### _getPlayerBookings_
* __Description__: for a given day, city, and playerId, 
get all the  bookings:
* __security/caller__: callerId
* __request__: getPlayerBookings(day, cityId, playerId)
* __response__: 
  * __success__: successCode + array of (centreId, venueBookings), where
venueBookings is an array of (venueId, courtBookings), where
courtBookings is an array of (courtId, bookings), where
bookings is an array of (bookingId, startHour, endHour)
  * __error__: errorCode [Homework #3]

_The above 2 functions are used to calculate slots of a city. By all bookings of a court, we can calculate available slots for a court. Then for all courts of a city_
#### _createBooking_
* __Description__: for a given day, court, start, end, and playerId, 
create a new booking.
* __security/caller__: callerId
* __request__: createBooking(day, courtId, start, end, playerId)
* __response__: 
  * __success__: successCode
  * __error__: errorCode [Homework #3]

#### _cancelBooking_
* __Description__: for a given bookingId, cancel the booking
* __security/caller__: callerId
* __request__: cancelBooking(bookingId)
* __response__: 
  * __success__: successCode
  * __error__: errorCode [Homework #3]

#### _getBookingInfo_
* __Description__: for a given bookingId, get all the booking's
information: cityId, venueId, courtId, day, start, end, playerId,
statusId.
* __security/caller__: callerId
* __request__: getBookingInfo(bookingId)
* __response__: 
  * __success__: successCode
  * __error__: errorCode [Homework #3]

#### _updateBookingPaymentStatus_
* __Description__: for a given bookingId, update the booking's 
payment status
* __security/caller__: callerId
* __request__: updateBookingPaymentStatus(bookingId, statusId)
* __response__: 
  * __success__: successCode
  * __error__: errorCode [Homework #3]

#### _getNameCity_/_getNameVenue_/_getNameCourt_/_getNameUser_
* Description: for a given cityId/venueId/courtId/userId,
get the corresponding name (to display)
* security/caller: callerId
* request: getNameCity(cityId)/getNameVenue(venueId)/getNameCourt(courtId)/getNameUser(userId)
* response: 
  * success: successCode + name of the cityId/venueId/courtId/userId
  * error: errorCode [Homework #3]

### Design database (Entity-Relationship Diagram)  
![ERDiagram](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Entity-Relationship%20Diagram/ER-Diagram%20for%20project.PNG)  
### Design UI (Activity diagram + mockups)  
#### Activity diagram  
**1. User**  
![UserActivity](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Activity%20Diagram/User.jpg)  


**2. Staff**  
![StaffActivity](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Activity%20Diagram/Staff.jpg)  
#### Mockups  
**1. User**  
* 1.1. Create a booking  
![CreateAnAccount](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/User/Create%20a%20booking.png)  
***

* 1.2. Login  
![Login](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/User/Log-in.png)  
***

* 1.3. List Of Place  
![ListOfPlace](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/User/List%20of%20place.png)  
***

* 1.4. User Account  
![UserAccount](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/User/User%20account.png)  
***

* 1.5. Booking Information  
![BookingInformation](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/User/Booking%20information.png)  
***

* 1.6. My Booking  
![MyBooking](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/User/My%20booking.png)  
***

**2. Staff**
* 2.1. Login - Staff  
![Login](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/Staff/Login%20-%20Staff.png)  
***

* 2.2. All Booking  
![AllBooking](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/Staff/All%20bookings.png)  
***

* 2.3. Booking Information  
![BookingInformation](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/Staff/Booking%20information.png)  
***

* 2.4. Find Names  
![FindNames](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/Staff/Find%20names.png)  
***

* 2.5. Staff Account  
![StaffAccount](https://github.com/manuelclavel/teamepe2020/blob/master/Images/Diagrams/Mock-ups/Staff/Staff%20Account.png)  
***
