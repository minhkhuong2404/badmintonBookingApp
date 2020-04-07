package com.example.courtbooking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class BookingFragment(val date: String, val city: String, val center: String, val court: String, val slot: String, private var confirmBooking: ConfirmBookingInterface): DialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView:View=inflater.inflate(R.layout.fragment_booking, container, false)

        val placeTV = rootView.findViewById<TextView>(R.id.placeTextView)
        val dateTV = rootView.findViewById<TextView>(R.id.dateBookingTextView)
        val slotTV = rootView.findViewById<TextView>(R.id.slotTextView)

        val conformButton = rootView.findViewById<Button>(R.id.bookingConformBtn)

        placeTV.text = court + ", " + center + ", " + city
        dateTV.text = date
        slotTV.text = slot

        conformButton.setOnClickListener {
            dismiss()   // close the current fragment
            confirmBooking.confirm(date, city, center, court, slot)
        }

        return rootView
    }

    interface ConfirmBookingInterface {
        fun confirm(date: String, city: String, center: String, court: String, slot: String)
    }
}