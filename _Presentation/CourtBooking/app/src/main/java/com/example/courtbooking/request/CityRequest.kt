package com.example.courtbooking.request

class CityRequest {
    private var cityId: String = ""
    constructor(pcityId : String){
        this.cityId = pcityId
    }
    fun getCityId(): String {
        return cityId
    }
}