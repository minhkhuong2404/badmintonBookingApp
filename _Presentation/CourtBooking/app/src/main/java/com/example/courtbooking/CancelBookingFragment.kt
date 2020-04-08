package com.example.courtbooking

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class CancelBookingFragment(var bookingId: String, private var cancelIterface: CancelFinishInterface): DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View =inflater.inflate(R.layout.fragment_cancel_booking, container, false)
        val textView = rootView.findViewById<TextView>(R.id.textView)
        val noButton = rootView.findViewById<Button>(R.id.noButton)
        val yesButton = rootView.findViewById<Button>(R.id.yesButton)

        textView.text =
            "Cancelation only take place 24 hours before the start time. Do you want to cancel booking $bookingId"

        noButton.setOnClickListener {
            dismiss()
        }

        yesButton.setOnClickListener {
            dismiss()
            cancelIterface.moveToFinishCancel(bookingId)

        }

        return rootView
    }

    interface CancelFinishInterface {
        fun moveToFinishCancel(message: String)
    }
}