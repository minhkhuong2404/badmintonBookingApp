package com.example.courtbooking

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courtbooking.booking.*
import com.example.courtbooking.request.*
import kotlinx.android.synthetic.main.activity_main_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList


class MainScreenActivity : AppCompatActivity(),
    CenterAdapter.CallbackInterface,
    AskForBookingFragment.CallbackRequestFragment,
    BookingFragment.ConfirmBookingInterface,
    FinishBookingFragment.MyBookingInterface,
    BookingAdapter.CancelInterface,
    CancelBookingFragment.CancelFinishInterface {

    lateinit var cityChooser : Spinner
    lateinit var dateChooser : EditText
    lateinit var welcomeText : TextView
    lateinit var welcomeText2 : TextView
    lateinit var bookList: ArrayList<Booking>
    lateinit var centerList: List<Center>

    lateinit var city: String   // for later choosing
    lateinit var date: String   // for later choosing

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    // random user's number of booking
    private val bookNum = 0

    private var mAPIService: JsonPlaceHolderApi? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        // new
        mAPIService = ApiUtils.aPIService
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://10.0.2.2:8000/api/hello/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        mAPIService = retrofit.create(JsonPlaceHolderApi::class.java)

        // findViewById
        cityChooser = findViewById<Spinner>(R.id.sp_city)
        dateChooser = findViewById<EditText>(R.id.et_date)
        welcomeText = findViewById<TextView>(R.id.welcome_text_1)
        welcomeText2 = findViewById<TextView>(R.id.welcome_text_2)

        // City list store here
        var listOfCities = ArrayList<String>()
        listOfCities = sendGetCity()
        listOfCities.add(0,"Choose a city")

        // Show first item on the cities array on the chosenCity spinner
        cityChooser.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfCities)

        // On City Choosing Listener
        cityChooser.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) { /* Just a place holder */ }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                city = listOfCities[position] // For testing
            }
        }
        // Set calendar
        val cal = Calendar.getInstance(TimeZone.getDefault()) // Get current date
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        // Set default as current day
        et_date.setText("$day/$month/$year")
        date = et_date.text.toString() // For testing

        // On click of date chooser
        dateChooser.setOnClickListener {
            // Setting up date picker dialog
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ _: DatePicker?, mYear: Int, mMonth: Int, mDay: Int ->
                // Add 1, since in Kotlin start from 0 - 11
                val finalMonth : Int = mMonth + 1;
                // Set result to EditText
                et_date.setText("$mDay/$finalMonth/$mYear")
                date = et_date.text.toString(); // For testing
            }, year, month, day)
            datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
            // Show Date Dialog
            datePicker.show()

        }

        // On button clicked Show Available Slots
        b_show_slots.setOnClickListener {
            Toast.makeText(this, "City: " + city + "\nDate: " + date, Toast.LENGTH_SHORT).show()

            val selectedCity = cityChooser.selectedItem
            val randomCenter = listOfCities.indexOf(selectedCity) + (1..3).random()
            // Initialize the CENTER recycler view
            initRecyclerViewCenter(randomCenter)
            // Hide welcome texts
            welcomeText.text = ""
            welcomeText2.text = ""
            // Hide Recycler of ViewBooking
            rv_booking.adapter = null
            findViewById<RelativeLayout>(R.id.center_view).bringToFront()

        }
        // On button clicked Show My Bookings
        b_show_bookings.setOnClickListener {
            // Initialize the BOOKING recycler view
            Toast.makeText(this,"Show booking",Toast.LENGTH_SHORT).show()
            sendGetBooking()
            initRecyclerViewBooking()
            // Hide welcome texts
            welcomeText.text = ""
            welcomeText2.text = ""
            // Hide 'Show Available Slots'
            rv_center.adapter = null
            findViewById<RelativeLayout>(R.id.booking_view).bringToFront()
            Log.i("bb","Bookinggggg")
        }

    }

    // Generate fake data for testing recycler view
    private fun getBookingList() : ArrayList<Booking> {
        val list = ArrayList<Booking>()

        list += Booking(
            "0001",
            "27/04/2020",
            "07:00 - 9:00",
            "Undue",
            "City#B, Center#C, Court#12",
            "06/04/2020 18:43"
        )
        list += Booking(
            "0004",
            "26/04/2020",
            "08:00 - 9:00",
            "Undue",
            "City#B, Center#1, Court#12",
            "06/04/2020 18:43"
        )
        list += Booking(
            "0008",
            "19/04/2020",
            "08:30 - 9:00",
            "Overdue",
            "City#1, Center#2, Court#12",
            "06/04/2020 18:43"
        )

        return list
    }

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

        }
    }
    private  fun initRecyclerViewBooking(){
        bookList = getBookingList()
        rv_booking.apply {
            layoutManager = LinearLayoutManager(this@MainScreenActivity, LinearLayoutManager.VERTICAL, false)
            adapter = BookingAdapter(
                bookList,
                this@MainScreenActivity
            )
            setHasFixedSize(true)
        }
    }

    @SuppressLint("WrongConstant")
    private fun initRecyclerViewCenter(numOfCenter: Int){
        // Calling the recycler view for Center
        centerList = getCenterCourtSlotList(numOfCenter)
        rv_center.apply {
            layoutManager = LinearLayoutManager(this@MainScreenActivity, LinearLayout.VERTICAL, false)
            adapter = CenterAdapter(centerList, this@MainScreenActivity)
            setHasFixedSize(true)
        }
    }

    // override passDataCallback from CenterAdapter.CallbackInterface
    override fun passDataCallback(message: Slot) {
        Log.i("Main", "Finished")
        Toast.makeText(this, ("You choose " + dateChooser.text + "\n" + cityChooser.selectedItem + "\n" + message.id), Toast.LENGTH_SHORT).show()

        val slotInfo = message.id.split("/")

        val date = dateChooser.text.toString()
        val city = cityChooser.selectedItem.toString()
        val center = slotInfo[0]    // get center
        val court = slotInfo[1]     // get court
        val slot = slotInfo[2]      // slot

        val fm=supportFragmentManager
        val requestFragment =
            AskForBookingFragment(
                date,
                city,
                center,
                court,
                slot,
                bookNum,
                this
            )
        requestFragment.show(fm, "Booking Request")
    }

    override fun callBack(date: String, city: String, center: String, court: String, slot: String) {
        val fm= supportFragmentManager
        val bookingFragment = BookingFragment(
            date,
            city,
            center,
            court,
            slot,
            this,
            this
        )
        bookingFragment.show(fm, "Booking Process")
    }

    // when press show all bookings
    private fun sendGetBooking() {
        Log.i("bb","Uaaaaaa")
        val parameters: MutableMap<String, String> = HashMap()
        parameters["name"] = "Khuong"

        val call = mAPIService?.getBookings(parameters)

        Log.i("bb",parameters.toString())

        call?.enqueue(object : Callback<List<GetBookingRequest>> {
            override fun onResponse(
                call: Call<List<GetBookingRequest>>,
                response: Response<List<GetBookingRequest>>
            ) {
                Log.i("bb",call.toString())
                if (!response.isSuccessful) {
//                    Toast.makeText(this@MainScreenActivity, response.code() , Toast.LENGTH_SHORT).show()
                    Log.i("bb","WORKKKKKKKKKKKKKKK444444444")

                    return
                }
                val bookingRequest : List<GetBookingRequest>?  = response.body()
                if (bookingRequest != null) {
                    for (booking in bookingRequest){

                    }
                }
                Log.i("bb","WORKKKKKKKKKKKKKKK")
            }

            override fun onFailure(
                call: Call<List<GetBookingRequest>>,
                t: Throwable
            ) {
                Toast.makeText(this@MainScreenActivity, t.message , Toast.LENGTH_SHORT).show()
                Log.i("bb","WORKKKKKKKKKKKKKKK222222222")

            }
        })

    }

    // when press to select a city
    private fun sendGetCity() : ArrayList<String> {
        val parameters: MutableMap<String, String> = HashMap()
        parameters["cityId"] = "all"
//        val cityRequest = CityRequest("pcityid" )

        val call = mAPIService?.getCity(parameters)
        var cities : ArrayList<String> = ArrayList()

        Log.i("bb",parameters.toString())

        call?.enqueue(object : Callback<List<CityRequest>> {
            override fun onResponse(
                call: Call<List<CityRequest>>,
                response: Response<List<CityRequest>>
            ) {
                Log.i("bb",call.toString())
                if (!response.isSuccessful) {
                    cities.add("City1")
                    cities.add("City2")
                    cities.add("City3")
                    cities.add("City4")

//                    Toast.makeText(this@MainScreenActivity, response.code() , Toast.LENGTH_SHORT).show()
                    Log.i("bb","WORKKKKKKKKKKKKKKK444444444")
                    return
                }
                val cityRequest : List<CityRequest>?  = response.body()
                if (cityRequest != null) {
                    for (city in cityRequest){
                        var content = ""
                        content += "" + city.getCityId()
                        cities.add(city.toString())
                    }
                }
                Log.i("bb","WORKKKKKKKKKKKKKKK")
            }

            override fun onFailure(
                call: Call<List<CityRequest>>,
                t: Throwable
            ) {
                Toast.makeText(this@MainScreenActivity, t.message , Toast.LENGTH_SHORT).show()
                cities.add("City??")
                Log.i("bb","WORKKKKKKKKKKKKKKK222222222")

            }
        })
        return cities
    }

    private fun sendGetCenter(cityid : String) : ArrayList<String> {
        val parameters: MutableMap<String, String> = HashMap()
        parameters["pcityid"] = "all"
//        val cityRequest = CityRequest("pcityid" )

        val call = mAPIService?.getCenter(parameters)
        var centers : ArrayList<String> = ArrayList()

        Log.i("bb",parameters.toString())

        call?.enqueue(object : Callback<List<CenterRequest>> {
            override fun onResponse(
                call: Call<List<CenterRequest>>,
                response: Response<List<CenterRequest>>
            ) {
                Log.i("bb",call.toString())
                if (!response.isSuccessful) {
//                    Toast.makeText(this@MainScreenActivity, response.code() , Toast.LENGTH_SHORT).show()
                    Log.i("bb","WORKKKKKKKKKKKKKKK444444444")
                    return
                }
                val centerRequest : List<CenterRequest>?  = response.body()
                if (centerRequest != null) {
                    for (center in centerRequest){
                        var content = ""
                        content += "" + center.getCenterId(cityid)
                        centers.add(center.toString())
                    }
                }
                Log.i("bb","WORKKKKKKKKKKKKKKK")
            }

            override fun onFailure(
                call: Call<List<CenterRequest>>,
                t: Throwable
            ) {
                Toast.makeText(this@MainScreenActivity, t.message , Toast.LENGTH_SHORT).show()
                Log.i("bb","WORKKKKKKKKKKKKKKK222222222")

            }
        })
        return centers
    }

    private fun sendGetCourt() {
        val parameters: MutableMap<String, String> = HashMap()
        parameters["pcourtid"] = "all"

        val call = mAPIService?.getCourt(parameters)

        Log.i("bb",parameters.toString())

        call?.enqueue(object : Callback<List<CourtRequest>> {
            override fun onResponse(
                call: Call<List<CourtRequest>>,
                response: Response<List<CourtRequest>>
            ) {
                Log.i("bb",call.toString())
                if (!response.isSuccessful) {
//                    Toast.makeText(this@MainScreenActivity, response.code() , Toast.LENGTH_SHORT).show()
                    Log.i("bb","WORKKKKKKKKKKKKKKK444444444")
                    return
                }
                Log.i("bb","WORKKKKKKKKKKKKKKK")
            }

            override fun onFailure(
                call: Call<List<CourtRequest>>,
                t: Throwable
            ) {
                Toast.makeText(this@MainScreenActivity, t.message , Toast.LENGTH_SHORT).show()
                Log.i("bb","WORKKKKKKKKKKKKKKK222222222")

            }
        })
    }

    // when press to select a date
    private fun sendGetDate() {
        val parameters: MutableMap<String, String> = HashMap()
        parameters["pdate"] = "all"

        val call = mAPIService?.getDate(parameters)

        Log.i("bb",parameters.toString())

        call?.enqueue(object : Callback<List<DateRequest>> {
            override fun onResponse(
                call: Call<List<DateRequest>>,
                response: Response<List<DateRequest>>
            ) {
                Log.i("bb",call.toString())
                if (!response.isSuccessful) {
//                    Toast.makeText(this@MainScreenActivity, response.code() , Toast.LENGTH_SHORT).show()
                    Log.i("bb","WORKKKKKKKKKKKKKKK444444444")
                    return
                }
                Log.i("bb","WORKKKKKKKKKKKKKKK")
            }

            override fun onFailure(
                call: Call<List<DateRequest>>,
                t: Throwable
            ) {
                Toast.makeText(this@MainScreenActivity, t.message , Toast.LENGTH_SHORT).show()
                Log.i("bb","WORKKKKKKKKKKKKKKK222222222")
            }
        })

    }

    // when press show all slots
    private fun sendGetSlot(city: String, date: String) {
        val parameters: MutableMap<String, String> = HashMap()
        parameters["pcityid"] = city
        parameters["pdate"] = date

        val call = mAPIService?.getSlots(parameters)

        Log.i("bb",parameters.toString())

        call?.enqueue(object : Callback<List<SlotRequest>> {
            override fun onResponse(
                call: Call<List<SlotRequest>>,
                response: Response<List<SlotRequest>>
            ) {
                Log.i("bb",call.toString())
                if (!response.isSuccessful) {
//                    Toast.makeText(this@MainScreenActivity, response.code() , Toast.LENGTH_SHORT).show()
                    Log.i("bb","WORKKKKKKKKKKKKKKK444444444")
                    return
                }
                val slotRequests : List<SlotRequest>?  = response.body()
                if (slotRequests != null) {
                    for (slot in slotRequests){

                    }
                }

                Log.i("bb","WORKKKKKKKKKKKKKKK")
            }

            override fun onFailure(
                call: Call<List<SlotRequest>>,
                t: Throwable
            ) {
                Toast.makeText(this@MainScreenActivity, t.message , Toast.LENGTH_SHORT).show()
                Log.i("bb","WORKKKKKKKKKKKKKKK222222222")

            }
        })

    }

    // when press create a new booking
    private fun sendPostBooking(bookingId:String, date: String, startTime: String, endTime:String, cityId:String, centerId: String, courtId: String, playerId: String) {
//        val bookingRequest = BookingRequest(
//            "booking1",
//            "2021-05-01", "10:00:00", "10:45:00",
//            "1", "2", "A", "B"
//        )
        val bookingRequest = BookingRequest(bookingId, "2020-04-11 13:00:00", date, startTime, endTime, cityId, centerId, courtId, playerId)


//        val fields: MutableMap<String, String> = HashMap()

        val call = mAPIService?.createBookings(bookingRequest)

        Log.i("bb",bookingRequest.toString())

        call?.enqueue(object : Callback<BookingRequest?> {
            override fun onResponse(
                call: Call<BookingRequest?>,
                response: Response<BookingRequest?>
            ) {

                if (!response.isSuccessful) {
//                    Toast.makeText(this@MainScreenActivity, response.code() , Toast.LENGTH_SHORT).show()
                    Log.i("bb","WOWWWWWWWWWWWWWW44444444")
                    return
                }
                val bookingRequest: BookingRequest? = response.body()
                var content = ""
                    content += """
                    Code: ${response.code()}

                    """.trimIndent()
                    content += """
                    Booking ID: ${bookingRequest!!.pbookingid}

                    """.trimIndent()

                    content += """
                    Date: ${bookingRequest!!.pdate}

                    """.trimIndent()
                    content += """
                    Start Time: ${bookingRequest!!.pstarttime}

                    """.trimIndent()
                    content += """
                    End Time: ${bookingRequest!!.pendtime}

                    """.trimIndent()
                    content += """
                    City Id: ${bookingRequest!!.pcityid}

                    """.trimIndent()
                    content += """
                    Center Id: ${bookingRequest!!.pcenterid}

                    """.trimIndent()
                    content += """
                    Court Id: ${bookingRequest!!.pcourtid}

                    """.trimIndent()
                    content += """
                    Facebook Id: ${bookingRequest!!.pplayerid}

                    """.trimIndent()
                    Toast.makeText(this@MainScreenActivity, content , Toast.LENGTH_SHORT).show()
                Log.i("bb",content)
                Log.i("bb","WOWWWWWWWWWWWWWW")
            }

            override fun onFailure(call: Call<BookingRequest?>, t: Throwable) {
                Toast.makeText(this@MainScreenActivity, t.message , Toast.LENGTH_SHORT).show()
                Log.i("bb","WOWWWWWWWWWWWWWW22222")
            }
        })

    }

    override fun callBackFail() {
        val fm= supportFragmentManager
        val bookingFragment = MoreThan3Fragment()
        bookingFragment.show(fm, "Booking Stop")
    }

    override fun confirm(date: String, city: String, center: String, court: String, slot: String, start: String, end: String) {
        val fm= supportFragmentManager
        val bookingFragment = FinishBookingFragment(date, city, center, court, start, end, this)
        bookingFragment.show(fm, "Booking Finish")

        sendPostBooking("Booking Id", date, start, end, city, center, court, "Player Id")
        Toast.makeText(this, "Date: $date\nCenter: $center\nCourt: $court\nSlot: $slot\nStart: $start End: $end", Toast.LENGTH_SHORT).show()
    }

    override fun showBooking() {
        // Initialize the BOOKING recycler view
        initRecyclerViewBooking()
        // Hide welcome texts
        welcomeText.text = ""
        welcomeText2.text = ""
        // Hide 'Show Available Slots'
        rv_center.adapter = null
        findViewById<RelativeLayout>(R.id.booking_view).bringToFront()
        sendGetBooking()
        Log.i("bb","Hiii")
    }

    // override function from BookingAdapter.CancelInterface, open cancel dialog fragment
    override fun moveToCancelFragment(message: String) {
        val fm= supportFragmentManager
        val bookingFragment =
            CancelBookingFragment(message, this)
        bookingFragment.show(fm, "Cancel Booking")
    }

    fun aaa(view: View) {
        Log.i("bb", "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV")
        Log.i("bb", "ahihi")
        Log.i("bb", view.toString())
    }

    override fun moveToFinishCancel(message: String) {

        val fm= supportFragmentManager

        var bookingFragment =
            CancelResultFragment(message, false)

        if (message == "0004") {
            // remove the booking in the list
            for (booking in bookList) {
                if (booking.id == message) {
                    bookList.remove(booking)
                    break
                }
            }

            //
            rv_booking.adapter = BookingAdapter(
                bookList,
                this@MainScreenActivity
            )
            bookingFragment =
                CancelResultFragment(
                    message,
                    true
                )
        }

        bookingFragment.show(fm, "Cancel Result")

    }

}




