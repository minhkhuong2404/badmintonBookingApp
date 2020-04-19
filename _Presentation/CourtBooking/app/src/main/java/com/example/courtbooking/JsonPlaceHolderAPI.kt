package com.example.courtbooking

import retrofit2.Call
import retrofit2.http.*


interface JsonPlaceHolderApi {
    @GET("posts")
    fun getPosts(
        @Query("userId") userId: Int,
        @Query("_sort") sort: String?,
        @Query("_order") order: String?
    ): Call<List<BookingRequest>>?

    @GET("posts")
    fun getPosts(@QueryMap parameters: Map<String, String>): Call<List<BookingRequest>>?

    @POST("posts")
    fun createPost(@Body post: BookingRequest?): Call<BookingRequest>?

    @FormUrlEncoded
    @POST("posts")
    fun createPost(
        @Field("userId") userId: Int,
        @Field("title") title: String?,
        @Field("body") text: String?
    ): Call<BookingRequest?>?

    @FormUrlEncoded
    @POST("posts")
    fun createPost(@FieldMap fields: Map<String?, String?>?): Call<BookingRequest>?
}