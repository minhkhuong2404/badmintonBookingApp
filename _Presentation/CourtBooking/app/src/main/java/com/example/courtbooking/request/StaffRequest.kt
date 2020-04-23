package com.example.courtbooking.request

class StaffRequest{
    var staffid : String = ""
    constructor(staff : String){
        this.staffid =staff
    }
    fun getStaffId(): String{
        return staffid
    }
}