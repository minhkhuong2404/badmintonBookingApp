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
import com.example.courtbooking.adapter.Center
import com.example.courtbooking.adapter.CenterAdapter
import com.example.courtbooking.adapter.Court
import com.example.courtbooking.adapter.Slot
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
import kotlin.collections.HashMap


class MainScreenActivity :
    AppCompatActivity(),
    CenterAdapter.CallbackInterface,
    AskForBookingFragment.CallbackRequestFragment,
    BookingFragment.ConfirmBookingInterface,
    FinishBookingFragment.MyBookingInterface,
    BookingAdapter.CancelInterface,
    CancelBookingFragment.CancelFinishInterface {

    // Activity vars
    lateinit var userId : String
    lateinit var accessToken: AccessToken
    private var mAPIService: JsonPlaceHolderApi? = null
    private val bookNum = 0

    // View vars
    lateinit var cityChooser : Spinner
    lateinit var dateChooser : EditText
    lateinit var welcomeText : TextView
    lateinit var welcomeText2 : TextView

    //    lateinit var cityList: ArrayList<String>
    lateinit var centerList: List<Center>
    lateinit var bookList: ArrayList<Booking>

    // Chooser view var
    lateinit var selectedCity: String   // for later choosing
    lateinit var selectedDate: String   // for later choosing

    // Recycler view var
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        // Get token & user id
        accessToken = AccessToken.getCurrentAccessToken()
        userId = accessToken.userId

        // New API Server
        mAPIService = ApiUtils.APIService

        // findViewById
        cityChooser = findViewById<Spinner>(R.id.sp_city)
        dateChooser = findViewById<EditText>(R.id.et_date)
        welcomeText = findViewById<TextView>(R.id.welcome_text_1)
        welcomeText2 = findViewById<TextView>(R.id.welcome_text_2)

        // Send get city request to server, then show city chooser
        sendGetCity()

        // Set date chooser
        setDateChooser()

        // On button clicked Show Available Slots
        b_show_slots.setOnClickListener {
//            Toast.makeText(this, "City: " + city + "\nDate: " + date, Toast.LENGTH_SHORT).show()

//            val selectedCity = cityChooser.selectedItem

            val centerCourtSlotList = sendGetCitySlot()
            // Initialize the CENTER recycler view
            initRecyclerViewCenter(centerCourtSlotList)
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

    // set date chooser
    private fun setDateChooser() {
        // Get current date
        val cal = Calendar.getInstance(TimeZone.getDefault())
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        // Set default as current day
        // Show on view
        et_date.setText(dateConvert(day, month, year, '/'))
        selectedDate = dateConvert(day, month, year, '-')

        Toast.makeText(this, selectedDate, Toast.LENGTH_SHORT).show()

        // On click of date chooser
        dateChooser.setOnClickListener {
            // Setting up date picker dialog
            val datePicker = DatePickerDialog(
                this@MainScreenActivity,
                DatePickerDialog.OnDateSetListener { _: DatePicker?, mYear: Int, mMonth: Int, mDay: Int ->
                    // Add 1, since in Kotlin start from 0 - 11
                    val mFinalMonth: Int = mMonth + 1;
                    // Set result to EditText

                    et_date.setText(dateConvert(mDay, mMonth, mYear, '/'))
                    selectedDate = dateConvert(mDay, mMonth, mYear, '-')

                    Toast.makeText(this, selectedDate, Toast.LENGTH_SHORT).show()
                },
                year,
                month,
                day
            )
            datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
            // Show Date Dialog
            datePicker.show()
        }
    }

    // convert date to string
    private fun dateConvert(day : Int, month : Int, year : Int, separator : Char) : String {
        val mFinalMonth = month + 1
        val dayText: String
        val monthText: String
        if (day < 10) {
            dayText = "0$day";
        } else {
            dayText = "$day"
        }
        if (mFinalMonth < 10) {
            monthText = "0$mFinalMonth";
        } else {
            monthText = "$mFinalMonth"
        }
        if (separator == '-') {
            return "$year-$monthText-$dayText"
        }
        return "$dayText/$monthText/$year"
    }

    // set city chooser
    private fun setCityChooser(cityList: ArrayList<String>) {
        // Show first item on the cities array on the chosenCity spinner
        cityChooser.adapter = ArrayAdapter<String>(
            this@MainScreenActivity,
            android.R.layout.simple_list_item_1,
            cityList
        )

        // On City Choosing Listener
        cityChooser.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { /* Just a place holder */
            }

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                selectedCity = cityList[position] // For testing
                Toast.makeText(
                    this@MainScreenActivity,
                    "Selected: $selectedCity",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // send getCities() request to server, then show city spinner
    private fun sendGetCity() {
        val call = mAPIService?.getCities()
        val cityList: ArrayList<String> = ArrayList()
        call?.enqueue(object : Callback<List<String>> {

            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (!response.isSuccessful) {
                    Log.i("getCities", "Response unsuccessful.")
                    return
                }
                val cities: List<String>? = response.body()
                if (cities != null) {
                    for (city in cities) {
                        cityList.add(city)
                        Log.i("getCities", "Add city: $city")
                    }
                    Log.i("getCities", "Size: " + cityList.size)
                }

                // set city chooser
                setCityChooser(cityList)
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.i("getCities", "Failed. Message: " + t.message)
            }
        })
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

    // init recycler view booking
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
    private fun getSlotList(type : Int): List<Slot> {
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
    @SuppressLint("WrongConstant")
    private fun initRecyclerViewCenter(centerList: List<Center>){
        // Calling the recycler view for Center
//        centerList = getCenterCourtSlotList(numOfCenter)
        rv_center.apply {
            layoutManager = LinearLayoutManager(this@MainScreenActivity, LinearLayout.VERTICAL, false)
            adapter = CenterAdapter(
                centerList,
                this@MainScreenActivity
            )
            setHasFixedSize(true)
        }
    }

    // get player bookings from server
    private fun sendGetPLayerBooking(): ArrayList<PlayerBookingRequest> {
//        val parameters: MutableMap<String, String> = HashMap()

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



    private fun sendGetCitySlot(): List<Center> {
        val call = mAPIService?.getCitySlot(selectedCity, selectedDate)

        var centerCourtSlotList = ArrayList<Center>()

        call?.enqueue(object: Callback<HashMap<String, HashMap<String, List<List<String>>>>> {
            override fun onResponse(
                call: Call<HashMap<String, HashMap<String, List<List<String>>>>>,
                response: Response<HashMap<String, HashMap<String, List<List<String>>>>>
            ) {
                if (!response.isSuccessful) {
                    Log.i("bb",response.code().toString())
                    Log.i("bb",response.message().toString())
                    Log.i("bb","CitySlot444444444")
                    return
                }

                val citySlotList = response.body()
                val centerList = ArrayList<Center>()
                // for each center id create a center object
                for (centerId in citySlotList!!.keys) {
                    // create court list
                    val courtList = ArrayList<Court>()

                    // for each court id create a court object
                    for (courtId in citySlotList[centerId]!!.keys) {
                        // create a slot list
                        val slotList = ArrayList<Slot>()

                        // for each slot create a slot object
                        for (slot in citySlotList[centerId]!![courtId]!!) {
                            val startSlot = slot[0]    // start time
                            val endSlot = slot[1]    // end time

                            // add new slot to slotList
                            slotList.add(Slot("$startSlot - $endSlot"))
                        }

                        // add new court to courtList
                        courtList.add( Court(courtId, slotList))
                    }

                    // add new center to centerList
                    centerList.add(Center(centerId, courtList))

                }

                centerCourtSlotList = centerList

            }

            override fun onFailure(
                call: Call<HashMap<String, HashMap<String, List<List<String>>>>>,
                t: Throwable
            ) {
                Toast.makeText(this@MainScreenActivity, t.message , Toast.LENGTH_SHORT).show()
//                cities.add("City??")
                Log.i("bb","CitySlot222222222")
            }
        })

        return centerCourtSlotList
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
    private fun sendGetCenter() : ArrayList<String> {
        val parameters: MutableMap<String, String> = HashMap()
//        val cityRequest = CityRequest("pcityid" )

        val call = mAPIService?.getCityCenters("C")
        val listOfCenters : ArrayList<String> = ArrayList()

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
}




