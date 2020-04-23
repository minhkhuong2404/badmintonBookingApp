package com.example.courtbooking.request

import com.example.courtbooking.request.RetrofitClient.getClient

object ApiUtils {
    const val BASE_URL = "http://10.0.2.2:8003/"
    val aPIService: JsonPlaceHolderApi
        get() = getClient(BASE_URL)!!.create(
            JsonPlaceHolderApi::class.java)
}