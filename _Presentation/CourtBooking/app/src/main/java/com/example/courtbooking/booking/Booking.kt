package com.example.courtbooking.booking

data class Booking (
    val id : String,
    val date : String,
    val time : String,
    val paymentStatus : String,
    val court : String,
    val createdOn : String
)