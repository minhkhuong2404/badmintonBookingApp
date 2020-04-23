package com.example.courtbooking.request

class CenterBookingRequest {
    var centerid : String = ""
    var bookingid : String = ""
    constructor(center : String, booking: String){
        this.centerid = center
        this.bookingid = booking
    }
    fun getBookingId(): String{
        return bookingid
    }
    fun getCenterId() : String{
        return centerid
    }
}