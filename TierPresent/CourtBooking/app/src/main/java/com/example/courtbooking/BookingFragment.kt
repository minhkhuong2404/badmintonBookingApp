package com.example.courtbooking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class BookingFragment(slotInfo: List<String>): DialogFragment() {

    val date = slotInfo.get(0)
    val city = slotInfo.get(1)
    val center = slotInfo.get(2)
    val court = slotInfo.get(3)
    val slot = slotInfo.get(4)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView:View=inflater.inflate(R.layout.fragment_booking, container, false)

        val placeTV = rootView.findViewById<TextView>(R.id.placeTextView)
        val dateTV = rootView.findViewById<TextView>(R.id.dateBookingTextView)
        val slotTV = rootView.findViewById<TextView>(R.id.slotTextView)

        placeTV.text = court + ", " + center + ", " + city
        dateTV.text = date
        slotTV.text = slot

        return rootView
    }
}