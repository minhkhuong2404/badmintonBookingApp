package com.example.courtbooking

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.courtbooking.request.ApiUtils
import com.example.courtbooking.request.MySingleton
import com.facebook.AccessToken
import kotlinx.android.synthetic.main.activity_selection.*
import java.util.*
import kotlin.collections.ArrayList


class SelectionActivity : AppCompatActivity() {

    // View vars
    lateinit var cityChooser : Spinner
    lateinit var dateChooser : EditText

    // Chooser view var
    lateinit var selectedCity: String   // for later choosing
    lateinit var selectedDate: String   // for later choosing


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        // Setting views
        cityChooser = findViewById<Spinner>(R.id.sp_city)
        dateChooser = findViewById<EditText>(R.id.et_date)

        // On button clicked Show Available Slots
        b_show_slots.setOnClickListener {
            // Prepare intent
            val toCitySlotActivity = Intent(this@SelectionActivity, CitySlotActivity::class.java)
            toCitySlotActivity.putExtra("city", selectedCity)
            toCitySlotActivity.putExtra("date", selectedDate)
            // To next activity
            startActivity(toCitySlotActivity)
        }

        // On button clicked Show My Bookings
        b_show_bookings.setOnClickListener {
            // Prepare intent
            val toUserBookingActivity =
                Intent(this@SelectionActivity, UserBookingActivity::class.java)
            toUserBookingActivity.putExtra("city", selectedCity)
            toUserBookingActivity.putExtra("date", selectedDate)
            // To next activity
            startActivity(toUserBookingActivity)
        }

        // Request city from server then set city spinner
        requestCityList()
        // Set date picker, today is default
        setDatePicker()

        Toast.makeText(this, AccessToken.USER_ID_KEY, Toast.LENGTH_SHORT).show()
    }


    // set date chooser
    private fun setDatePicker() {
        // Get current date
        val cal = Calendar.getInstance(TimeZone.getDefault())
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        // Set default as current day
        // Show on view
        et_date.setText(dateConvert(day, month, year, '/'))
        selectedDate = dateConvert(day, month, year, '-')

        // On click of date chooser
        dateChooser.setOnClickListener {
            // Setting up date picker dialog
            val datePicker = DatePickerDialog(
                this@SelectionActivity,
                DatePickerDialog.OnDateSetListener { _: DatePicker?, mYear: Int, mMonth: Int, mDay: Int ->
                    // Add 1, since in Kotlin start from 0 - 11
                    val mFinalMonth: Int = mMonth + 1;

                    // Set result to EditText
                    et_date.setText(dateConvert(mDay, mMonth, mYear, '/'))
                    selectedDate = dateConvert(mDay, mMonth, mYear, '-')
                },
                year,
                month,
                day
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

    // request city list
    private fun requestCityList() {
        var cityList: ArrayList<String> = ArrayList()

        // Get a RequestQueue
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, ApiUtils.URL_GET_CITY_ALL, null,
            Response.Listener { response ->
                Log.i("city response", "Response: %s".format(response.toString()))
                for (i in 0 until response.length()) {
                    cityList.add(response.get(i).toString())
                }
                // set city chooser
                setCitySpinner(cityList)
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
                Log.i("city response", "Response: %s".format(error.message))
            }
        )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest)
    }

    // set city spinner
    private fun setCitySpinner(cityList: ArrayList<String>) {
        // Show first item on the cities array on the chosenCity spinner
        cityChooser.adapter = ArrayAdapter<String>(
            this@SelectionActivity,
            android.R.layout.simple_list_item_1,
            cityList
        )

        // On City Choosing Listener
        cityChooser.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { /* Just a place holder */
            }

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                selectedCity = cityList[position]
            }
        }
    }

//    //
//    fun loadUserId(newAccessToken: AccessToken) {
//        val request = GraphRequest.newMeRequest(newAccessToken, object : GraphRequest.GraphJSONObjectCallback {
//            override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
//                try {
//                    userId = `object`?.getString("id").toString()
//                } catch(e: JSONException) {
//                    e.printStackTrace()
//                }
//            } // end onCompleted
//
//        })
//
//        val parameter = Bundle()
//        parameter.putString("fields", "id")
//        request.parameters = parameter
//        request.executeAsync()
//    }
}




