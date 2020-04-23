package com.example.courtbooking.request

class StaffRequest{
    private var staffId : String = ""
    private var cityId : String  = ""
    private var courtId : String = ""

    constructor(staffId: String, cityId: String, courtId: String) {
        this.staffId = staffId
        this.cityId = cityId
        this.courtId = courtId
    }

    fun getStaffId(): String{
        return staffId
    }
    fun getCityId(): String{
        return cityId
    }
    fun getCourtId(): String{
        return courtId
    }
}