package com.example.courtbooking.request

class CenterRequest {
    private var pcenterid: String = ""
    private var pcityid : String = ""
    fun getCenterId(city : String) : String {
        this.pcityid = city
        return pcenterid
    }
}