package com.example.courtbooking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.DatePickerDialog
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var result : TextView
    lateinit var dateChooser : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // findViewById
        dateChooser = findViewById(R.id.et_date) as EditText
        result = findViewById(R.id.tv_result) as TextView
        // Set calendar
        val cal = Calendar.getInstance(TimeZone.getDefault()) // Get current date
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        // Set default as current day
        et_date.setText("" + day + "/" + month + "/" + year)
        result.text = et_date.text
        dateChooser.setOnClickListener {
            val datePicker = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _: DatePicker?, mYear: Int, mMonth: Int, mDay: Int ->
                    // Add 1, since in Kotlin start from 0 - 11
                    val finalMonth: Int = mMonth + 1;
                    // Set result to EditText
                    et_date.setText("" + mDay + "/" + finalMonth + "/" + mYear)
                    result.text = et_date.text;
                },
                year,
                month,
                day
            )
            // Show Date Dialog
            datePicker.show()
        }
    }
}
