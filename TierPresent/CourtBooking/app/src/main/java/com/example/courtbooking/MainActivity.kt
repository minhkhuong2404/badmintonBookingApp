package com.example.courtbooking

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
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
    }
}
