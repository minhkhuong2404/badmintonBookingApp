package com.example.courtbooking

import android.content.Intent
import android.os.Bundle
import android.util.JsonToken
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.courtbooking.adapter.BookingAdapter
import com.example.courtbooking.request.ApiUtils
import com.example.courtbooking.request.MySingleton
import com.facebook.AccessToken
import kotlinx.android.synthetic.main.activity_player_booking.*
import org.json.JSONArray


class PlayerBookingActivity : AppCompatActivity() {
    // User vars
    // User vars
    lateinit var accessToken: AccessToken
    lateinit var playerId : String
    lateinit var selectedCity: String
    lateinit var selectedDate: String
    // View
    lateinit var noBookingTextview : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_booking)

        // create no booking text view
        noBookingTextview = findViewById(R.id.noBookingTextView)

        // get data from previous activity
        selectedCity = intent.getStringExtra("city")
        selectedDate = intent.getStringExtra("date")
        playerId = intent.getStringExtra("player")
        var jsonString = intent.getStringExtra("jsonString")

        // requestPlayerBooking to server
        requestPlayerBooking(playerId, selectedCity, selectedDate)
    }

    // request player booking list
    private fun requestPlayerBooking(player: String, city: String, date: String) {
        // Preparing query
        var query = "?id=$player&cityid=$city&date=$date"

        // Get a RequestQueue
        val jsonArrayRequest =
            JsonArrayRequest(
                Request.Method.GET, ApiUtils.URL_GET_BOOKING_BY_PLAYER + query, null,
                Response.Listener { response ->
                    Log.i("Player Booking", "Response: %s".format(response.toString()))
                    // initialize recycler view booking
                    if (response.length() > 0) {
                        // hide no booking text view
                        findViewById<TextView>(R.id.noBookingTextView).visibility = View.INVISIBLE
                        // display data
                        initRecyclerViewBooking(response)
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Cannot connect to server.", Toast.LENGTH_SHORT).show()
                }
            )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest)
    }

    // init recycler view booking
    private fun initRecyclerViewBooking(bookingList: JSONArray) {
        rv_booking.apply {
            layoutManager =
                LinearLayoutManager(this@PlayerBookingActivity, LinearLayoutManager.VERTICAL, false)
            adapter = BookingAdapter(bookingList)
        }
    }
}
