package com.example.courtbooking.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.courtbooking.R

class AskForBookingFragment(val date: String, val city: String, val center: String, val court: String, val slot: String, val bookNum: Int, private val callback: CallbackRequestFragment): DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View =inflater.inflate(R.layout.fragment_ask_for_booking, container, false)
        val textView = rootView.findViewById<TextView>(R.id.text2)
        val noButton = rootView.findViewById<Button>(R.id.noButton)
        val yesButton = rootView.findViewById<Button>(R.id.yesButton)

        textView.text =
            "Time slot is $slot at $court, $center, $city on $date. Do you want to create a booking"

        // dismiss the fragment if user press no
        noButton.setOnClickListener {
            dismiss()
        }

        //
        yesButton.setOnClickListener {
            if (bookNum == 3){
                dismiss()
                callback.callBackFail()
            } else {
                dismiss()
                callback.callBack(date, city, center, court, slot)
            }
        }

        return rootView
    }

    interface CallbackRequestFragment {
        fun callBack(date: String, city: String, center: String, court: String, slot: String)
        fun callBackFail()
    }
}