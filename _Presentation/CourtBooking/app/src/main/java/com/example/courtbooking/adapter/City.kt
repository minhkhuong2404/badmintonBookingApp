package com.example.courtbooking.adapter

import org.json.JSONObject

class City {
    private var id: String
    private var centerList: ArrayList<Center>

    constructor(city: JSONObject, date: String) {
        id = city.getString("cityId")
        centerList = ArrayList<Center>()

        val jsonCenterList = city.getJSONArray("citySlots")
        // create a center list without any 'no court' center
        for (i in 0 until jsonCenterList.length()) {
            val jsonCenter = jsonCenterList.getJSONObject(i)
            val courtList = jsonCenter.getJSONArray("centerSlots")
            if (courtList.length() != 0) {
                centerList.add( Center(jsonCenter, date) )
            }
        }
    }

    fun getId(): String {
        return id
    }
    fun getCenterList(): ArrayList<Center> {
        return centerList
    }
}