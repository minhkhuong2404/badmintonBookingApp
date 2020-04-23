package com.example.courtbooking.request

class CenterRequest {
    private var centerId: String = ""
    private var cityId : String = ""
    constructor(city : String, center : String){
        this.cityId = city
        this.centerId = center
    }
    fun getCenterId() : String {
        return centerId
    }
    fun getCityId() : String{
        return cityId
    }
}