package com.example.courtbooking

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.example.courtbooking.adapter.Court
import com.example.courtbooking.adapter.Slot
import com.example.courtbooking.request.ApiUtils
import com.example.courtbooking.request.MySingleton
import com.facebook.AccessToken
import kotlinx.android.synthetic.main.activity_city_slot.*
import org.json.JSONArray
import org.json.JSONObject

class CitySlotActivity : AppCompatActivity() {
    // User vars
    lateinit var accessToken: AccessToken
    lateinit var playerId: String
    lateinit var selectedCity: String
    lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_slot)
        val holidayTextView = findViewById<TextView>(R.id.holidayTextView)

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
        var query = "?id=$city&date=$date"

        // Get a RequestQueue
        val jsonObjectRequest =
            JsonObjectRequest(
                Request.Method.GET, ApiUtils.URL_GET_CITY_SLOT + query, null,
                Response.Listener { response ->
                    Log.i("City Slot", "Response: %s".format(response.toString()))
                    // data for center adapter
                    var centerList = serializeJson(response.toString())
                    // show available slot
                    initRecyclerViewCenter(centerList, playerId, selectedCity, selectedDate)
                },
                Response.ErrorListener { error ->
<<<<<<< HEAD
                    holiday_text_view.text = error.message
                    Toast.makeText(this, "Cannot connect to server.", Toast.LENGTH_SHORT).show()
=======
                    val msg = String(error.networkResponse.data)
                    if (msg == "GCB-HOL") {
                        holidayTextView.visibility = View.VISIBLE
                    }
                    if (msg == "GCB-000") {
                        Toast.makeText(this, "City id is not alphanumeric.", Toast.LENGTH_SHORT).show()
                    }
                    if (msg == "GCB-001") {
                        Toast.makeText(this, "City is not existed.", Toast.LENGTH_SHORT).show()
                    }
>>>>>>> 28b1b0b7db4adb785c896d4572fd7e5d1e782693
                }
            )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    // serialize json for recycler view
    private fun serializeJson(jsonString: String): ArrayList<Center> {
        // return
        var centerList = ArrayList<Center>()

        var cityObj = JSONObject(jsonString)
        var cityIter = cityObj.keys()
        while (cityIter.hasNext()) {
            var centerKey = cityIter.next()
            Log.i("Center", "$centerKey")
            var centerObj = JSONObject(cityObj.get(centerKey).toString())
            var centerIter = centerObj.keys()

            var courtList = ArrayList<Court>()
            while (centerIter.hasNext()) {
                var courtKey = centerIter.next()
                var slotArray = JSONArray(centerObj.get(courtKey).toString())

                var slotList = ArrayList<Slot>()
                for (i in 0 until slotArray.length()) {
                    var slotObj = JSONObject(slotArray[i].toString())
                    var start = slotObj.getString("start").subSequence(0, 5)
                    var end = slotObj.getString("end").subSequence(0, 5)
                    var slotId = "$centerKey/$courtKey/$start - $end"
                    var slot = Slot(slotId)
                    slotList.add(slot)
                }
                var court = Court(courtKey, slotList)
                courtList.add(court)
            }
            var center = Center(centerKey, courtList)
            centerList.add(center)
        }
        return centerList
    }

    // init recycler view center
    @SuppressLint("WrongConstant")
    private fun initRecyclerViewCenter(
        centerList: ArrayList<Center>,
        player: String,
        city: String,
        date: String
    ) {
        // Calling the recycler view for Center
        rv_center.apply {
            layoutManager = LinearLayoutManager(this@CitySlotActivity, LinearLayout.VERTICAL, false)
            adapter = CenterAdapter(
                centerList,
                this@CitySlotActivity,
                player,
                city,
                date
            )
            setHasFixedSize(true)
        }
    }
    // reload slot when go back
    override fun onResume() {
        super.onResume()
        // request available slot to server
        requestCitySlot(playerId, selectedCity, selectedDate)
    }
}