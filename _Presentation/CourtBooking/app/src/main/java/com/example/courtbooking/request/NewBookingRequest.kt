package com.example.courtbooking.request


class NewBookingRequest{
    private var pdate: String = ""
    private var pstarttime: String = ""
    private var pendtime: String = ""
    private var pcityid: String = ""
    private var pcenterid: String = ""
    private var pcourtid: String = ""
    private var pplayerid: String = ""

    constructor(
        pdate: String,
        pstarttime: String,
        pendtime: String,
        pcityid: String,
        pcenterid: String,
        pcourtid: String,
        pplayerid: String
    ) {
        this.pdate = pdate
        this.pstarttime = pstarttime
        this.pendtime = pendtime
        this.pcityid = pcityid
        this.pcenterid = pcenterid
        this.pcourtid = pcourtid
        this.pplayerid = pplayerid
    }

    fun getDate(): String{
        return pdate
    }
    fun getStartTime(): String{
        return pstarttime
    }
    fun getEndTime(): String{
        return pendtime
    }
    fun getCityID(): String{
        return pcityid
    }
    fun getCenterID(): String{
        return pcenterid
    }
    fun getCourtId(): String{
        return pcourtid
    }
    fun getPlayerId(): String{
        return pplayerid
    }
}

