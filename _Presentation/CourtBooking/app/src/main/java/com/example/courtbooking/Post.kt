package com.example.courtbooking

import com.google.gson.annotations.SerializedName


class Post(val userId: Int, val title: String, @field:SerializedName("body") val text: String) {
    val id: Int? = null

}