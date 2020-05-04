package com.example.courtbooking

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.courtbooking.adapter.Center
import com.example.courtbooking.adapter.CenterAdapter
import com.example.courtbooking.adapter.City
import com.example.courtbooking.request.ApiUtils
import com.example.courtbooking.request.MySingleton
import com.facebook.AccessToken
import kotlinx.android.synthetic.main.activity_city_slot.*
import org.json.JSONArray

class CitySlotActivity : AppCompatActivity() {
    // User vars
    lateinit var accessToken: AccessToken
    lateinit var playerId: String
    lateinit var selectedCity: String
    lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_slot)
        // create no booking text view
        val noSlotTextview = findViewById<TextView>(R.id.noSlotTextView)

        // get data from previous activity
        playerId = intent.getStringExtra("player")
        selectedCity = intent.getStringExtra("city")
        selectedDate = intent.getStringExtra("date")

        // request available slot to server
        requestCitySlot(playerId, selectedCity, selectedDate)
    }

    // request available slot
    private fun requestCitySlot(player: String, city: String, date: String) {
        // Preparing query
        val query = "?id=$city&date=$date"

        // Get a RequestQueue
        val jsonObjectRequest =
            JsonObjectRequest(
                Request.Method.GET, ApiUtils.URL_GET_CITY_SLOT + query, null,
                Response.Listener { response ->
                    val city = City(response)
                    val centerList = city.getCenterList()

                    // show available slot
                    initRecyclerViewCenter(centerList)
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Cannot connect to server.", Toast.LENGTH_SHORT).show()
                }
            )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    // init recycler view center
    @SuppressLint("WrongConstant")
    private fun initRecyclerViewCenter(centerList: ArrayList<Center>) {
        // Calling the recycler view for Center
        if (centerList.size != 0) {
            rv_center.apply {
                layoutManager =
                    LinearLayoutManager(this@CitySlotActivity, LinearLayout.VERTICAL, false)
                adapter = CenterAdapter(
                    this@CitySlotActivity,
                    selectedDate,
                    selectedCity,
                    playerId,
                    centerList
                )
                setHasFixedSize(true)
            }
        }

    }
    // reload slot when go back
    override fun onResume() {
        super.onResume()
        // request available slot to server
        requestCitySlot(playerId, selectedCity, selectedDate)
    }
}
