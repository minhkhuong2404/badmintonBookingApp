package com.example.courtbooking

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.courtbooking.request.ApiUtils
import com.example.courtbooking.request.MySingleton
import com.facebook.AccessToken
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_create_booking.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalTime


class CreateBookingActivity : AppCompatActivity() {
    lateinit var accessToken: AccessToken
    lateinit var playerId : String
    lateinit var cityId : String
    lateinit var date : String
    /* Constants */
    private val PLAYTIME_45M = 45
    private val PLAYTIME_1H = 60
    private val PLAYTIME_1H15 = 75
    private val PLAYTIME_1H30 = 90
    private val ALL_PLAYTIME_DURATION = listOf(PLAYTIME_45M, PLAYTIME_1H, PLAYTIME_1H15, PLAYTIME_1H30)
    private var PLAYTIME_MINIMUM = PLAYTIME_45M
    private var chosenStartTime = -1 // initialize
    private var chosenPlaytime = -1 // initialize
    private var chosenEndTime = -1 // initialize
    private var chosenCard = ""
    private var cardList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_booking)

        /* get data from intent */
        cityId = intent.getStringExtra("city")
        playerId = intent.getStringExtra("player")
        date = intent.getStringExtra("date")
        val center = intent.getStringExtra("center")
        val court = intent.getStringExtra("court")
        val slotStartString = intent.getStringExtra("start")
        val slotEndString = intent.getStringExtra("end")
        val slotStartTime = LocalTime.parse(slotStartString)
        val slotEndTime = LocalTime.parse(slotEndString)
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
        val durationSpinner = findViewById<Spinner>(R.id.durationSpinner)
        val createBookingButton = findViewById<Button>(R.id.createBookingBtn)
        val toCreatedBookingButton = findViewById<Button>(R.id.toCreatedBookingBtn)
        val cardSpinner = findViewById<Spinner>(R.id.cardSpinner)

        /* Bind data to views */
        findViewById<TextView>(R.id.tvCity).text = cityId
        findViewById<TextView>(R.id.tvCenter).text = center
        findViewById<TextView>(R.id.tvCourt).text = court
        findViewById<TextView>(R.id.tvPlayer).text = playerId
        findViewById<TextView>(R.id.tvDate).text = date
        findViewById<TextView>(R.id.tvSlot).text = slotStartString.subSequence(0,5).toString() + " - " + slotEndString.subSequence(0,5).toString()

        /* default things with play time */
        chosenStartTime = toMinute(slotStartTime) // slot start time as default
        chosenPlaytime = PLAYTIME_MINIMUM // default playtime is minimum playtime
        chosenEndTime = toMinute(slotStartTime) + PLAYTIME_MINIMUM // slot end time as start time + PLAYTIME_MINIMUM
        tvStart.text = minuteToString(chosenStartTime) // takes "hh:mm" only
        tvEnd.text = minuteToString(chosenEndTime) // takes "hh:mm" only

        /* request list of card*/
        cardRequest(playerId, date)

        cardSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cardList)
        cardSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) { /* Just a place holder */ }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                chosenCard = cardSpinner.selectedItem.toString().split(" ").get(1)
            }
        }
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
            val durationList = ArrayList<String>()
            for (i in ALL_PLAYTIME_DURATION){
                if (chosenStartTime + i <= slotEndInt){
                    durationList.add(i.toString())
                }
            }
            durationSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, durationList)

        }
        createBookingButton.setOnClickListener {
            // convert to right fornat
            val start = minuteToString(chosenStartTime) + ":00"
            val end = minuteToString(chosenEndTime) + ":00"
            requestCreateBooking(date, start, end, cityId, center, court, playerId, chosenCard.toInt())

        }
        toCreatedBookingButton.setOnClickListener {
            // Prepare intent
            val toPlayerBookingActivity =
                Intent(this@CreateBookingActivity, PlayerBookingActivity::class.java)
            toPlayerBookingActivity.putExtra("player", this.playerId)
            toPlayerBookingActivity.putExtra("city", cityId)
            toPlayerBookingActivity.putExtra("date", date)
            // Turn to PlayerBookingActivity
            startActivity(toPlayerBookingActivity)
        }
        durationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) { /* Just a place holder */ }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                chosenEndTime = chosenStartTime + durationSpinner.selectedItem.toString().toInt()
                tvEnd.text = minuteToString(chosenEndTime)
            }
        }
    }

    // request create booking
    private fun requestCreateBooking(
        pdate: String,
        pstarttime: String,
        pendtime: String,
        pcityid: String,
        pcenterid: String,
        pcourtid: String,
        pplayerid: String,
        chosenCard: Int
    ) {
        val bookingObj = JSONObject()
        bookingObj.put("pdate", pdate)
        bookingObj.put("pstarttime", pstarttime)
        bookingObj.put("pendtime", pendtime)
        bookingObj.put("pcityid", pcityid)
        bookingObj.put("pcenterid", pcenterid)
        bookingObj.put("pcourtid", pcourtid)
        bookingObj.put("pplayerid", pplayerid)
        bookingObj.put("pcardid", chosenCard)
        // Get a RequestQueue
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, ApiUtils.URL_BOOKING_CREATE, bookingObj,
            Response.Listener { response ->
                when (response.getString("code")) {
                    "200" -> {
                        val toast = Toast.makeText(this, "Booking successfully created.", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0); toast.show()
                        createBookingBtn.visibility = View.GONE
                        toCreatedBookingBtn.visibility = View.VISIBLE
                    }
                    "CB-005" -> {
                        val toast = Toast.makeText(this, "Cannot make booking in the past.", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0); toast.show()
                    }
                    "CB-006" -> {
                        val toast = Toast.makeText(this, "Create failed: start time is before open time.", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0); toast.show()
                    }
                    "CB-007" -> {
                        val toast = Toast.makeText(this, "Create failed: end time is after close time.", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0); toast.show()
                    }
                    "CB-008" -> {
                        val toast = Toast.makeText(this, "Create failed: end time is before open time.", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0); toast.show()
                    }
                    "CB-009" -> {
                        val toast = Toast.makeText(this, "Create failed: playtime is invalid.", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0); toast.show()
                    }
                    "CB-010" -> {
                        val toast = Toast.makeText(this, "Create failed: overlapped with another booking.", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0); toast.show()
                    }
                    "CB-011" -> {
                        val toast = Toast.makeText(this, "Create failed: you have an unpaid booking.", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0); toast.show()
                    }
                    "CB-012" -> {
                        val toast = Toast.makeText(this, "Create failed: cannot make more than 3 bookings in advance.", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0); toast.show()
                    }
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Create Booking Error: #${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    private fun setTimePicker(
        timePicker: TimePicker,
        timePickerTextview: TextView,
        slotStartTime: LocalTime,
        slotEndTime: LocalTime,
        SLOT_MIN_LENGTH: Int
    ) {
        val start = toMinute(slotStartTime)
        val end = toMinute(slotEndTime)
        val latestStart = end - SLOT_MIN_LENGTH
        timePickerTextview.text = "Select a time in range of ${slotStartTime.toString().subSequence(0, 5)} to ${minuteToString(latestStart)}"
        timePicker.setIs24HourView(true)
        timePicker.setOnTimeChangedListener { _, hour, minute ->
            var selecting = toMinute(hour, minute)
            if (selecting < start) {
                selecting = start
            } else if (selecting > latestStart) {
                selecting = latestStart
            }
            timePicker.hour = selecting / 60
            timePicker.minute = selecting % 60
        }
    }
    private fun minuteToString(time : Int) : String{
        val hour = time / 60
        val min = time % 60
        val hourS = hour.toString().padStart(2, '0')
        val minS = min.toString().padStart(2, '0')
        return "$hourS:$minS"
    }

    private fun toMinute(hour: Int, minute: Int): Int {
        return hour * 60 + minute
    }

    private fun toMinute(time: LocalTime): Int {
        return time.hour * 60 + time.minute
    }
//    private fun requestMinLengthPlaytime(){}
    private fun cardRequest(playerId : String, date: String){
        val query = "?playerId=$playerId&date=$date"

    val JsonArray =
        JsonArrayRequest(
            Request.Method.GET, ApiUtils.URL_GET_CARD + query, null,
            Response.Listener { response ->
                for (i in 0 until response.length()){
                    var currentCard = response.getJSONObject(i)
                    var card = ""
                    card += "card id: " + (currentCard.getString("id").toString())
                    val expire_date = currentCard.getString("expire_date").toLong()

                    val dateChosen = SimpleDateFormat("dd-MM-yyyy").parse(date)
                    val validCard = (expire_date - dateChosen.time)/60/60/24/1000

                    card += "days left: $validCard"
                    card += "remained booking: " + (currentCard.getString("remainBooking").toString())

                    cardList.add(card)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Cannot connect to server.", Toast.LENGTH_SHORT).show()
            }
        )

    // Access the RequestQueue through your singleton class.
    MySingleton.getInstance(this).addToRequestQueue(JsonArray)
    }
}
