package com.example.mybooking2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class AvailableSlot : AppCompatActivity() {
    var place = "Here"
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.available_slot)

        val cityList = arrayOf("Ho Chi Minh", "Binh Duong", "Ca Mau", "Dong Nai", "Ben Tre", "Dong Thap")
        val listOfCity = findViewById<Spinner>(R.id.listOfCity)
        if (listOfCity != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cityList)
            listOfCity.adapter = arrayAdapter
            listOfCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    Toast.makeText(this@AvailableSlot, getString(R.string.select_a_city) + " " + cityList[position], Toast.LENGTH_SHORT).show()
                    place = cityList[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                    val selectedPlace = intent.getStringExtra("Place")
                    Toast.makeText(this@AvailableSlot, getString(R.string.select_a_city) + " " + selectedPlace, Toast.LENGTH_SHORT).show()
                }
            }
        }

        showAvailableSlot.setOnClickListener {

            val available = Intent(this,AvailableSlot::class.java)
            available.putExtra("Place",place)
            startActivity(available)
        }
        showMyBookings.setOnClickListener {
            Toast.makeText(this,"My bookings was clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
