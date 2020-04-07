package com.example.courtbooking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class FinishBookingFragment(val date: String, val city: String, val center: String, val court: String, val slot: String, private var myBookingInterface: MyBookingInterface): DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView: View =inflater.inflate(R.layout.fragment_finish_booking, container, false)

        val placeTV = rootView.findViewById<TextView>(R.id.placeTextView)
        val dateTV = rootView.findViewById<TextView>(R.id.dateBookingTextView)
        val startTV = rootView.findViewById<TextView>(R.id.startTextView)
        val endTV = rootView.findViewById<TextView>(R.id.endTextView)

        val myBookingBtn = rootView.findViewById<Button>(R.id.myBookingButton)

        placeTV.text = court + ", " + center + ", " + city
        dateTV.text = date
        startTV.text = slot.split(" - ").get(0)
        endTV.text = slot.split(" - ").get(1)

        myBookingBtn.setOnClickListener {
            dismiss()   // close the current fragment
            myBookingInterface.showBooking()
        }

        return rootView
    }

    interface MyBookingInterface {
        fun showBooking()
    }

}