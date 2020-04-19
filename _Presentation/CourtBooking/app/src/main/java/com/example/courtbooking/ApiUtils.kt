package com.example.courtbooking

import com.example.courtbooking.RetrofitClient.getClient

object ApiUtils {
    const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    val aPIService: JsonPlaceHolderApi
        get() = getClient(BASE_URL)!!.create(JsonPlaceHolderApi::class.java)
}