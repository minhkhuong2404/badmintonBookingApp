package com.example.courtbooking

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.courtbooking.request.ApiUtils
import com.example.courtbooking.request.MySingleton

class CenterActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_center)

        val centerId = intent.getStringExtra("centerId").toString()

        requestCenterInfo(centerId)


    }

    // request player booking list
    private fun requestCenterInfo(centerId:  String) {
        // Preparing query
        var query = "?id=$centerId"

        // Get a RequestQueue
        val jsonArrayRequest =
            JsonArrayRequest(
                Request.Method.GET, ApiUtils.URL_GET_CENTER_INFO + query, null,
                Response.Listener { response ->
                    Log.i("Player Booking", "Response: %s".format(response.toString()))
                    // write to text view
                    findViewById<TextView>(R.id.centerInfoTextView).text = response.toString()

                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Cannot connect to server.", Toast.LENGTH_SHORT).show()
                }
            )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest)
    }
}