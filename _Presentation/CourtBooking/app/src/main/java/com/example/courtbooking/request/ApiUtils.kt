package com.example.courtbooking.request

object ApiUtils {
    const val BASE_URL = "http://10.0.2.2:8003"

    // URL GET
    const val URL_GET_CITY_ALL = BASE_URL + "/api/city/all"
    const val URL_GET_BOOKING_BY_PLAYER = BASE_URL + "/api/booking/player"
    const val URL_GET_CITY_SLOT = BASE_URL + "/api/city/slot"

    // URL POST
    const val URL_BOOKING_CREATE = BASE_URL + "/api/booking/create"
    const val URL_BOOKING_CANCEL = BASE_URL + "/api/booking/cancel"
}