package com.example.courtbooking

import com.example.courtbooking.*
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var cityChooser : Spinner
    lateinit var dateChooser : EditText
    lateinit var result : TextView
    lateinit var result2 : TextView

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


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

        // On click of date chooser

        dateChooser.setOnClickListener {
            // Setting up date picker dialog
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

        // Initialize the CENTER recycler view
        viewManager = LinearLayoutManager(this)
        viewAdapter = CenterAdapter(getCenterCourtSlotList(27))

        recyclerView = findViewById<RecyclerView>(R.id.rv_center).apply {
            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter
            adapter = viewAdapter
        }
    }
    // Generate fake data for testing recycler view
    private fun getCenterCourtSlotList(size: Int): List<Center> {
        val list = ArrayList<Center>()
        val exampleCourtList = getCourtList()
        for (i in 0 until size) {
            val slot = Center("Center#$i", exampleCourtList)
            list += slot
        }
        return list
    }
    private fun getCourtList(): List<Court> {
        val list = listOf<Court>(Court("Court#1", getSlotList()), Court("Court#2", getSlotList()),
            Court("Court#3", getSlotList()), Court("Court#4", getSlotList()),Court("Court#5", getSlotList()))
        return list
    }
    private fun getSlotList(): List<Slot> {
        val slots = listOf<Slot>(Slot("7:00 - 8:00"), Slot("7:30 - 8:30"), Slot("8:30 - 9:00"),
            Slot("9:00 - 10:00"), Slot("10:00 - 11:00"), Slot("12:00 - 13:00"), Slot("13:00 - 21:00"))
        return slots
    }
}