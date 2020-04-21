package com.example.courtbooking

import com.google.gson.annotations.SerializedName

class BookingRequest(
    val pbookingid: String,
    val ptimestamp: String,
    val pdate: String,
    val pstarttime: String,
    val pendtime: String,
    val pcityid: String,
    val pcenterid: String,
    val pcourtid: String,
    val pplayerid: String
//    val name : String,
//    val pass : String
    )


