package com.example.courtbooking.request

class CourtRequest {
    private var pcourtid: String = ""
    private var pcityid: String = ""
    private var pcenterid: String = ""

    fun getCourtId(center : String, city : String): String{
        this.pcenterid = center
        this.pcityid = city
        return pcourtid
    }


}