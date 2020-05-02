package com.example.courtbooking.adapter

import org.json.JSONObject

class Slot {
    private var startTime: String
    private var endTime: String

    constructor(slot: JSONObject) {
        startTime = slot.getString("start")
        endTime = slot.getString("end")
    }

    constructor(start: String, end: String) {
        startTime = start
        endTime = end
    }

    fun setStart(start: String) {
        startTime = start
    }
    fun setEnd(end: String) {
        endTime = end
    }
    fun getStart(): String {
        return startTime
    }

    fun getEnd(): String {
        return endTime
    }
}
