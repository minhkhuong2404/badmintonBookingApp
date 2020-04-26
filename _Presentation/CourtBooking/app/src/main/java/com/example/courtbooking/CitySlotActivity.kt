package com.example.courtbooking

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.courtbooking.adapter.Center
import com.example.courtbooking.adapter.Court
import com.example.courtbooking.adapter.Slot
import com.facebook.AccessToken

class CitySlotActivity : AppCompatActivity() {
    // Activity vars
    lateinit var userId : String
    lateinit var accessToken: AccessToken

    lateinit var selectedCity: String
    lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_slot)

        selectedCity = intent.getStringExtra("city")
        selectedDate = intent.getStringExtra("date")

        // Get token & user id
        accessToken = AccessToken.getCurrentAccessToken()
        userId = accessToken.userId

        Log.i("UserBookingActivity", "$selectedCity | $selectedDate")
        // Initialize the CENTER recycler view
        //initRecyclerViewCenter(randomCenter)
    }
    // request city slot

    // generate fake data for slot
    private fun getCenterCourtSlotList(size: Int): List<Center> {
        val list = ArrayList<Center>()
        //val exampleCourtList = getCourtList()
        for (i in 0 until size) {
            //val slot = Center("Center#$i", exampleCourtList)
            val slot = Center(
                "Center#$i",
                getCourtList()
            )
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
        return listOf(
            Court("Court#1", getSlotList(1)),
            Court("Court#2", getSlotList(2)),
            Court("Court#3", getSlotList(3)),
            Court("Court#4", getSlotList(4)),
            Court(
                "Court#5",
                getSlotList(5)
            )
        )
    }

    private fun getSlotList(type: Int): List<Slot> {
        return when (type) {
            1 -> listOf(
                Slot("7:00 - 8:00"),
                Slot("7:30 - 8:30"),
                Slot("8:30 - 9:00"),
                Slot("9:00 - 10:00"),
                Slot("10:00 - 11:00"),
                Slot("12:00 - 13:00"),
                Slot("13:00 - 21:00")
            )
            2 -> listOf(
                Slot("7:30 - 8:30"),
                Slot("8:30 - 9:00"),
                Slot("9:00 - 10:00"),
                Slot("10:00 - 11:00"),
                Slot("12:00 - 13:00"),
                Slot("13:00 - 21:00")
            )
            3 -> listOf(
                Slot("8:30 - 9:00"),
                Slot("9:00 - 10:00"),
                Slot("10:00 - 11:00"),
                Slot("12:00 - 13:00"),
                Slot("13:00 - 21:00")
            )
            4 -> listOf(
                Slot("9:00 - 10:00"),
                Slot("10:00 - 11:00"),
                Slot("12:00 - 13:00"),
                Slot("13:00 - 21:00")
            )
            else -> listOf(
                Slot("12:00 - 13:00"),
                Slot("13:00 - 21:00")
            )

        }
    }


    // init recycler view center
//    @SuppressLint("WrongConstant")
//    private fun initRecyclerViewCenter(){
//        // Calling the recycler view for Center
//        centerList = getCenterCourtSlotList(numOfCenter)
//        rv_center.apply {
//            layoutManager = LinearLayoutManager(this@CitySlotActivity, LinearLayout.VERTICAL, false)
//            adapter = CenterAdapter(
//                centerList,
//                this@CitySlotActivity
//            )
//            setHasFixedSize(true)
//        }
//    }
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