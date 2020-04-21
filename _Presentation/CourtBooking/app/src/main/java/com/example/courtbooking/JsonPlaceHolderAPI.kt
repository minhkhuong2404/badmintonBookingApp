package com.example.courtbooking

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface JsonPlaceHolderApi {
    @GET(".")
    fun getBookings(
//        @Query("userId") userId: Int,
//        @Query("_sort") sort: String?,
//        @Query("_order") order: String?
        @Query("name") name : String
    ): Call<List<BookingRequest>>?

    @GET(".")
    fun getBookings(@QueryMap parameters: Map<String, String>): Call<List<BookingRequest>>?

    @POST(".")
    fun createBookings(@Body post: BookingRequest?): Call<BookingRequest>?

    @FormUrlEncoded
    @POST(".")
    fun createBookings(
//        @Field("userId") userId: Int,
//        @Field("title") title: String?,
//        @Field("body") text: String?
//        @Body requestBody: RequestBody
        @Field("pbookingid") pbookingid : String,
        @Field("ptimestamp") ptimestamp : String,
        @Field("pdate") pdate : String,
        @Field("pstarttime") pstarttime : String,
        @Field("pendtime") pendtime : String,
        @Field("pcityid") pcityid : String,
        @Field("pcenterid") pcenterid : String,
        @Field("pcourtid") pcourtid : String,
        @Field("pplayerid") pplayerid : String

    ): Call<BookingRequest?>?

    @FormUrlEncoded
    @POST(".")
    fun createBookings(@FieldMap fields: Map<String, String>): Call<BookingRequest>?
}