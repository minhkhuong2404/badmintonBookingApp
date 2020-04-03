package com.example.courtbooking

import java.io.Serializable

data class MainHeadingData(
    var mainHeading: String? = "",
    var subData: ArrayList<SubHeadingData>
) : Serializable

data class SubHeadingData(
    var subHeading: String? = ""
) : Serializable