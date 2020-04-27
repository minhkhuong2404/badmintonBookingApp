package com.example.courtbooking

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.courtbooking.request.ApiUtils
import com.example.courtbooking.request.MySingleton
import com.facebook.AccessToken
import kotlinx.android.synthetic.main.activity_selection.*
import org.json.JSONArray
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class SelectionActivity : AppCompatActivity() {
    // cache file name
    var cacheFilename = "city.json"
    // User vars
    lateinit var userId : String
    lateinit var accessToken: AccessToken
    // View
    lateinit var citySpinner: Spinner
    lateinit var datePicker: EditText

    // Chosen city & date
    var selectedCity: String = "" // for later choosing
    var selectedDate: String = ""   // for later choosing

    // Get current date
    val cal = Calendar.getInstance(TimeZone.getDefault())
    var selectedYear = cal.get(Calendar.YEAR)
    var selectedMonth = cal.get(Calendar.MONTH)
    var selectedDay = cal.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
        // Setting views
        citySpinner = findViewById<Spinner>(R.id.sp_city)
        datePicker = findViewById<EditText>(R.id.et_date)

        // Get token & user id
        accessToken = AccessToken.getCurrentAccessToken()
        userId = accessToken.userId

        // On button clicked Show Available Slots
        b_show_slots.setOnClickListener {
            if (isNetworkAvailable(this)) {
                // check city spinner empty
                if (citySpinner.count == 0){
                    // try to load city list again
                    requestCityList()
                }
                // if user has not chosen city
                if (selectedCity.length == 0) {
                    Toast.makeText(this, "Please select a city.", Toast.LENGTH_SHORT).show()
                } else {
                    // request player booking to server
                    requestCitySlot(selectedCity, selectedDate)
                    Log.i("City Slot", "Request called.")
                }
            } else {
                Toast.makeText(this, "No internet connection.", Toast.LENGTH_SHORT).show()
            }
        }

        // On button clicked Show My Bookings
        b_show_bookings.setOnClickListener {
            // Check internet connection
            if (isNetworkAvailable(this)) {
                // check city spinner empty
                if (citySpinner.count == 0){
                    // try to load city list again
                    requestCityList()
                }
                // if user has not chosen city
                if (selectedCity.length == 0) {
                    Toast.makeText(this, "Please select a city.", Toast.LENGTH_SHORT).show()
                } else {
                    // request player booking to server
                    requestPlayerBooking("player1", selectedCity, selectedDate)
                    Log.i("Button Booking", "Request called.")
                }
            } else {
                Toast.makeText(this, "No internet connection.", Toast.LENGTH_SHORT).show()
            }
        }

        // Request city from server (or get from cached) then set city spinner
        requestCityList()

        // Set date picker, today is default
        setDatePicker()

//        Toast.makeText(this, "UserID: $userId", Toast.LENGTH_SHORT).show()
    }

    fun isNetworkAvailable(activity: AppCompatActivity): Boolean {
        val connectivityManager =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    // set date chooser
    private fun setDatePicker() {

        // Show on view
        et_date.setText(dateConvert(selectedDay, selectedMonth, selectedYear, '/'))
        selectedDate = dateConvert(selectedDay, selectedMonth, selectedYear, '-')

        // On click of date chooser
        datePicker.setOnClickListener {
            // Setting up date picker dialog
            val datePicker = DatePickerDialog(
                this@SelectionActivity,
                DatePickerDialog.OnDateSetListener { _: DatePicker?, mYear: Int, mMonth: Int, mDay: Int ->
                    // Add 1, since in Kotlin start from 0 - 11
                    val mFinalMonth: Int = mMonth + 1;

                    // update select date
                    selectedDay = mDay
                    selectedMonth = mMonth
                    selectedYear = mYear

                    // Show on view
                    et_date.setText(dateConvert(selectedDay, selectedMonth, selectedYear, '/'))
                    selectedDate = dateConvert(selectedDay, selectedMonth, selectedYear, '-')
                },
                selectedYear,
                selectedMonth,
                selectedDay
            )
            //datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
            // Show Date Dialog
            datePicker.show()
        }
    }

    // convert date to string
    private fun dateConvert(day : Int, month : Int, year : Int, separator : Char) : String {
        val mFinalMonth = month + 1
        val dayText: String
        val monthText: String
        if (day < 10) {
            dayText = "0$day";
        } else {
            dayText = "$day"
        }
        if (mFinalMonth < 10) {
            monthText = "0$mFinalMonth";
        } else {
            monthText = "$mFinalMonth"
        }
        if (separator == '-') {
            return "$year-$monthText-$dayText"
        }
        return "$dayText/$monthText/$year"
    }

    // set city spinner
    private fun setCitySpinner(cityJSONArray: JSONArray) {
        // if city list not empty, update the spinner
        var cityList = ArrayList<String>()
        for (i in 0 until cityJSONArray.length()) {
            cityList.add(cityJSONArray.get(i).toString())
        }
        // Show first item on the cities array on the chosenCity spinner
        citySpinner.adapter = ArrayAdapter<String>(
            this@SelectionActivity,
            android.R.layout.simple_list_item_1,
            cityList
        )
        // On City Choosing Listener
        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { /* Just a place holder */
            }

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                selectedCity = cityList[position]
            }
        }
    }
    // request city list
    private fun requestCityList() {
        var cityList: ArrayList<String> = ArrayList()

        // Get a RequestQueue
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, ApiUtils.URL_GET_CITY_ALL, null,
            Response.Listener { response ->
                if (response.length() > 0) {
                    // set city chooser
                    setCitySpinner(response)
                    // cache city list
                    cacheCity(response)
                } else {
                    Toast.makeText(this, "Received no city at all.", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                // load cache city list
                var cached_cityJSONArray : JSONArray? = loadCacheCity()

                // if cache not existed
                if (cached_cityJSONArray == null) {
                    Toast.makeText(this, "Unable to load cities.", Toast.LENGTH_SHORT).show()
                } else {
                    // use cache to set city chooser
                    setCitySpinner(cached_cityJSONArray)
                }
            }
        )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest)
    }

    // request available slot
    private fun requestCitySlot(cityid: String, date: String) {
        // Preparing query
        var query = "?id=$cityid&date=$date"

        // Get a RequestQueue
        val jsonArrayRequest =
            JsonObjectRequest(
                Request.Method.GET, ApiUtils.URL_GET_CITY_SLOT + query, null,
                Response.Listener { response ->
                    Log.i("City Slot", "Response: %s".format(response.toString()))
                    // Prepare intent
                    val toCitySlotActivity =
                        Intent(this@SelectionActivity, CitySlotActivity::class.java)
                    toCitySlotActivity.putExtra("city", selectedCity)
                    toCitySlotActivity.putExtra("date", selectedDate)
                    toCitySlotActivity.putExtra("jsonString", response.toString())
                    // Turn to CitySlotActivity
                    startActivity(toCitySlotActivity)
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Cannot connect to server.", Toast.LENGTH_SHORT).show()
                }
            )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest)
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
                    // Prepare intent
                    val toPlayerBookingActivity =
                        Intent(this@SelectionActivity, PlayerBookingActivity::class.java)
                    toPlayerBookingActivity.putExtra("city", selectedCity)
                    toPlayerBookingActivity.putExtra("date", selectedDate)
                    toPlayerBookingActivity.putExtra("jsonString", response.toString())
                    // Turn  PlayerBookingActivit
                    startActivity(toPlayerBookingActivity)
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Cannot connect to server.", Toast.LENGTH_SHORT).show()
                }
            )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest)
    }

    // cache city list
    fun cacheCity(cityJSONArray: JSONArray) {
        // prepare content
        val json = cityJSONArray.toString()
        // prepare cache file
        File.createTempFile(cacheFilename, null, this.cacheDir)
        val cacheFile = File(this.cacheDir, cacheFilename)
        // write cache
        cacheFile.writeText(json, Charsets.UTF_8)
    }

    // load city list from cache
    fun loadCacheCity(): JSONArray? {
        // read cache
        val cacheFile = File(this.cacheDir, cacheFilename)
        if (cacheFile.exists()) {
            val json = cacheFile.readText(Charsets.UTF_8)
            return JSONArray(json) // return city json array
        } else return null // return null city list
    }
}




