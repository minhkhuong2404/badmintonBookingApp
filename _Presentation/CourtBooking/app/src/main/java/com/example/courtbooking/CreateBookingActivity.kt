package com.example.courtbooking

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.courtbooking.request.ApiUtils
import com.example.courtbooking.request.MySingleton
import com.facebook.AccessToken
import kotlinx.android.synthetic.main.activity_create_booking.*
import org.json.JSONObject
import java.sql.Time
import kotlin.math.floor


class CreateBookingActivity : AppCompatActivity() {
    lateinit var accessToken: AccessToken
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_booking)

        val tvCity = findViewById<TextView>(R.id.tvCity)
        val tvCenter = findViewById<TextView>(R.id.tvCenter)
        val tvCourt = findViewById<TextView>(R.id.tvCourt)
        val tvPlayer = findViewById<TextView>(R.id.tvPlayer)
        val tvDate = findViewById<TextView>(R.id.tvDate)
        val tvStart = findViewById<TextView>(R.id.tvStart)
        val tvEnd = findViewById<TextView>(R.id.tvEnd)
        val endBtn = findViewById<Button>(R.id.endBtn)
        val doneBtn = findViewById<Button>(R.id.done)

//
//        // get data from intent
        val city = intent.getStringExtra("city").toString()
        val center = intent.getStringExtra("center").toString()
        val court = intent.getStringExtra("court").toString()
        val player = intent.getStringExtra("player").toString()
        val date = intent.getStringExtra("date").toString()
        var start = intent.getStringExtra("start").toString()
        var end = intent.getStringExtra("end").toString()

        // get data from intent
        tvCity.text = city
        tvCenter.text = center
        tvCourt.text = court
        tvPlayer.text = player
        tvDate.text = date
        tvStart.text = start
        tvEnd.text = end
//

//        --------------------------------------------------------------------------

//
        var startSlot = start
        var endSlot = end

        // initialize the choosen time
        var startTime = startSlot
        var endTime = toTimeString(toMinute(startTime) + 45)

        // initial start and end choosing
        var startForChoosing = ArrayList<String>()
        while (toMinute(startSlot) <= toMinute(endSlot) - 45) {
            startForChoosing.add(startSlot)
            startSlot = toTimeString(toMinute(startSlot) + 1)
        }
        startSpinner.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, startForChoosing)

        startSlot = start

        var endForChoosing = ArrayList<String>()
        if (toMinute(startSlot) + 45 <= toMinute(endSlot)) {
            endForChoosing.add(toTimeString(toMinute(startTime) + 45))
        }   // 45 minutes booking
        if (toMinute(startSlot) + 60 <= toMinute(endSlot)) {
            endForChoosing.add(toTimeString(toMinute(startTime) + 60))
        }   // 60 minutes booking
        if (toMinute(startSlot) + 75 <= toMinute(endSlot)) {
            endForChoosing.add(toTimeString(toMinute(startTime) + 75))
        }   // 75 minutes booking
        if (toMinute(startSlot) + 90 <= toMinute(endSlot)) {
            endForChoosing.add(toTimeString(toMinute(startTime) + 90))
        }   // 90 minutes booking
        endSpinner.adapter = ArrayAdapter<String>(
            this@CreateBookingActivity,
            android.R.layout.simple_list_item_1,
            endForChoosing
        )

        // On startTime Choosing Listener
        startSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { /* Just a place holder */
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                startTime = startForChoosing[position] // For testing
                endTime = toTimeString(toMinute(startTime) + 45)
                endForChoosing = ArrayList<String>()
                if (toMinute(startTime) + 45 <= toMinute(endSlot)) {
                    endForChoosing.add(toTimeString(toMinute(startTime) + 45))
                }   // 45 minutes booking
                if (toMinute(startTime) + 60 <= toMinute(endSlot)) {
                    endForChoosing.add(toTimeString(toMinute(startTime) + 60))
                }   // 60 minutes booking
                if (toMinute(startTime) + 75 <= toMinute(endSlot)) {
                    endForChoosing.add(toTimeString(toMinute(startTime) + 75))
                }   // 75 minutes booking
                if (toMinute(startTime) + 90 <= toMinute(endSlot)) {
                    endForChoosing.add(toTimeString(toMinute(startTime) + 90))
                }   // 90 minutes booking

                endSpinner.adapter = ArrayAdapter<String>(
                    this@CreateBookingActivity,
                    android.R.layout.simple_list_item_1,
                    endForChoosing
                )
            }
        }
        // On endTime Choosing Listener
        endSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { /* Just a place holder */
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                endTime = endForChoosing[position] // For testing

            }
        }


//        --------------------------------------------------------------------------


        doneBtn.setOnClickListener {
            //Toast.makeText(this, "Selected: $startTime - $endTime", Toast.LENGTH_SHORT).show()
            // convert to right fornat
            end = Time.valueOf(endTime + ":00").toString()
            start = Time.valueOf(startTime + ":00").toString()
            Toast.makeText(this, "Selected: ~$start~ - ~$end~", Toast.LENGTH_SHORT).show()
            requestCreateBooking(date, start, end, city, center, court, player)
        }
    }

    // request cancel booking
    private fun requestCreateBooking(
        pdate: String,
        pstarttime: String,
        pendtime: String,
        pcityid: String,
        pcenterid: String,
        pcourtid: String,
        pplayerid: String
    ) {
        var bookingObj = JSONObject()
        bookingObj.put("pdate", pdate)
        bookingObj.put("pstarttime", pstarttime)
        bookingObj.put("pendtime", pendtime)
        bookingObj.put("pcityid", pcityid)
        bookingObj.put("pcenterid", pcenterid)
        bookingObj.put("pcourtid", pcourtid)
        bookingObj.put("pplayerid", pplayerid)

        // Get a RequestQueue
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, ApiUtils.URL_BOOKING_CREATE, bookingObj,
            Response.Listener { response ->
                val result_code = response.getString("code")
                Toast.makeText(this, "Booking created. $result_code", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    this,
                    "Create Booking Error: #${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun toMinute(time: String): Int {
        val timeSplit = time.split(":")
        val hour = timeSplit[0].toInt()
        val minute = timeSplit[1].toInt()
        return (hour * 60 + minute)
    }

    fun toTimeString(time: Int): String {
        val hour = floor(time.toDouble() / 60).toInt().toString()
        val minute = (time.toDouble() % 60).toInt().toString()

        var result = String()
        if (minute.toInt() < 10) {
            result = "$hour:0$minute"
        } else {
            result = "$hour:$minute"
        }

        return result
    }
}
