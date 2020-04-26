package com.example.courtbooking

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courtbooking.adapter.BookingAdapter
import kotlinx.android.synthetic.main.activity_player_booking.*
import org.json.JSONArray


class PlayerBookingActivity : AppCompatActivity() {
    lateinit var noBookingTextview : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_booking)

        // create no booking text view
        noBookingTextview = findViewById(R.id.noBookingTextView)

        // get response json from previous activity
        var jsonString: String = intent.getStringExtra("jsonString")
        // json string to json array
        val bookingJSONArray = JSONArray(jsonString)

        if (bookingJSONArray.length() > 0) {
            // hide no booking text view
            findViewById<TextView>(R.id.noBookingTextView).visibility = View.INVISIBLE
            // display data
            initRecyclerViewBooking(bookingJSONArray)
        }
    }

    // init recycler view booking
    private fun initRecyclerViewBooking(bookingList: JSONArray) {
        rv_booking.apply {
            layoutManager =
                LinearLayoutManager(this@PlayerBookingActivity, LinearLayoutManager.VERTICAL, false)
            adapter = BookingAdapter(bookingList)
            setHasFixedSize(true)
        }
    }
}
