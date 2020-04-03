package com.example.courtbooking

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.court.*
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var cityChooser : Spinner
    lateinit var dateChooser : EditText
    lateinit var result : TextView
    lateinit var result2 : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        // findViewById
        cityChooser = findViewById(R.id.sp_city) as Spinner
        dateChooser = findViewById(R.id.et_date) as EditText
        //result = findViewById(R.id.tv_result) as TextView
        //result2 = findViewById(R.id.tv_result2) as TextView

        // City list store here
        var cities = arrayOf("City#A", "City#B", "City#C") // For testing

        // Show first item on the cities array on the chosenCity spinner
        cityChooser.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cities)
        // On City Choosing Listener
        cityChooser.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) { /* Just a place holder */ }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //result.text = cities.get(position) // For testing
            }
        }
        // Set calendar
        val cal = Calendar.getInstance(TimeZone.getDefault()) // Get current date
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        // Set default as current day
        et_date.setText("" + day + "/" + month + "/" + year)
        //result2.text = et_date.text // For testing
        //
        dateChooser.setOnClickListener {
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ _: DatePicker?, mYear: Int, mMonth: Int, mDay: Int ->
                // Add 1, since in Kotlin start from 0 - 11
                val finalMonth : Int = mMonth + 1;
                // Set result to EditText
                et_date.setText("" + mDay + "/" + finalMonth + "/" + mYear)
                //result2.text = et_date.text; // For testing
            }, year, month, day)
            // Show Date Dialog
            datePicker.show()
        }

        // On button clicked Show Available Slots
        // On button clicked Show My Bookings

        /* Test RecyclerView */

        // Create fake data for testing
        //val exampleList = getSlotList(5)
        val exampleList = getCenterList(20)

        rv_center.adapter = CenterAdapter(exampleList)
        rv_center.layoutManager = LinearLayoutManager(this)
        rv_center.setHasFixedSize(true)

        /* End Test RecyclerView*/
    }
    private fun getCenterList(size: Int): List<Center> {
        val list = ArrayList<Center>()

        for (i in 0 until size) {
            val slot = Center("Center#" + i)
            list += slot
        }
        return list
    }
    /* Test RecyclerView
    private fun getSlotList(size: Int): List<Slot> {
        val list = ArrayList<Slot>()

        for (i in 0 until size) {
            val slot = Slot("8:00 - 9:00")
            list += slot
        }
        return list
    }
    End Test RecyclerView */
}
