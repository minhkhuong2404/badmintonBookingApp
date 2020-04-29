package com.example.courtbooking

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.courtbooking.request.ApiUtils
import com.example.courtbooking.request.MySingleton
import com.facebook.AccessToken
import org.json.JSONObject
import java.sql.Time


class CreateBookingActivity : AppCompatActivity() {
    lateinit var accessToken: AccessToken

    /* Constants */
    val PLAYTIME_45M = 45
    val PLAYTIME_1H = 60
    val PLAYTIME_1H15 = 75
    val PLAYTIME_1H30 = 90
    val ALL_PLAYTIME_DURATION = listOf(PLAYTIME_45M, PLAYTIME_1H, PLAYTIME_1H15, PLAYTIME_1H30)
    var PLAYTIME_MINIMUM = PLAYTIME_45M
    var chosenStartTime = -1 // initialize
    var chosenPlaytime = -1 // initialize
    var chosenEndTime = -1 // initialize
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_booking)

        /* get data from intent */
        val city = intent.getStringExtra("city")
        val center = intent.getStringExtra("center")
        val court = intent.getStringExtra("court")
        val player = intent.getStringExtra("player")
        val date = intent.getStringExtra("date")
        val slotStartString = intent.getStringExtra("start")
        val slotEndString = intent.getStringExtra("end")
        val slotStartTime = Time.valueOf(slotStartString.toString())
        val slotEndTime = Time.valueOf(slotEndString.toString())
        val slotStartInt = toMinute(slotStartTime)
        val slotEndInt = toMinute(slotEndTime)

        /* Get reference to views */
        val tvStart = findViewById<TextView>(R.id.tvStart)
        val tvEnd = findViewById<TextView>(R.id.tvEnd)
        val chooseStartButton = findViewById<Button>(R.id.chooseStartBtn)
        val timePickerTextview = findViewById<TextView>(R.id.timepickerTextView)
        val timepicker = findViewById<TimePicker>(R.id.timepicker)
        val timepickerCloseButton = findViewById<Button>(R.id.timepickerCloseBtn)
        val timepickerOkButton = findViewById<Button>(R.id.timepickerChooseBtn)
        val timePickerLayout = findViewById<ConstraintLayout>(R.id.timepickerLayout)
        val createBookingButton = findViewById<Button>(R.id.createBookingBtn)
        val durationSpinner = findViewById<Spinner>(R.id.durationSpinner)

        /* Bind data to views */
        findViewById<TextView>(R.id.tvCity).text = city
        findViewById<TextView>(R.id.tvCenter).text = center
        findViewById<TextView>(R.id.tvCourt).text = court
        findViewById<TextView>(R.id.tvPlayer).text = player
        findViewById<TextView>(R.id.tvDate).text = date

        /* default things with play time */
        chosenStartTime = toMinute(slotStartTime) // slot start time as default
        chosenPlaytime = PLAYTIME_MINIMUM // default playtime is minimum playtime
        chosenEndTime = toMinute(slotStartTime) + PLAYTIME_MINIMUM // slot end time as start time + PLAYTIME_MINIMUM
        tvStart.text = minuteToString(chosenStartTime) // takes "hh:mm" only
        tvEnd.text = minuteToString(chosenEndTime) // takes "hh:mm" only


        /* Setting button */
        chooseStartButton.setOnClickListener {
            timePickerLayout.visibility = View.VISIBLE
            timepicker.hour = chosenStartTime / 60
            timepicker.minute = chosenStartTime % 60
            setTimePicker(timepicker, timePickerTextview, slotStartTime, slotEndTime, PLAYTIME_MINIMUM)
        }
        timepickerCloseButton.setOnClickListener {
            timePickerLayout.visibility = View.GONE
        }
        timepickerOkButton.setOnClickListener {
            chosenStartTime = timepicker.hour * 60 + timepicker.minute
            timePickerLayout.visibility = View.GONE
            tvStart.text = minuteToString(chosenStartTime)
            chosenEndTime = chosenStartTime + chosenPlaytime
            tvEnd.text = minuteToString(chosenEndTime)
            // recalculate duration list
            var duration_list = ArrayList<String>()
            for (i in ALL_PLAYTIME_DURATION){
                if (chosenStartTime + i <= slotEndInt){
                    duration_list.add(i.toString())
                }
            }
            durationSpinner.adapter =
                ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    duration_list
                )
        }
        createBookingButton.setOnClickListener {
            // convert to right fornat
            val start = minuteToString(chosenStartTime) + ":00"
            val end = minuteToString(chosenEndTime) + ":00"
            Toast.makeText(this, "Created: $start - $end", Toast.LENGTH_SHORT).show()
            requestCreateBooking(date, start, end, city, center, court, player)
        }
        durationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) { /* Just a place holder */ }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                chosenEndTime = chosenStartTime + durationSpinner.selectedItem.toString().toInt()
                tvEnd.text = minuteToString(chosenEndTime)
            }
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

    private fun setTimePicker(
        timePicker: TimePicker,
        timePickerTextview: TextView,
        slotStartTime: Time,
        slotEndTime: Time,
        SLOT_MIN_LENGTH: Int
    ) {
        val start = toMinute(slotStartTime)
        val end = toMinute(slotEndTime)
        val latest_start = end - SLOT_MIN_LENGTH
        timePickerTextview.text = "Select a time in range of ${slotStartTime.toString().subSequence(0, 5)} to ${minuteToString(latest_start)}"
        timePicker.setIs24HourView(true)
        timePicker.setOnTimeChangedListener { _, hour, minute ->
            var selecting = toMinute(hour, minute)
            if (selecting < start) {
                selecting = start
            } else if (selecting > latest_start) {
                selecting = latest_start
            }
            timePicker.hour = selecting / 60
            timePicker.minute = selecting % 60
        }
    }
    private fun minuteToString(time : Int) : String{
        val hour = time / 60
        val min = time % 60
        val hour_s = hour.toString().padStart(2, '0')
        val min_s = min.toString().padStart(2, '0')
        return hour_s + ':' + min_s
    }

    private fun toMinute(hour: Int, minute: Int): Int {
        return hour * 60 + minute
    }

    private fun toMinute(time: Time): Int {
        val hour = time.hours
        val minute = time.minutes % 60
        return hour * 60 + minute
    }
//    private fun getPlaytimeList()
}
