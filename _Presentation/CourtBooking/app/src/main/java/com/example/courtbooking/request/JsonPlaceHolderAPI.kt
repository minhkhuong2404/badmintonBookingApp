package com.example.courtbooking.request

import retrofit2.Call
import retrofit2.http.*

interface JsonPlaceHolderApi {
    @GET("api/booking/view2")
    fun getPlayerBookings(
//        @Query("userId") userId: Int,
//        @Query("_sort") sort: String?,
//        @Query("_order") order: String?
        @Query("playerid") playerid : String
    ): Call<List<PlayerBookingRequest>>?

    @GET("api/staff/view2")
    fun getStaffs(
    ): Call<List<StaffRequest>>?

    @GET("api/city/view2")
    fun getCities(
    ): Call<List<CityRequest>>?

    @GET("api/city/view")
    fun getCityCenters(
        @Query("cityid") cityid : String
    ): Call<List<CenterRequest>>?

    @GET("api/city/view3")
    fun getCityCenterCourts(
        @Query("cityid") cityid : String,
        @Query("centerid") centerid : String
    ): Call<List<CourtRequest>>?

    @GET("api/booking/view")
    fun getCenterBookings(
        @Query("centerid") centerid : String
    ): Call<List<CenterBookingRequest>>?

    @GET("api/staff/view")
    fun getCityCenterStaffs(
        @Query("cityid") cityid : String,
        @Query("centerid") centerid : String
    ): Call<List<CityCenterStaff>>?

//    @GET(".")
//    fun getBookings(@QueryMap parameters: Map<String, String>): Call<List<GetBookingRequest>>?
//
//    @GET(".")
//    fun getCity(@QueryMap parameters: Map<String, String>): Call<List<CityRequest>>?
//
//    @GET(".")
//    fun getCourt(@QueryMap parameters: Map<String, String>): Call<List<CourtRequest>>?
//
//    @GET(".")
//    fun getCenter(@QueryMap parameters: Map<String, String>): Call<List<CenterRequest>>?
//
//    @GET(".")
//    fun getDate(@QueryMap parameters: Map<String, String>): Call<List<DateRequest>>?
//
//    @GET(".")
//    fun getSlots(@QueryMap parameters: Map<String, String>): Call<List<SlotRequest>>?
//
//    @GET(".")
//    fun getStaff(@QueryMap parameters: Map<String, String>): Call<StaffRequest>?

    @POST("api/booking/create")
    fun createBookings(@Body post: BookingRequest?): Call<BookingRequest>?

    @FormUrlEncoded
    @POST("api/booking/create")
    fun createBookings(
//        @Field("userId") userId: Int,
//        @Field("title") title: String?,
//        @Field("body") text: String?
//        @Body requestBody: RequestBody
        @Field("pdate") pdate : String,
        @Field("pstarttime") pstarttime : String,
        @Field("pendtime") pendtime : String,
        @Field("pcityid") pcityid : String,
        @Field("pcenterid") pcenterid : String,
        @Field("pcourtid") pcourtid : String,
        @Field("pplayerid") pplayerid : String

    ): Call<BookingRequest?>?

    @FormUrlEncoded
    @POST("api/bookings/create")
    fun createBookings(@FieldMap fields: Map<String, String>): Call<BookingRequest>?
}