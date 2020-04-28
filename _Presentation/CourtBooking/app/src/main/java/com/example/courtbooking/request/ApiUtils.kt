package com.example.courtbooking.request

object ApiUtils {
    const val BASE_URL = "http://10.0.2.2:8003"
    const val PORT_NUMBER = 8003

    // URL GET
    const val URL_GET_CITY_ALL = BASE_URL + "/api/city/all"
    const val URL_GET_BOOKING_BY_PLAYER = BASE_URL + "/api/booking/player"
    const val URL_GET_CITY_SLOT = BASE_URL + "/api/city/slot"
    const val URL_GET_CENTER_INFO = BASE_URL + "/api/center/info"

    const val URL_GET_CENTER_HOLIDAY = BASE_URL + "/api/center/holiday"
    const val URL_GET_CENTER_OPENING_HOUR = BASE_URL + "/api/center/activehour"
    const val URL_GET_CENTER_MIN_LENGTH = BASE_URL + "/api/center/minlength"


    // URL POST
    const val URL_BOOKING_CREATE = BASE_URL + "/api/booking/create"
    const val URL_BOOKING_CANCEL = BASE_URL + "/api/booking/cancel"
}