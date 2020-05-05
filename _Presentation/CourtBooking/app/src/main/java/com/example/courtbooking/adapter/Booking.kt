package com.example.courtbooking.adapter

import org.json.JSONObject
import java.sql.Date
import java.sql.Timestamp

class Booking {
    private var bookingId:String
    private var cityId:String
    private var centerId:String
    private var courtId:String
    private var date: String
    private var timestamp: String
    private var start:String
    private var end:String
    private var playerId:String
    private var status: Int
    private var cardId: Int

    constructor(booking: JSONObject) {
        bookingId = booking.getInt("bookingId").toString()
        cityId = booking.getString("cityId")
        centerId = booking.getString("centerId")
        courtId = booking.getString("courtId")
        date = Date(booking.getLong("date")).toString()
        timestamp = Timestamp(booking.getLong("timestamp")).toString()
        start = booking.getString("start")
        end = booking.getString("end")
        playerId = booking.getString("playerId")
        status = booking.getInt("status")
        cardId = booking.getInt("cardId")
    }

    fun getBookingId(): String {
        return bookingId
    }
    fun getCityId(): String {
        return cityId
    }
    fun getCenterId(): String {
        return centerId
    }
    fun getCourtId(): String {
        return courtId
    }
    fun getDate(): String {
        return date
    }
    fun getTimestamp(): String {
        return timestamp
    }
    fun getStart(): String {
        return start
    }
    fun getEnd(): String {
        return end
    }
    fun getPlayerId(): String {
        return playerId
    }
    fun getStatus(): Int {
        return status
    }
    fun getCard(): Int {
        return cardId
    }
}