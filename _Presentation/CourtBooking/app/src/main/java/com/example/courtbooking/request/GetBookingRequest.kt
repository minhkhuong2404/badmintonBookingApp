package com.example.courtbooking.request

class GetBookingRequest (
    val id : String,
    val date : String,
    val time : String,
    val paymentStatus : String,
    val court : String,
    val createdOn : String
)