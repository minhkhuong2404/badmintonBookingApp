package com.example.courtbooking.adapter

import android.icu.text.StringPrepParseException

class Booking {

    private var bookingId: String = ""
    private var timestamp: String = ""
    private var date: String = ""
    private var start: String = ""
    private var end: String = ""
    private var cityId: String = ""
    private var centerId: String = ""
    private var courtId: String = ""
    private var playerId: String = ""
    private var status: String = ""

    constructor(
        bookingId: String,
        timestamp: String,
        date: String,
        start: String,
        end: String,
        cityId: String,
        centerId: String,
        courtId: String,
        playerId: String,
        status: String
    ) {
        this.bookingId = bookingId
        this.timestamp = timestamp
        this.date = date
        this.start = start
        this.end = end
        this.cityId = cityId
        this.centerId = centerId
        this.courtId = courtId
        this.playerId = playerId
        this.status = status
    }

    fun getBookingId() : String{
        return bookingId
    }
    fun getTimestamp() : String{
        return timestamp
    }
    fun getDate() : String{
        return bookingId
    }
    fun getStartTime() : String{
        return start
    }
    fun getEndTime() : String{
        return end
    }
    fun getCityId() : String{
        return cityId
    }
    fun getCourtId() : String{
        return courtId
    }
    fun getPlayerId() : String{
        return playerId
    }
    fun getStatus() : String{
        return status
    }
}