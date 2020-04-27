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
    lateinit var accessToken: AccessToken
    lateinit var cityid: String
    lateinit var date: String
    lateinit var playerid: String
    lateinit var noBookingTextview : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_booking)

        // create no booking text view
        noBookingTextview = findViewById(R.id.noBookingTextView)

        // get response json from previous activity
        cityid = intent.getStringExtra("cityid")
        date = intent.getStringExtra("date")

        var jsonString: String = intent.getStringExtra("jsonString")
        // json string to json array
        val bookingJSONArray = JSONArray(jsonString)

        if (bookingJSONArray.length() > 0) {
            // hide no booking text view
            findViewById<TextView>(R.id.noBookingTextView).visibility = View.INVISIBLE
            // display data
            initRecyclerViewBooking(bookingJSONArray)
        }
        requestPlayerBooking("player1", cityid, date)
    }

    // request player booking list
    private fun requestPlayerBooking(playerid: String, cityid: String, date: String) {
        // Preparing query
        var query = "?id=$playerid&cityid=$cityid&date=$date"

        // Get a RequestQueue
        val jsonArrayRequest =
            JsonArrayRequest(
                Request.Method.GET, ApiUtils.URL_GET_BOOKING_BY_PLAYER + query, null,
                Response.Listener { response ->
                    Log.i("player booking response", "Response: %s".format(response.toString()))
                    initRecyclerViewBooking(response)
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
