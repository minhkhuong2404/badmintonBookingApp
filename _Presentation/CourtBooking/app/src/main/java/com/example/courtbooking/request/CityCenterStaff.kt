package com.example.courtbooking.request

class CityCenterStaff {
    var cityId: String = ""
    var courtId: String = ""
    var staffId : String = ""
    constructor(city : String, center : String, staff : String){
        this.cityId = city
        this.courtId = center
        this.staffId = staff
    }
    fun getStaff(): String{
        return staffId
    }
    fun getCity() : String{
        return cityId
    }
    fun getCenter(): String{
        return courtId
    }
}