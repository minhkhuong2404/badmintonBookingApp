package com.example.courtbooking.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.courtbooking.R

class FinishBookingFragment(val date: String, val city: String, val center: String, val court: String, val start: String, val end: String, private var myBookingInterface: MyBookingInterface): DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View =inflater.inflate(R.layout.fragment_finish_booking, container, false)
        val placeTV = rootView.findViewById<TextView>(R.id.placeTextView)
        val dateTV = rootView.findViewById<TextView>(R.id.dateBookingTextView)
        val startTV = rootView.findViewById<TextView>(R.id.startTextView)
        val endTV = rootView.findViewById<TextView>(R.id.endTextView)

        val myBookingBtn = rootView.findViewById<Button>(R.id.myBookingButton)

        placeTV.text = "$court, $center, $city"
        dateTV.text = date
        startTV.text = start
        endTV.text = end

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