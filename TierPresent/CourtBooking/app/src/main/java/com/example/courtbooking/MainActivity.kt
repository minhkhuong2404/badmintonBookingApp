package com.example.courtbooking

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), CenterAdapter.CallbackInterface {
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
        cityChooser = findViewById<Spinner>(R.id.sp_city)
        dateChooser = findViewById<EditText>(R.id.et_date)
        //result = findViewById(R.id.tv_result) as TextView
        //result2 = findViewById(R.id.tv_result2) as TextView

        // City list store here
        val cities = arrayOf("City#A", "City#B", "City#C", "City#D", "City#E") // For testing

        // Show first item on the cities array on the chosenCity spinner

        cityChooser.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cities)

        // On City Choosing Listener

        cityChooser.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) { /* Just a place holder */ }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                result.text = cities[position] // For testing
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
            datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
            // Show Date Dialog
            datePicker.show()

        }

        // On button clicked Show Available Slots
        b_show_slots.setOnClickListener {
            val selectedCity = cityChooser.selectedItem
            val randomCenter = cities.indexOf(selectedCity) + (1..3).random()
            initRecyclerViewVCenter(randomCenter)
        }
        // On button clicked Show My Bookings

        // Initialize the CENTER recycler view
    }
    // Generate fake data for testing recycler view
    private fun getCenterCourtSlotList(size: Int): List<Center> {
        val list = ArrayList<Center>()
        //val exampleCourtList = getCourtList()
        for (i in 0 until size) {
            //val slot = Center("Center#$i", exampleCourtList)
            val slot = Center("Center#$i", getCourtList())
            list += slot
        }

        // change id of
        for (center in list) {
            for (court in center.courtList) {
                for (slot in court.slotList) {
                    slot.id = center.name + "/" + court.name + "/" + slot.id
                }
            }
        }

        return list
    }
    private fun getCourtList(): List<Court> {
<<<<<<< Updated upstream
        return listOf(Court("Court#1", getSlotList(1)), Court("Court#2", getSlotList(2)),
            Court("Court#3", getSlotList(3)), Court("Court#4", getSlotList(4)),Court("Court#5",
                getSlotList(5)
            ))
    }
    private fun getSlotList(type : Int): List<Slot> {

        return when (type) {
            1 -> listOf(Slot("7:00 - 8:00"), Slot("7:30 - 8:30"), Slot("8:30 - 9:00"),
                Slot("9:00 - 10:00"), Slot("10:00 - 11:00"), Slot("12:00 - 13:00"), Slot("13:00 - 21:00"))
            2 -> listOf(Slot("7:30 - 8:30"), Slot("8:30 - 9:00"),
                Slot("9:00 - 10:00"), Slot("10:00 - 11:00"), Slot("12:00 - 13:00"), Slot("13:00 - 21:00"))
            3 -> listOf(Slot("8:30 - 9:00"),
                Slot("9:00 - 10:00"), Slot("10:00 - 11:00"), Slot("12:00 - 13:00"), Slot("13:00 - 21:00"))
            4 -> listOf(Slot("9:00 - 10:00"), Slot("10:00 - 11:00"), Slot("12:00 - 13:00"), Slot("13:00 - 21:00"))
            else -> listOf(Slot("12:00 - 13:00"), Slot("13:00 - 21:00"))
=======
        val list = listOf<Court>(
            Court("Court#1", getSlotList(1).toList()),
            Court("Court#2", getSlotList(2).toList()),
            Court("Court#3", getSlotList(3).toList()),
            Court("Court#4", getSlotList(4).toList()),
            Court("Court#5", getSlotList(5).toList()))
        return list
    }
    private fun getSlotList(type : Int): List<Slot> {

        val slots = when {
            type == 1 -> listOf<Slot>(
                Slot("7:00 - 8:00"),
                Slot("7:30 - 8:30"),
                Slot("8:30 - 9:00"),
                Slot("9:00 - 10:00"),
                Slot("10:00 - 11:00"),
                Slot("12:00 - 13:00"),
                Slot("13:00 - 21:00"))
            type == 2 -> listOf<Slot>(
                Slot("7:30 - 8:30"),
                Slot("8:30 - 9:00"),
                Slot("9:00 - 10:00"),
                Slot("10:00 - 11:00"),
                Slot("12:00 - 13:00"),
                Slot("13:00 - 21:00"))
            type == 3 -> listOf<Slot>(
                Slot("8:30 - 9:00"),
                Slot("9:00 - 10:00"),
                Slot("10:00 - 11:00"),
                Slot("12:00 - 13:00"),
                Slot("13:00 - 21:00"))
            type == 4 -> listOf<Slot>(
                Slot("9:00 - 10:00"),
                Slot("10:00 - 11:00"),
                Slot("12:00 - 13:00"),
                Slot("13:00 - 21:00"))
            else -> listOf<Slot>(
                Slot("12:00 - 13:00"),
                Slot("13:00 - 21:00"))
>>>>>>> Stashed changes
        }
    }
    private fun initRecyclerViewVCenter(numOfCenter: Int){
        // Calling the recycler view for Center
        rv_center.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayout.VERTICAL, false)
            adapter = CenterAdapter(getCenterCourtSlotList(numOfCenter), this@MainActivity)
        }
    }

    // override passDataCallback from CenterAdapter.CallbackInterface
    override fun passDataCallback(message: Slot) {
        Log.i("Main", "Finished")
        Toast.makeText(this, ("You choose " + message.id), Toast.LENGTH_SHORT).show()

        val fm=supportFragmentManager
        val bookingFragment = BookingFragment()
        bookingFragment.show(fm, "Booking Fragment")
    }



}

