package com.example.courtbooking

import com.google.gson.annotations.SerializedName

class BookingRequest(
//    val bookingId: String,
//    val bookingTime: String,
//    val date: String,
//    val startTime: String,
//    val endTime: String,
//    val cityId: String,
//    val centerId: String,
//    val courtId: String,
//    val facebookId: String

    )
class Post(val userId: Int?, val title: String?, @field:SerializedName("body") val text: String?) {
    val id: Int? = null

}