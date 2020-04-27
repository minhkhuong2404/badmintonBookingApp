package com.example.courtbooking

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courtbooking.adapter.Center
import com.example.courtbooking.adapter.CenterAdapter
import com.example.courtbooking.adapter.Court
import com.example.courtbooking.adapter.Slot
import kotlinx.android.synthetic.main.activity_city_slot.*
import org.json.JSONArray
import org.json.JSONObject

class CitySlotActivity : AppCompatActivity() {
    lateinit var selectedCity: String
    lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_slot)

        selectedCity = intent.getStringExtra("city")
        selectedDate = intent.getStringExtra("date")
        val jsonString = intent.getStringExtra("jsonString")


        var cityObj = JSONObject(jsonString)
        var cityIter = cityObj.keys()

        var centerList = ArrayList<Center>()

        while (cityIter.hasNext()) {
            var centerKey = cityIter.next()
            Log.i("Center", "$centerKey")
            var centerObj = JSONObject(cityObj.get(centerKey).toString())
            var centerIter = centerObj.keys()

            var courtList = ArrayList<Court>()
            while (centerIter.hasNext()) {
                var courtKey = centerIter.next()
                var slotArray = JSONArray(centerObj.get(courtKey).toString())

                var slotList = ArrayList<Slot>()
                for (i in 0 until slotArray.length()) {
                    var slotObj = JSONObject(slotArray[i].toString())
                    var start = slotObj.getString("start").subSequence(0, 5)
                    var end = slotObj.getString("end").subSequence(0, 5)
                    var slotId = "$centerKey/$courtKey/$start - $end"
                    var slot = Slot(slotId)
                    slotList.add(slot)
                }
                var court = Court(courtKey, slotList)
                courtList.add(court)
            }
            var center = Center(centerKey, courtList)
            centerList.add(center)
        }

        initRecyclerViewCenter(centerList)

        // For testing
        Log.i("CitySlotActivity", "City: $selectedCity | Date: $selectedDate")
        Log.i("CitySlotActivity", "Data Response: $jsonString")
    }


    // init recycler view center
    @SuppressLint("WrongConstant")
    private fun initRecyclerViewCenter(centerList: ArrayList<Center>) {
        // Calling the recycler view for Center
        rv_center.apply {
            layoutManager = LinearLayoutManager(this@CitySlotActivity, LinearLayout.VERTICAL, false)
            adapter = CenterAdapter(
                centerList,
                this@CitySlotActivity
            )
            setHasFixedSize(true)
        }
    }
//    override fun passDataCallback(slot: Slot) {
//        Toast.makeText(this, ("You choose " + dateChooser.text + "\n" + cityChooser.selectedItem + "\n" + slot.id), Toast.LENGTH_SHORT).show()
//
//        val slotInfo = slot.id.split("/")
//
//        val date = dateChooser.text.toString()
//        val city = cityChooser.selectedItem.toString()
//        val center = slotInfo[0]    // get center
//        val court = slotInfo[1]     // get court
//        val slot = slotInfo[2]      // slot
//
//        val fm=supportFragmentManager
//        val requestFragment =
//            AskForBookingFragment(
//                date,
//                city,
//                center,
//                court,
//                slot,
//                0, // booking number of user
//                this
//            )
//        requestFragment.show(fm, "Booking Request")
//    }
//
//    // override method callback from interface AskForBookingFragment.CallbackRequestFragment
//    // when the user choose 'yes' for a booking requirement in AskForBookingFragment, open BookingFragment
//    override fun callBack(date: String, city: String, center: String, court: String, slot: String) {
//        val fm= supportFragmentManager
//        val bookingFragment = BookingFragment(
//            date,
//            city,
//            center,
//            court,
//            slot,
//            this,
//            this
//        )
//        bookingFragment.show(fm, "Booking Process")
//    }
//
//    // override method callBackFail from interface AskForBookingFragment.CallbackRequestFragment
//    // when the user choose 'yes' for a booking requirement but the number of booking and ending is already 3
//    // open MoreThan3Fragment to inform the user
//    override fun callBackFail() {
//        val fm= supportFragmentManager
//        val bookingFragment = MoreThan3Fragment()
//        bookingFragment.show(fm, "Booking Stop")
//    }
//
//
//
//    // override method confirm from interface BookingFragment.ConfirmBookingInterface
//    // when the user confirm the booking in BookingFragment after fill in all information
//    // open FinishBookingFragment to inform the user
//    override fun confirm(date: String, city: String, center: String, court: String, slot: String, start: String, end: String) {
//        val fm= supportFragmentManager
//        val bookingFragment = FinishBookingFragment(date, city, center, court, start, end, this)
//        bookingFragment.show(fm, "Booking Finish")
//
//        //sendPostBooking(date, start, end, city, center, court, userId)
//        Toast.makeText(this, "User id: $userId\nDate: $date\nCenter: $center\nCourt: $court\nSlot: $slot\nStart: $start End: $end", Toast.LENGTH_SHORT).show()
//    }
}