package com.example.courtbooking

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.courtbooking.booking.Booking
import com.example.courtbooking.booking.BookingAdapter
import com.example.courtbooking.request.ApiUtils
import com.example.courtbooking.request.MySingleton
import com.facebook.AccessToken
import kotlinx.android.synthetic.main.activity_player_booking.*
import org.json.JSONArray

class UserBookingActivity : AppCompatActivity() {
    // Activity vars
    lateinit var userId: String
    lateinit var accessToken: AccessToken

    lateinit var selectedCity: String
    lateinit var selectedDate: String
    lateinit var noBookingTextview : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_booking)
        noBookingTextview = findViewById(R.id.noBookingTextView)

        selectedCity = intent.getStringExtra("city")
        selectedDate = intent.getStringExtra("date")

        // Get token & user id
        accessToken = AccessToken.getCurrentAccessToken()
        userId = accessToken.userId

        // request player booking list
        requestPlayerBookingList("player10", selectedCity, selectedDate)

        Log.i("UserBookingActivity", "$selectedCity | $selectedDate")
    }

    // request player booking list
    private fun requestPlayerBookingList(playerid: String, cityid: String, date: String) {
        var bookingList: ArrayList<Booking>

        var query = "?id=$playerid&cityid=$cityid&date=$date"
        // Get a RequestQueue
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, ApiUtils.URL_GET_BOOKING_BY_PLAYER + query, null,
            Response.Listener { response ->
                Log.i("player booking response", "Response: %s".format(response.toString()))
                if (response.length() != 0) {
                    noBookingTextview.text = ""
                    initRecyclerViewBooking(response)
                }
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
                Log.i("player booking response", "Response: %s".format(error.message))
            }
        )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest)
    }

    // init recycler view booking
    private fun initRecyclerViewBooking(bookingList: JSONArray) {
        rv_booking.apply {
            layoutManager =
                LinearLayoutManager(this@UserBookingActivity, LinearLayoutManager.VERTICAL, false)
            adapter = BookingAdapter(bookingList /*, this@MainScreenActivity */)
            setHasFixedSize(true)
        }
    }

    fun basicAlert() {

        val positiveButtonClick = { dialog: DialogInterface, which: Int ->
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
        }
        val negativeButtonClick = { dialog: DialogInterface, which: Int ->
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
        }

        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            setTitle("Androidly Alert")
            setMessage("We have a message")
            setPositiveButton("OK", DialogInterface.OnClickListener(function = positiveButtonClick))
            setNegativeButton(android.R.string.no, negativeButtonClick)
            show()
        }
    }
}
