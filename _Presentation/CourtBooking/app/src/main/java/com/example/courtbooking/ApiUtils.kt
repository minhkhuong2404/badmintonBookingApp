package com.example.courtbooking

import com.example.courtbooking.RetrofitClient.getClient

object ApiUtils {
    const val BASE_URL = "http://10.0.2.2:8003/api/booking/create/"
    val aPIService: JsonPlaceHolderApi
        get() = getClient(BASE_URL)!!.create(JsonPlaceHolderApi::class.java)
}