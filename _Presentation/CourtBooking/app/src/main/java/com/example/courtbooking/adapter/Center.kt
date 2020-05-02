package com.example.courtbooking.adapter

import org.json.JSONObject

class Center {
    private var cityId: String
    private var id: String
    private var courtList: ArrayList<Court>

    constructor(center: JSONObject) {
        cityId = center.getString("cityId")
        id = center.getString("centerId")

        courtList = ArrayList()
        val jsonCourtList = center.getJSONArray("centerSlots")
        // add court that has available slot to slotList
        for (i in (jsonCourtList.length() - 1) downTo 0) {
            val jsonCourt = jsonCourtList.getJSONObject(i)
            val slotList = jsonCourt.getJSONArray("courtSlots")
            if (slotList.length() != 0) {
                courtList.add( Court(jsonCourt) )
            }
        }
    }

    fun getCityId(): String {
        return cityId
    }
    fun getId(): String {
        return id
    }
    fun getCourtList(): ArrayList<Court> {
        return courtList
    }
}
