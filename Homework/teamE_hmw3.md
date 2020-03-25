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


### _getAvailableSlots_   
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
TU01 | Check ```getAvailableSlots``` with valid data | date, city | server returns list of available slots | As Expected | Pass
TU02 | Check ```getAvailableSlots``` with invalid data | date, city | server returns 'no result' | As Expected | Pass
TU03 | Check ```getAvailableSlots``` when 2 users are booking at the same time with the interval time intercept each other | date, city | server returns 'no slot available', although the user can find it at the first time | As Expected | Pass
TU04 | Check ```getAvailableSlots``` when there is not enough interval time available for the user's need(Eg: slot from 9am-10am, but user wants to play from 9am-10:30am) | date, city | server returns 'no slot available' | As Expected | Pass

test case:  
Input valid date and city: server returns list of available slots  
Input invalid date: server returns list of available slots on 
No response: User chooses date and city but there is no result return from the server  

### _getPlayerBookings_  
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
TU05 | Check ```getPlayerBooking``` with valid data and callerId | date, city, playerId, callerId | server returns list of all player bookings | As Expected | Pass
TU06 | Check ```getPlayerBooking``` with invalid data and callerId |date, callerId | server returns list of all player bookings with wrong date or 'no result' | As Expected | Pass
TU07 | Check ```getPlayerBooking``` with invalid data and callerId | city, callerId | server returns list of all player bookings with wrong city or 'no results' | As Expected | Pass
TU08 | Check ```getPlayerBooking``` with invalid data and callerId | playerId, callerId | server returns list of all player bookings with wrong player or 'no results' | As Expected | Pass
TU09 | Check ```getPlayerBooking``` with valid data but the callerId on the client and the request callerId are different | date, city, playerId, callerId | server returns 'action is not allowed' | As Expected | Pass

test case:  
- callerId, Input date, city, PlayerId: server returns list of all player bookings 
(courtID, startHour, endHour, venueID, centreID)  
- callerId, wrong Input data/City/PlayerId: server returns PlayerBooking on wrong date/city/player 
or 'no result is found'  
- wrong/no callerId, Input date, City, PlayerID: server returns 'no result is found'  
  
### _getVenueBooking_  
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
TU10 | Check ```getVenueBooking``` with valid data and callerId | date, venueId, callerId | server returns list of all bookings | As Expected | Pass
TU11 | Check ```getVenueBooking``` with invalid data and callerId | date, callerId | server returns list of all bookings on wrong date or 'no result' | As Expected | Pass
TU12 | Check ```getVenueBooking``` with invalid data and callerId | venueId, callerId | server returns list of all bookings on wrong venue or 'no result' | As Expected | Pass
TU13 | Check ```getVenueBooking``` with valid data but the callerId on the client and the request callerId are different | date, venueId, callerId | server returns 'action is not allowed' | As Expected | Pass


test case:  
- callerId, Input date, venueId: server return the list of all bookings  
- callerId, wrong Input day/venueId: server return VenueBookings on wrong date/venue or 'no result is found'  
- wrong/no callerId, Input day, venueId: server returns 'no result is found'  

### _createBooking_  
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
TU14 | Check ```createBooking``` with valid data and callerId | date, courtId, start, end, playerId, callerId | server create a bookings | As Expected | Pass
TU15 | Check ```createBooking``` with invalid data and callerId | date/courtId/start/end (same with existed booking) | server refuses bookings | As Expected | Pass
TU16 | Check ```createBooking``` with valid data but the callerId on the client and the request callerId are different | date, courtId, start, end, playerId, callerId | server refuses bookings and returns 'Action is now allowed | As Expected | Pass
TU17 | Check ```createBooking``` with valid data and callerId and if server returns courtId.count > 3| date, courtId, start, end, playerId, callerId | server refuses bookings and return 'no more booking is allowed | As Expected | Pass

test case:  
- callerId, Input date, courtId, start, end, playerId: server creates a booking  
- Two or more users input same date, courtId, start, end: server will accept the first confirm booking 
and refuse others with message 'Please select another date'  
- User inputs date, courtId, start, end which have been booked: server refuses booking 
with message 'Please select another date'  
- wrong/no callerId, Input date, courtId, start time, end time, playerId: server refuses creating booking

### _cancelBooking_
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
TU18 | Check ```cancelBooking``` with valid data and callerId and > 24 hours before start time| bookingId, callerId | server cancels booking | As Expected | Pass
TU19 | Check ```cancelBooking``` with valid data and callerId and < 24 hours before start time| bookingId, callerId | server refuses to cancel booking and returns message 'Fail to cancel' | As Expected | Pass
TU20 | Check ```cancelBooking``` with invalid data and callerId | bookingId, callerId | server refuses to cancel booking and returns 'Booking Id does not exist | As Expected | Pass
TU21 | Check ```cancelBooking``` with valid data but the callerId on the client and the request callerId are different | bookingId, callerId | server refuses to cancel booking and return 'Action is not allowed | As Expected | Pass


test case:
- callerId, Input bookingId, >24 hours before start time: cancelBooking success  
- callerId, Input bookingId, <24 hours before start time: refuse cancelBooking and return message 'It's too late, babe'  
- callerId, wrong Input booking Id, ><24 before start time: server returns 'No result is found' and refuses cancelBooking  
- wrong/no callerId, Input booking Id, ><24 before start time: refuse cancelBooking  

### _getBookingInfo_  
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
TU22 | Check ```getBookingInfo``` with valid data and callerId | bookingId, callerId | server returns cityId, venueId, courtId, day, start, end, playerId, statusId | As Expected | Pass
TU20 | Check ```getBookingInfo``` with invalid data and callerId | bookingId, callerId | server returns 'no result' | As Expected | Pass
TU23 | Check ```getBookingInfo``` with valid data but the callerId on the client and the request callerId are different | bookingId, callerId | server returns 'Action is not allowed' | As Expected | Pass

test case:  
- callerId, Input bookingId: server returns all booking's infomation: cityId, venueId, courtId, day, start, end,
playerId, statusId  
- callerId, wrong Input bookingId: server returns 'no result is found'  
- wrong/no callerId, Input booking Id: server returns 'no result is found'  

### _updateBookingPaymentStatus_  
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
TU24 | Check ```updateBookingPaymentStatus``` with valid data and callerId | bookingId, callerId | server updates the booking's payment status | As Expected | Pass
TU25 | Check ```updateBookingPaymentStatus``` with valid data and callerId | bookingId, callerId | server fails to updates the booking's payment status and return 'BookingId does not exist' | As Expected | Pass
TU26 | Check ```updateBookingPaymentStatus``` with valid data but the callerId on the client and the request callerId are different(not staff) | bookingId, callerId | server return 'Action is not allow' | As Expected | Pass
TU27 | Check ```updateBookingPaymentStatus``` with valid data and callerId and statusId.change > 2 | bookingId, callerId | server return 'Try again later' | As Expected | Pass

test case:  
- callerId, Input bookingId: update the booking's payment status  
- callerId, wrong Input bookingId: server returns 'no result is found'
- wrong/no callerId, bookingId: server returns 'no result is found'  

### _getNameCity/getNameVenue/getNameCourt/getNameUser_  
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
TU28 | Check ```getNameCity/getNameVenue/getNameCourt/getNameUser``` with valid data and callerId | cityId/venueId/courtId/userId, callerId | display corresponding name | As Expected | Pass
TU29 | Check ```getNameCity/getNameVenue/getNameCourt/getNameUser``` with valid data and callerId | cityId/venueId/courtId/userId, callerId | display corresponding name | display cityId/venueId/courtId/userId | Fail
TU30 | Check ```getNameCity/getNameVenue/getNameCourt/getNameUser``` with valid data and wrong callerId | cityId/venueId/courtId/userId, callerId | server returns 'no result' | As Expected | Pass

test case:  
- for cityId/venueId/courtId/userId: display corresponding name  
- for cityId/venueId/courtId/userId: display cityId/venueId/courtId/userId  
- no callerId: server cannot return corresponding name  
