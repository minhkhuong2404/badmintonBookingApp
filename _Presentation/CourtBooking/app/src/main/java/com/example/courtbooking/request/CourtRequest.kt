package com.example.courtbooking.request

class CourtRequest {
    var cityid : String = ""
    var centerid : String = ""
    var courtid : String = ""

    constructor(city : String, center : String, court : String){
        this.cityid = city
        this.centerid = center
        this.courtid = court
    }
    fun getCourtId(): String{
        return courtid
    }
}