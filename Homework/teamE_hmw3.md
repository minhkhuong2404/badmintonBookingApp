# Homework 3 #  
## Team E ##  
### Notes  
* unique identifiers known/agreed upon by presentation/client and logic/server: cityId, venueId, courtId,
userId/playerId, bookingId, statusId.  
* success/errorCode known/agreed upon by client and server  
* ordering (if any), when response is a list/array, known/agreed upon by client and server  
* structure (if any), when response contains (potentially) structured data,
known/agreed upon by client and server  

### _getAvailableSlots_  
* __Description__: for a given day and cityId, get all the slots available:  
* __security/caller__: anonymous  
* __request__: getSlots(day, cityId)  
* __response__:  
  * __success__: successCode + array of (centreId, venueSlots), where
venueSlots is an array of (venueId, courtSlots), where
courtSlots is an array of (courtId, slots), where
slots is an array of (startHour, endHour)  
  * __error__: errorCode [Homework #3]  

### _getPlayerBookings_  
* __Description__: for a given day, city, and playerId, 
get all the  bookings:  
* __security/caller__: callerId  
* __request__: getPlayerBookings(day, cityId, playerId)  
* __response__:  
  * __success__: successCode + array of (centreId, venueBookings), where
venueBookings is an array of (venueId, courtBookings), where
courtBookings is an array of (courtId, bookings), where
bookings is an array of (startHour, endHour)  
  * __error__: errorCode [Homework #3]  

### _getVenueBookings_  
* __Description__: for a given day and venueId
get all the bookings:  
* __security/caller__: callerId  
* __request__: getVenueBookings(day, venueId)  
* __response__:  
  * __success__: successCode + array of (courtId, bookings), where
bookings is an array of (startHour, endHour)  
  * __error__: errorCode [Homework #3]  

### _createBooking_  
* __Description__: for a given day, court, start, end, and playerId, 
create a new booking.  
* __security/caller__: callerId  
* __request__: createBooking(day, courtId, start, end, playerId)  
* __response__:  
  * __success__: successCode  
  * __error__: errorCode [Homework #3]  

### _cancelBooking_  
* __Description__: for a given bookingId, cancel the booking  
* __security/caller__: callerId  
* __request__: cancelBooking(bookingId)  
* __response__:  
  * __success__: successCode  
  * __error__: errorCode [Homework #3]  

### _getBookingInfo_  
* __Description__: for a given bookingId, get all the booking's
information: cityId, venueId, courtId, day, start, end, playerId,
statusId.  
* __security/caller__: callerId  
* __request__: getBookingInfo(bookingId)  
* __response__:  
  * __success__: successCode  
  * __error__: errorCode [Homework #3]  

### _updateBookingPaymentStatus_  
* __Description__: for a given bookingId, update the booking's 
payment status  
* __security/caller__: callerId  
* __request__: updateBookingPaymentStatus(bookingId, statusId)  
* __response__:  
  * __success__: successCode  
  * __error__: errorCode [Homework #3]  

### _getNameCity_/_getNameVenue_/_getNameCourt_/_getNameUser_ 
* Description: for a given cityId/venueId/courtId/userId,
get the corresponding name (to display)  
* security/caller: callerId  
* request: getNameCity(cityId)/getNameVenue(venueId)/getNameCourt(courtId)/getNameUser(userId)  
* response:  
  * success: successCode + name of the cityId/venueId/courtId/userId  
  * error: errorCode [Homework #3]  

```### _getAvailableSlots_  
test case:  
- Input date and city: server returns list of available slots  
- No response: User chooses date and city but there is no result return from the server  
- No slot avaible when 2 users are booking at the same time with the interval time intercept each other: server turns 'no slot available', although the user can find it at the first time  
- Not enough interval time available for the user's need(Ex: slot from 9am-10am, but user wants to 
play from 9am-10:30am): server return 'no slot available'  

### _getPlayerBookings_  
test case:  
- callerId, Input date, city, PlayerId: server returns list of all player bookings 
(courtID, startHour, endHour, venueID, centreID)  
- callerId, wrong Input data/City/PlayerId: server returns PlayerBooking on wrong date/city/player 
or 'no result is found'  
- wrong/no callerId, Input date, City, PlayerID: server returns 'no result is found'  
- different between the callerId on the client and the request callerId: server returns 'action is 
not allowed'  
- callerId, Input date, city, PlayerId: if server returns list of length > 3, returns 'no more booking 
is allowed'  
  
### _getVenueBooking_
test case:  
- callerId, Input date, venueId: server return the list of all bookings  
- callerId, wrong Input date/venueId: server return VenueBookings on wrong date/venue or 'no result is found'  
- wrong/no callerId (not staff), Input day, venueId: server returns 'no result is found'  

### _createBooking_  
test case:  
- callerId, Input date, courtId, start, end, playerId: server creates a booking  
- Two or more users input same date, courtId, start, end: server will accept the first confirm booking 
and refuse others with message 'Please select another date'  
- User inputs date, courtId, start, end which have been booked: server refuses booking 
with message 'Please select another date'  
- wrong/no callerId, Input date, courtId, start time, end time, playerId: server refuses creating booking
- callerId, Input date, courtId.count > 3, start, end, playerId: server deny that booking  

### _cancelBooking_
test case:
- callerId, Input bookingId, >24 hours before start time: cancelBooking success  
- callerId, Input bookingId, <24 hours before start time: refuse cancelBooking and return message 'It's too late, babe'  
- callerId, wrong (or other user) Input booking Id: server returns 'No result is found' and refuses cancelBooking  
- wrong/no callerId, Input booking Id: refuse cancelBooking  

### _getBookingInfo_  
test case:  
- callerId (for user), Input bookingId: server returns all booking's infomation: cityId, venueId, courtId, day, start, end, playerId, statusId for cancelling
- callerId (for staff), Input bookingId: server returns all booking's infomation: courtId, day, start, end, playerId, statusId for payment
- callerId, wrong (or cancelled) Input bookingId: server returns 'no result is found'  
- wrong/no callerId, Input booking Id: server returns 'no result is found'  

### _updateBookingPaymentStatus_  
test case:  
- callerId, Input bookingId: update the booking's payment status  
- callerId, wrong Input bookingId: server returns 'no result is found'
- wrong/no callerId (not staff), bookingId: server returns 'no result is found'  
- callerId, Input bookingId, statusId.change > 2: server returns 'try again later'  

### _getNameCity/getNameVenue/getNameCourt/getNameUser_  
test case:  
- for cityId/venueId/courtId/userId: display corresponding name  
- for cityId/venueId/courtId/userId: display cityId/venueId/courtId/userId  
- no callerId: server cannot return corresponding name  
- wrong cityId/venueId/courtId/userId: server returns 'no result is found'  
