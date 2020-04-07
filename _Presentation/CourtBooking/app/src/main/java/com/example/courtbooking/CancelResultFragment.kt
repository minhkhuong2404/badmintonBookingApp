package com.example.courtbooking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class CancelResultFragment(var bookingId: String, var success: Boolean): DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView: View =inflater.inflate(R.layout.fragment_cancel_result, container, false)

        var header = rootView.findViewById<TextView>(R.id.cancelResultHeader)
        var text = rootView.findViewById<TextView>(R.id.cancelResultInfo)
        var button = rootView.findViewById<Button>(R.id.cancelButton)

        if (success == true) {
            header.text = "Booking Canceled"
            text.text = "The booking " + bookingId + " has been canceled."
        } else {
            header.text = "Cancel Error"
            text.text = "Cancellation only takes place 24 hours before the start time. You cannot cancel this booking."
        }

        button.setOnClickListener {
            dismiss()
        }

        return rootView
    }
}