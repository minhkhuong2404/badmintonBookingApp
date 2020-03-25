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

test case:  
Input valid date and city: server returns list of available slots  
Input invalid date: server returns list of available slots on 
No response: User chooses date and city but there is no result return from the server  

### _getPlayerBookings_  
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
TU03 | Check ```getPlayerBooking``` with valid data and callerId | date, city, playerId, callerId | server returns list of all player bookings | As Expected | Pass
TU04 | Check ```getPlayerBooking``` with invalid data and callerId |date, callerId | server returns list of all player bookings with wrong date or 'no result' | As Expected | Pass
TU05 | Check ```getPlayerBooking``` with invalid data and callerId | city, callerId | server returns list of all player bookings with wrong city or 'no results' | As Expected | Pass
TU06 | Check ```getPlayerBooking``` with invalid data and callerId | playerId, callerId | server returns list of all player bookings with wrong player or 'no results' | As Expected | Pass
TU07 | Check ```getPlayerBooking``` with valid data and wrong callerId | date, city, playerId, callerId | server returns 'no results' | As Expected | Pass

test case:  
- callerId, Input date, city, PlayerId: server returns list of all player bookings 
(courtID, startHour, endHour, venueID, centreID)  
- callerId, wrong Input data/City/PlayerId: server returns PlayerBooking on wrong date/city/player 
or 'no result is found'  
- wrong/no callerId, Input date, City, PlayerID: server returns 'no result is found'  
  
### _getVenueBooking_  
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
TU08 | Check ```getVenueBooking``` with valid data and callerId | date, venueId, callerId | server returns list of all bookings | As Expected | Pass
TU09 | Check ```getVenueBooking``` with invalid data and callerId | date, callerId | server returns list of all bookings on wrong date or 'no result' | As Expected | Pass
TU10 | Check ```getVenueBooking``` with invalid data and callerId | venueId, callerId | server returns list of all bookings on wrong venue or 'no result' | As Expected | Pass
TU11 | Check ```getVenueBooking``` with valid data and wrong callerId | date, venueId, callerId | server returns 'no result' | As Expected | Pass

test case:  
- callerId, Input date, venueId: server return the list of all bookings  
- callerId, wrong Input day/venueId: server return VenueBookings on wrong date/venue or 'no result is found'  
- wrong/no callerId, Input day, venueId: server returns 'no result is found'  

### _createBooking_  
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
TU12 | Check ```createBooking``` with valid data and callerId | date, courtId, start, end, playerId, callerId | server create a bookings | As Expected | Pass
TU13 | Check ```createBooking``` with invalid data and callerId | date/courtId/start/end (same with existed booking) | server refuses bookings | As Expected | Pass
TU14 | Check ```createBooking``` with valid data and wrong callerId | date, courtId, start, end, playerId, callerId | server refuses bookings | As Expected | Pass

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
TU15 | Check ```cancelBooking``` with valid data and callerId and > 24 hours before start time| bookingId, callerId | server cancels booking | As Expected | Pass
TU16 | Check ```cancelBooking``` with valid data and callerId and < 24 hours before start time| bookingId, callerId | server refuses to cancel booking and returns message 'Fail to cancel' | As Expected | Pass
TU17 | Check ```cancelBooking``` with invalid data and callerId | bookingId, callerId | server refuses to cancel booking and returns 'Booking Id does not exist | As Expected | Pass
TU18 | Check ```cancelBooking``` with valid data and wrong callerId | bookingId, callerId | server refuses to cancel booking | As Expected | Pass

test case:
- callerId, Input bookingId, >24 hours before start time: cancelBooking success  
- callerId, Input bookingId, <24 hours before start time: refuse cancelBooking and return message 'It's too late, babe'  
- callerId, wrong Input booking Id, ><24 before start time: server returns 'No result is found' and refuses cancelBooking  
- wrong/no callerId, Input booking Id, ><24 before start time: refuse cancelBooking  

### _getBookingInfo_  
Test Case ID | Test Scenario | Test Data | Expected Results | Actual Results | Pass/Fail
------------ | ------------- | --------- | ---------------- | -------------- | ---------
TU19 | Check ```getBookingInfo``` with valid data and callerId | bookingId, callerId | server returns cityId, venueId, courtId, day, start, end, playerId, statusId | As Expected | Pass
TU20 | Check ```getBookingInfo``` with invalid data and callerId | bookingId, callerId | server returns 'no result' | As Expected | Pass
TU21 | Check ```getBookingInfo``` with valid data and wrong callerId | bookingId, callerId | server returns 'no result' | As Expected | Pass

test case:  
- callerId, Input bookingId: server returns all booking's infomation: cityId, venueId, courtId, day, start, end,
playerId, statusId  
- callerId, wrong Input bookingId: server returns 'no result is found'  
- wrong/no callerId, Input booking Id: server returns 'no result is found'  

### _updateBookingPaymentStatus_  
test case:  
- callerId, Input bookingId: update the booking's payment status  
- callerId, wrong Input bookingId: server returns 'no result is found'
- wrong/no callerId, bookingId: server returns 'no result is found'  

### _getNameCity/getNameVenue/getNameCourt/getNameUser_  
test case:  
- for cityId/venueId/courtId/userId: display corresponding name  
- for cityId/venueId/courtId/userId: display cityId/venueId/courtId/userId  
- no callerId: server cannot return corresponding name  
