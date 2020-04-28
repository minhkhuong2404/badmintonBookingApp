package com.example.courtbooking

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.courtbooking.request.ApiUtils
import com.example.courtbooking.request.MySingleton
import org.json.JSONArray
import org.json.JSONObject

class CenterActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_center)

        val centerId = intent.getStringExtra("centerId").toString()

        findViewById<TextView>(R.id.centerIdTextView).text = "Center $centerId"

//        requestCenterHoliday(centerId)
//        requestCenterOpeningHour(centerId)


    }


    // request center holiday
    private fun requestCenterHoliday(centerId: String) {
        // Preparing query
        var query = "?id=$centerId"

        // Get a RequestQueue
        val jsonArrayRequest =
            JsonArrayRequest(
                Request.Method.GET, ApiUtils.URL_GET_CENTER_HOLIDAY + query, null,
                Response.Listener { response ->
                    Log.i("Center Holiday", "Response: %s".format(response.toString()))

                    var holidayList = ""
                    var a = response.toString()

                    var centerHolidayObj = JSONArray(response.toString())
                    for (i in 0 until centerHolidayObj.length()) {
                        holidayList += centerHolidayObj[i].toString() + " "
                    }

                    findViewById<TextView>(R.id.centerHolidayTextView).text = holidayList


                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Cannot connect to server.", Toast.LENGTH_SHORT).show()
                }
            )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest)
    }

    // request center opening hour
    private fun requestCenterOpeningHour(centerId: String) {
        // Preparing query
        var query = "?id=$centerId"

        // Get a RequestQueue
        val jsonObjectRequest =
            JsonObjectRequest(
                Request.Method.GET, ApiUtils.URL_GET_CENTER_OPENING_HOUR + query, null,
                Response.Listener { response ->
                    Log.i("Center Opening hour", "Response: %s".format(response.toString()))

                    var openingList = ""
                    var a = response.toString()

                    var centerOpeningObj = JSONObject(response.toString())
                    var centerOpeningKey = centerOpeningObj.keys()
                    while(centerOpeningKey.hasNext()) {
                        var openingKey = centerOpeningKey.next()
                        var openingHour = centerOpeningObj.get(openingKey)
                        openingList += openingKey + ": " + openingHour + "\n"
                    }

                    //findViewById<TextView>(R.id.centerOpeningTextView).text = openingList

                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Cannot connect to server.", Toast.LENGTH_SHORT).show()
                }
            )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

//    // request center opening hour
//    private fun requestCenterMinMax(centerId: String) {
//        // Preparing query
//        var query = "?id=$centerId"
//
//        // Get a RequestQueue
//        val jsonArrayRequest =
//            JsonArrayRequest(
//                Request.Method.GET, ApiUtils.URL_GET_CENTER_INFO + query, null,
//                Response.Listener { response ->
//                    Log.i("Center Opening hour", "Response: %s".format(response.toString()))
//
//                    var minmaxList = ""
//                    var a = response.toString()
//
//                    var centerOpeningObj = JSONObject(response.toString())
//                    var centerOpeningKey = centerOpeningObj.keys()
//                    while(centerOpeningKey.hasNext()) {
//                        var openingKey = centerOpeningKey.next()
//                        var openingHour = centerOpeningObj.get(openingKey)
//                        openingList += openingKey + ": " + openingHour + "\n"
//                    }
//
//                    findViewById<TextView>(R.id.centerOpeningTextView).text = openingList
//
//
//                },
//                Response.ErrorListener { error ->
//                    Toast.makeText(this, "Cannot connect to server.", Toast.LENGTH_SHORT).show()
//                }
//            )
//
//        // Access the RequestQueue through your singleton class.
//        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest)
//    }
}