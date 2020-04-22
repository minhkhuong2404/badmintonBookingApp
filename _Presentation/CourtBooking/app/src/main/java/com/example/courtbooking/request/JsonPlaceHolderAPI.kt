package com.example.courtbooking.request

import retrofit2.Call
import retrofit2.http.*

interface JsonPlaceHolderApi {
    @GET(".")
    fun getBookings(
//        @Query("userId") userId: Int,
//        @Query("_sort") sort: String?,
//        @Query("_order") order: String?
        @Query("pbookingid") pbookingid : String
    ): Call<List<GetBookingRequest>>?

    @GET(".")
    fun getBookings(@QueryMap parameters: Map<String, String>): Call<List<GetBookingRequest>>?

    @GET(".")
    fun getCity(@QueryMap parameters: Map<String, String>): Call<List<CityRequest>>?

    @GET(".")
    fun getCourt(@QueryMap parameters: Map<String, String>): Call<List<CourtRequest>>?

    @GET(".")
    fun getCenter(@QueryMap parameters: Map<String, String>): Call<List<CenterRequest>>?

    @GET(".")
    fun getDate(@QueryMap parameters: Map<String, String>): Call<List<DateRequest>>?

    @GET(".")
    fun getSlots(@QueryMap parameters: Map<String, String>): Call<List<SlotRequest>>?

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