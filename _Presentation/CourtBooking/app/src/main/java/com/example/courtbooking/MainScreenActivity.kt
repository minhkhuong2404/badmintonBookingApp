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
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import kotlinx.android.synthetic.main.activity_main_screen.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class MainScreenActivity : AppCompatActivity(),
    CenterAdapter.CallbackInterface,
    AskForBookingFragment.CallbackRequestFragment,
    BookingFragment.ConfirmBookingInterface,
    FinishBookingFragment.MyBookingInterface,
    BookingAdapter.CancelInterface,
    CancelBookingFragment.CancelFinishInterface {

    lateinit var userId : String

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

        // get user id
        userId = "user id"
        Toast.makeText(this, userId, Toast.LENGTH_SHORT).show()
        if (AccessToken.getCurrentAccessToken() != null) {
            Log.i("k", "KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK")
            loadUserId(AccessToken.getCurrentAccessToken())
        }
        // new
        mAPIService = ApiUtils.aPIService

        // findViewById
        cityChooser = findViewById<Spinner>(R.id.sp_city)
        dateChooser = findViewById<EditText>(R.id.et_date)
        welcomeText = findViewById<TextView>(R.id.welcome_text_1)
        welcomeText2 = findViewById<TextView>(R.id.welcome_text_2)

        sendGetCenter()
        sendGetCenterBookings()
        sendGetCityCenterStaffs()
        sendGetCourt()
        sendGetStaff()

        // City list store here
        val listOfCities = sendGetCity()
        listOfCities.add(0,"Choose a city")

        // Show first item on the cities array on the chosenCity spinner
        cityChooser.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listOfCities)

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
        val month = cal.get(Calendar.MONTH) + 1
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
//            Toast.makeText(this, "City: " + city + "\nDate: " + date, Toast.LENGTH_SHORT).show()

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
            initRecyclerViewBooking()
            // Hide welcome texts
            welcomeText.text = ""
            welcomeText2.text = ""
            // Hide 'Show Available Slots'
            rv_center.adapter = null
            findViewById<RelativeLayout>(R.id.booking_view).bringToFront()
        }

    }

    // Generate fake data for testing recycler view
    private fun getBookingList() : ArrayList<Booking> {
        val list = sendGetPLayerBooking() as ArrayList<Booking>

//        list += Booking(
//             1,
//            1587574789000,
//            1619802000000,
//            "10:00:00",
//            "10:45:00",
//             "city1",
//            "center1",
//            "court1",
//             "player1",
//            0
//        )
//        list += Booking(
//            "0004",
//            "26/04/2020",
//            "08:00 - 9:00",
//            "Undue",
//            "City#B, Center#1, Court#12",
//            "06/04/2020 18:43"
//        )
//        list += Booking(
//            "0008",
//            "19/04/2020",
//            "08:30 - 9:00",
//            "Overdue",
//            "City#1, Center#2, Court#12",
//            "06/04/2020 18:43"
//        )

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

    // override method passDataCallback from interface CenterAdapter.CallbackInterface
    // after the user choose a slot in center adapter, get the information of the chosen slot
    // and open the AskForBookingFragment to ask the user if he/she want to make the booking with this slot (yes/no question)
    override fun passDataCallback(message: Slot) {
//        Toast.makeText(this, ("You choose " + dateChooser.text + "\n" + cityChooser.selectedItem + "\n" + message.id), Toast.LENGTH_SHORT).show()

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

    // override method callback from interface AskForBookingFragment.CallbackRequestFragment
    // when the user choose 'yes' for a booking requirement in AskForBookingFragment, open BookingFragment
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

    // override method callBackFail from interface AskForBookingFragment.CallbackRequestFragment
    // when the user choose 'yes' for a booking requirement but the number of booking and ending is already 3
    // open MoreThan3Fragment to inform the user
    override fun callBackFail() {
        val fm= supportFragmentManager
        val bookingFragment = MoreThan3Fragment()
        bookingFragment.show(fm, "Booking Stop")
    }

    // override method confirm from interface BookingFragment.ConfirmBookingInterface
    // when the user confirm the booking in BookingFragment after fill in all information
    // open FinishBookingFragment to inform the user
    override fun confirm(date: String, city: String, center: String, court: String, slot: String, start: String, end: String) {
        val fm= supportFragmentManager
        val bookingFragment = FinishBookingFragment(date, city, center, court, start, end, this)
        bookingFragment.show(fm, "Booking Finish")

        sendPostBooking(date, start, end, city, center, court, userId)
        Toast.makeText(this, "User id: $userId\nDate: $date\nCenter: $center\nCourt: $court\nSlot: $slot\nStart: $start End: $end", Toast.LENGTH_SHORT).show()
    }

    // override method showBooking from interface FinishBookingFragment.MyBookingInterface
    // if the user want to go to the booking list from the FinishBookingFragment
    // bring the RelativeLayout booking_view to the front
    override fun showBooking() {
        // Initialize the BOOKING recycler view
        initRecyclerViewBooking()
        // Hide welcome texts
        welcomeText.text = ""
        welcomeText2.text = ""
        // Hide 'Show Available Slots'
        rv_center.adapter = null
        findViewById<RelativeLayout>(R.id.booking_view).bringToFront()
    }

    // override method moveToCancelFragment from BookingAdapter.CancelInterface
    // when the user press 'cancel' in a booking,
    // open CancelBookingFragment to ask the user for cancellation permission
    override fun moveToCancelFragment(message: String) {
        val fm= supportFragmentManager
        val bookingFragment =
            CancelBookingFragment(message, this)
        bookingFragment.show(fm, "Cancel Booking")
    }

    // override method moveToFinishCancel from CancelBookingFragment.CancelFinishInterface
    // after the user send 'yes' for the cancellation permission in CancelBookingFragment
    // open CancelResultFragment to inform that the cancellation is success (or not)
    override fun moveToFinishCancel(message: String) {

        val fm= supportFragmentManager

        var bookingFragment =
            CancelResultFragment(message, false)

        if (message == "0004") {
            // remove the booking in the list
            for (booking in bookList) {
                if (booking.getBookingId() == message) {
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

    // when press show all bookings
    private fun sendGetPLayerBooking(): ArrayList<PlayerBookingRequest> {
        val parameters: MutableMap<String, String> = HashMap()

        val call = mAPIService?.getPlayerBookings("player1")

        val listOfPlayerBookingRequest = ArrayList<PlayerBookingRequest>()
//        Log.i("bb",parameters.toString())

        call?.enqueue(object : Callback<List<PlayerBookingRequest>> {
            override fun onResponse(
                call: Call<List<PlayerBookingRequest>>,
                response: Response<List<PlayerBookingRequest>>
            ) {
                Log.i("bb",call.toString())
                if (!response.isSuccessful) {
//                    Toast.makeText(this@MainScreenActivity, response.code() , Toast.LENGTH_SHORT).show()
                    Log.i("bb","PlayerBooking444444444")
                    return
                }
                val bookingRequest : List<PlayerBookingRequest>?  = response.body()
                if (bookingRequest != null) {
                    for (booking in bookingRequest){
                        var content = ""
                        content += "Booking ID: " + booking.getBookingId()
                        content += "\nTimestamp: " + java.time.format.DateTimeFormatter.ISO_INSTANT
                            .format(java.time.Instant.ofEpochSecond(booking.getTimestamp().toLong() * 1000))
                        content += "\nDate: " + booking.getDate()
                        content += "\nStart time " + booking.getStartTime()
                        content += "\nEnd time: " + booking.getEndTime()
                        content += "\nCityId: " + booking.getCityId()
                        content += "\nCenterId: " + booking.getCenterID()
                        content += "\nCourtId: " + booking.getCourtId()
                        content += "\nPlayerId: " + booking.getPlayerId()
                        content += "\nStatus: " + booking.getStatus()
                        listOfPlayerBookingRequest.add(booking)
                        Log.i("bb",content)
                    }
                }
                Log.i("bb","PlayerBooking")
            }

            override fun onFailure(
                call: Call<List<PlayerBookingRequest>>,
                t: Throwable
            ) {
                Toast.makeText(this@MainScreenActivity, t.message , Toast.LENGTH_SHORT).show()
                Log.i("bb","PlayerBooking222222222")

            }
        })
        return listOfPlayerBookingRequest

    }

    private fun sendGetStaff()  {
        val parameters: MutableMap<String, String> = HashMap()

        val call = mAPIService?.getStaffs()
//        var staffs : ArrayList<String> = ArrayList()

//        Log.i("bb",parameters.toString())

        call?.enqueue(object : Callback<List<StaffRequest>> {
            override fun onResponse(
                call: Call<List<StaffRequest>>,
                response: Response<List<StaffRequest>>
            ) {
                Log.i("bb",call.toString())
                if (!response.isSuccessful) {
                    Log.i("bb",response.code().toString())
                    Log.i("bb",response.message().toString())
                    Log.i("bb","Staff444444444")
                    return
                }
                val staffRequest : List<StaffRequest>?  = response.body()
                if (staffRequest != null) {
                    for (staff in staffRequest){
                        var content = ""
                        content += "StaffId: " + staff.getStaffId()
                        content += "\nCityId: " + staff.getCityId()
                        content += "\nCourtId: " + staff.getCourtId()
                        Log.i("bb",content)

                    }
                }
                Log.i("bb",staffRequest.toString())
                Log.i("bb","Staff")
            }

            override fun onFailure(
                call: Call<List<StaffRequest>>,
                t: Throwable
            ) {
                Toast.makeText(this@MainScreenActivity, t.message , Toast.LENGTH_SHORT).show()
//                cities.add("City??")
                Log.i("bb","Staff222222222")
            }
        })

    }
    // when press to select a city
    private fun sendGetCity() : ArrayList<String> {
        val parameters: MutableMap<String, String> = HashMap()
        parameters["cityId"] = "all"
//        val cityRequest = CityRequest("pcityid" )

        val call = mAPIService?.getCities()
        var cities : ArrayList<String> = ArrayList()

//        Log.i("bb",parameters.toString())

        call?.enqueue(object : Callback<List<CityRequest>> {
            override fun onResponse(
                call: Call<List<CityRequest>>,
                response: Response<List<CityRequest>>
            ) {
                Log.i("bb",call.toString())
                if (!response.isSuccessful) {
                    cities.add("A")
                    cities.add("BB")
                    cities.add("CCCC")

                    Log.i("bb","Cities444444444")
                    return
                }
                val cityRequest : List<CityRequest>?  = response.body()
                if (cityRequest != null) {
                    for (city in cityRequest){
                        var content = ""
                        content += "" + city.getCityId()
                        cities.add(content)
                        Log.i("bb", "City id: $content")
                    }
                }
                Log.i("bb","Cities")
            }

            override fun onFailure(
                call: Call<List<CityRequest>>,
                t: Throwable
            ) {
                Toast.makeText(this@MainScreenActivity, t.message , Toast.LENGTH_SHORT).show()
                cities.add("City??")
                Log.i("bb","Cities222222222")

            }
        })
        return cities
    }

    private fun sendGetCenter() : ArrayList<String> {
        val parameters: MutableMap<String, String> = HashMap()
//        val cityRequest = CityRequest("pcityid" )

        val call = mAPIService?.getCityCenters("C")
        var listOfCenters : ArrayList<String> = ArrayList()

//        Log.i("bb",parameters.toString())

        call?.enqueue(object : Callback<List<CenterRequest>> {
            override fun onResponse(
                call: Call<List<CenterRequest>>,
                response: Response<List<CenterRequest>>
            ) {
                Log.i("bb",call.toString())
                if (!response.isSuccessful) {
//                    Toast.makeText(this@MainScreenActivity, response.code() , Toast.LENGTH_SHORT).show()
                    Log.i("bb","Center444444444")
                    return
                }
                val  centers: List<CenterRequest>?  = response.body()
                if (centers != null) {
                    for (center in centers){
                        var content = ""
                        content += "Center Id: " + center.getCenterId()
                        content += "\nCity Id: " + center.getCityId()
                        listOfCenters.add(center.toString())
                        Log.i("bb",content)
                    }
                }
                Log.i("bb","Center")
            }

            override fun onFailure(
                call: Call<List<CenterRequest>>,
                t: Throwable
            ) {
                Toast.makeText(this@MainScreenActivity, t.message , Toast.LENGTH_SHORT).show()
                Log.i("bb","Center222222222")

            }
        })
        return listOfCenters
    }

    private fun sendGetCourt() : ArrayList<String> {
        val parameters: MutableMap<String, String> = HashMap()

        val call = mAPIService?.getCityCenterCourts("A","A1")

        val listOfCourts = ArrayList<String>()
//        Log.i("bb",parameters.toString())

        call?.enqueue(object : Callback<List<CourtRequest>> {
            override fun onResponse(
                call: Call<List<CourtRequest>>,
                response: Response<List<CourtRequest>>
            ) {
                Log.i("bb",call.toString())
                if (!response.isSuccessful) {
//                    Toast.makeText(this@MainScreenActivity, response.code() , Toast.LENGTH_SHORT).show()
                    Log.i("bb","Court444444444")
                    return
                }
                val courts : List<CourtRequest>?  = response.body()
                if (courts != null) {
                    for ( court in courts){
                        var content = ""
                        content += "City ID: " + court.getCityId() + "\n"
                        content += "Center ID: " + court.getCenterId() + "\n"
                        content += "Court ID: " + court.getCourtId() + "\n"
                        Log.i("bb", content)
                        listOfCourts.add(content)
                    }
                }
                Log.i("bb","Court")
            }

            override fun onFailure(
                call: Call<List<CourtRequest>>,
                t: Throwable
            ) {
                Toast.makeText(this@MainScreenActivity, t.message , Toast.LENGTH_SHORT).show()
                Log.i("bb","Court222222222")

            }
        })
        return listOfCourts
    }

    // when press to select a date
    private fun sendGetCenterBookings() {
        val parameters: MutableMap<String, String> = HashMap()

        val call = mAPIService?.getCenterBookings("A1")

//        Log.i("bb",parameters.toString())

        call?.enqueue(object : Callback<List<CenterBookingRequest>> {
            override fun onResponse(
                call: Call<List<CenterBookingRequest>>,
                response: Response<List<CenterBookingRequest>>
            ) {
                Log.i("bb",call.toString())
                if (!response.isSuccessful) {
//                    Toast.makeText(this@MainScreenActivity, response.code() , Toast.LENGTH_SHORT).show()
                    Log.i("bb","CenterBooking444444444")
                    return
                }
                val centerBookingRequests : List<CenterBookingRequest>?  = response.body()
                if (centerBookingRequests != null) {
                    for ( centerBooking in centerBookingRequests){
                        var content = ""
                        content += "Booking ID: " + centerBooking.getBookingId() + "\n"
                        content += "Center ID: " + centerBooking.getCenterId() + "\n"
                        Log.i("bb", content)
                    }
                }
                Log.i("bb","CenterBooking")
            }

            override fun onFailure(
                call: Call<List<CenterBookingRequest>>,
                t: Throwable
            ) {
                Toast.makeText(this@MainScreenActivity, t.message , Toast.LENGTH_SHORT).show()
                Log.i("bb","CenterBooking222222222")
            }
        })

    }

    // when press show all slots
    private fun sendGetCityCenterStaffs() {
        val parameters: MutableMap<String, String> = HashMap()
//        parameters["pcityid"] =
//        parameters["pdate"] = date

        val call = mAPIService?.getCityCenterStaffs("A","A1")

//        Log.i("bb",parameters.toString())

        call?.enqueue(object : Callback<List<CityCenterStaff>> {
            override fun onResponse(
                call: Call<List<CityCenterStaff>>,
                response: Response<List<CityCenterStaff>>
            ) {
                Log.i("bb",call.toString())
                if (!response.isSuccessful) {
//                    Toast.makeText(this@MainScreenActivity, response.code() , Toast.LENGTH_SHORT).show()
                    Log.i("bb","CityCenterStaff444444444")
                    return
                }
                val cityCenterStaffs : List<CityCenterStaff>?  = response.body()
                if (cityCenterStaffs != null) {
                    for ( cityCenterStaff in cityCenterStaffs){
                        var content = ""
                        content += "Staff ID: " + cityCenterStaff.getStaff() + "\n"
                        content += "City ID: " + cityCenterStaff.getCity() + "\n"
                        content += "Court ID: " + cityCenterStaff.getCenter()  + "\n"

                        Log.i("bb", content)

                    }
                }
                Log.i("bb","CityCenterStaff")
            }

            override fun onFailure(
                call: Call<List<CityCenterStaff>>,
                t: Throwable
            ) {
                Toast.makeText(this@MainScreenActivity, t.message , Toast.LENGTH_SHORT).show()
                Log.i("bb","CityCenterStaff222222222")

            }
        })

    }

    // when press create a new booking
    private fun sendPostBooking( date: String, startTime: String, endTime:String, cityId:String, centerId: String, courtId: String, playerId: String) {
//        val bookingRequest = BookingRequest(
//            "booking1",
//            "2021-05-01", "10:00:00", "10:45:00",
//            "1", "2", "A", "B"
//        )
        val bookingRequests = BookingRequest(date, startTime, endTime, cityId, centerId, courtId, playerId)

        val fields: MutableMap<String, String> = HashMap()
        fields["pdate"] = date
        fields["pstarttime"] = startTime
        fields["pendtime"] = endTime
        fields["pcityid"] = cityId
        fields["pcenterid"] = centerId
        fields["pcourtid"] = courtId
        fields["pplayerid"] = playerId

        val call = mAPIService?.createBookings(bookingRequests)

//        Log.i("bb",bookingRequests.toString())
        Log.i("bb",fields.toString())

        call?.enqueue(object : Callback<BookingRequest?> {
            override fun onResponse(
                call: Call<BookingRequest?>,
                response: Response<BookingRequest?>
            ) {

                if (!response.isSuccessful) {
//                    Toast.makeText(this@MainScreenActivity, response.code() , Toast.LENGTH_SHORT).show()
                    Log.i("bb", response.body().toString())
                    Log.i("bb",response.message())
                    Log.i("bb",response.code().toString())
                    Log.i("bb","CreateBooking44444444")
                    return
                }
                val bookingRequest: BookingRequest? = response.body()
                var content = ""
                    content += """
                    Code: ${response.code()}

                    """.trimIndent()

                    content += """
                    Date: ${bookingRequest!!.getDate()}

                    """.trimIndent()
                    content += """
                    Start Time: ${bookingRequest.getStartTime()}

                    """.trimIndent()
                    content += """
                    End Time: ${bookingRequest.getEndTime()}

                    """.trimIndent()
                    content += """
                    City Id: ${bookingRequest.getCityID()}

                    """.trimIndent()
                    content += """
                    Center Id: ${bookingRequest.getCenterID()}

                    """.trimIndent()
                    content += """
                    Court Id: ${bookingRequest.getCourtId()}

                    """.trimIndent()
                    content += """
                    Facebook Id: ${bookingRequest.getPlayerId()}

                    """.trimIndent()
                    Toast.makeText(this@MainScreenActivity, content , Toast.LENGTH_SHORT).show()
                Log.i("bb",content)
                Log.i("bb","CreateBooking")
            }

            override fun onFailure(call: Call<BookingRequest?>, t: Throwable) {
                Toast.makeText(this@MainScreenActivity, t.message , Toast.LENGTH_SHORT).show()
                Log.i("bb","CreateBooking22222")
            }
        })

    }


    fun aaa(view: View) {
        Log.i("bb", "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV")
        Log.i("bb", "ahihi")
        Log.i("bb", view.toString())
    }



    //
    fun loadUserId(newAccessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(newAccessToken, object : GraphRequest.GraphJSONObjectCallback {
            override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
                try {
                    userId = `object`?.getString("id").toString()
                } catch(e: JSONException) {
                    e.printStackTrace()
                }
            } // end onCompleted

        })

        val parameter = Bundle()
        parameter.putString("fields", "id")
        request.parameters = parameter
        request.executeAsync()
    }

}




