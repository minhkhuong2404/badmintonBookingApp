package com.example.courtbooking

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import kotlin.math.floor

class BookingFragment(val date: String, val city: String, val center: String, val court: String, val slot: String, private var confirmBooking: ConfirmBookingInterface, var mainScreen: AppCompatActivity): DialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView:View=inflater.inflate(R.layout.fragment_booking, container, false)

        val placeTV = rootView.findViewById<TextView>(R.id.placeTextView)
        val dateTV = rootView.findViewById<TextView>(R.id.dateBookingTextView)
        val slotTV = rootView.findViewById<TextView>(R.id.slotTextView)
        val startSpinner = rootView.findViewById<Spinner>(R.id.startSpinner)
        val endSpinner = rootView.findViewById<Spinner>(R.id.endSpinner)

        val conformButton = rootView.findViewById<Button>(R.id.bookingConformBtn)

        // write slot information
        placeTV.text = "$court, $center, $city"
        dateTV.text = date
        slotTV.text = slot

        // time choosing
        val slotSplit = slot.split(" - ")
        var startSlot = slotSplit[0]
        var endSlot = slotSplit[1]

        // initialize the choosen time
        var startTime = startSlot
        var endTime = toTimeString(toMinute(startTime) + 45)

        // initial start and end choosing
        var startForChoosing = ArrayList<String>()
        while(toMinute(startSlot) <= toMinute(endSlot) - 45) {
            startForChoosing.add(startSlot)
            startSlot = toTimeString(toMinute(startSlot) + 1)
        }
        startSpinner.adapter = ArrayAdapter<String>(mainScreen, android.R.layout.simple_list_item_1, startForChoosing)

        startSlot = slotSplit[0]
        var endForChoosing = ArrayList<String>()
        if (toMinute(startSlot) + 45 <= toMinute(endSlot)) { endForChoosing.add( toTimeString(toMinute(startTime) + 45) ) }   // 45 minutes booking
        if (toMinute(startSlot) + 60 <= toMinute(endSlot)) { endForChoosing.add( toTimeString(toMinute(startTime) + 60) ) }   // 60 minutes booking
        if (toMinute(startSlot) + 75 <= toMinute(endSlot)) { endForChoosing.add( toTimeString(toMinute(startTime) + 75) ) }   // 75 minutes booking
        if (toMinute(startSlot) + 90 <= toMinute(endSlot)) { endForChoosing.add( toTimeString(toMinute(startTime) + 90) ) }   // 90 minutes booking
        endSpinner.adapter = ArrayAdapter<String>(mainScreen, android.R.layout.simple_list_item_1, endForChoosing)

        // On startTime Choosing Listener
        startSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) { /* Just a place holder */ }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                startTime = startForChoosing[position] // For testing
                endTime = toTimeString(toMinute(startTime) + 45)
                endForChoosing = ArrayList<String>()
                if (toMinute(startTime) + 45 <= toMinute(endSlot)) { endForChoosing.add( toTimeString(toMinute(startTime) + 45) ) }   // 45 minutes booking
                if (toMinute(startTime) + 60 <= toMinute(endSlot)) { endForChoosing.add( toTimeString(toMinute(startTime) + 60) ) }   // 60 minutes booking
                if (toMinute(startTime) + 75 <= toMinute(endSlot)) { endForChoosing.add( toTimeString(toMinute(startTime) + 75) ) }   // 75 minutes booking
                if (toMinute(startTime) + 90 <= toMinute(endSlot)) { endForChoosing.add( toTimeString(toMinute(startTime) + 90) ) }   // 90 minutes booking

                endSpinner.adapter = ArrayAdapter<String>(mainScreen, android.R.layout.simple_list_item_1, endForChoosing)
            }
        }
        // On endTime Choosing Listener
        endSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) { /* Just a place holder */ }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                endTime = endForChoosing[position] // For testing

            }
        }

        //
        conformButton.setOnClickListener {
            dismiss()   // close the current fragment
            confirmBooking.confirm(date, city, center, court, slot, startTime, endTime)
        }

        return rootView
    }

    fun toMinute(time: String): Int {
        val timeSplit =  time.split(":")
        val hour = timeSplit[0].toInt()
        val minute = timeSplit[1].toInt()
        return (hour * 60 + minute)
    }

    fun toTimeString(time: Int): String {
        val hour = floor(time.toDouble() / 60).toInt().toString()
        val minute = (time.toDouble() % 60).toInt().toString()

        var result = String()
        if (minute.toInt() < 10) {
            result = "$hour:0$minute"
        } else {
            result = "$hour:$minute"
        }

        return result
    }

    interface ConfirmBookingInterface {
        fun confirm(date: String, city: String, center: String, court: String, slot: String, start: String, end: String)
    }
}