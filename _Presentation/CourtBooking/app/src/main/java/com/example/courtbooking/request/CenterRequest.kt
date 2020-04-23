package com.example.courtbooking.request

class CenterRequest {
    private var centerid: String = ""
    private var cityid : String = ""
    constructor(city : String, center : String){
        this.cityid = city
        this.centerid = center
    }
    fun getCenterId() : String {
        return centerid
    }
}