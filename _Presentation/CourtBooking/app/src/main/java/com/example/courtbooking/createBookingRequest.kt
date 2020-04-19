package com.example.courtbooking

import java.sql.Timestamp

data class createBookingRequest (
    val facebookID : String,
    val facebookName : String,
    val bookingTime: String,
    val date : String,
    val city : String,
    val center : String,
    val court : String,
    val slot : String
)