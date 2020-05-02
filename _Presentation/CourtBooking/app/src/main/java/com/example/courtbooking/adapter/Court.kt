package com.example.courtbooking.adapter

import org.json.JSONObject
import java.time.LocalDate
import java.time.LocalTime

class Court {
    private var id: String
    private var slotList: ArrayList<Slot>

    constructor(court: JSONObject, date: String) {
        this.id = court.getString("courtId")

        slotList = ArrayList()
        val slotJsonList = court.getJSONArray("courtSlots")

        val current = LocalTime.now()
        // for each json object in the slotJsonList from the last to the first
        for (i in (slotJsonList.length() - 1) downTo 0) {
            val jsonSlot = slotJsonList.getJSONObject(i)
            val startString = jsonSlot.getString("start")
            val endString = jsonSlot.getString("end")

            val start = LocalTime.parse(startString)
            val end = LocalTime.parse(endString)

            val today = LocalDate.now()
            val chosenDate = LocalDate.parse(date)

            if (!chosenDate.isEqual(today)) {
                // if it is not today, feel free to add the slot
                slotList.add( Slot(startString, endString) )
            } else if(start.isAfter(current)) {
                // if the start of the slot is after current time add to slotList
                slotList.add( Slot(startString, endString) )
            } else if (current.plusMinutes(45).isBefore(end)) {
                // if the start of the slot is before current but the length is still available, add to slotList
                slotList.add( Slot( current.toString().substring(0, 5), endString ) )
            } else {
                // if the slot is not available from current, break the loop
                break
            }
        }

    }
    fun getId(): String {
        return id
    }
    fun getSlotList(): ArrayList<Slot> {
        return slotList
    }
}
