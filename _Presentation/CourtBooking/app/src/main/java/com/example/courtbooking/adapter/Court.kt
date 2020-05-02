package com.example.courtbooking.adapter

import org.json.JSONObject
import java.time.LocalDate
import java.time.LocalTime

class Court {
    private var id: String
    private var slotList: ArrayList<Slot>

    constructor(court: JSONObject) {
        this.id = court.getString("courtId")

        slotList = ArrayList()
        val slotJsonList = court.getJSONArray("courtSlots")

        // for each json object in the slotJsonList from the last to the first
        for (i in (slotJsonList.length() - 1) downTo 0) {
            val jsonSlot = slotJsonList.getJSONObject(i)
            val start = jsonSlot.getString("start")
            val end = jsonSlot.getString("end")


        }

    }
    fun getId(): String {
        return id
    }
    fun getSlotList(): ArrayList<Slot> {
        return slotList
    }
}
