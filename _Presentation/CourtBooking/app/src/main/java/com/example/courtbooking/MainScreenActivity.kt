package com.example.courtbooking

import android.R.attr.path
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedWriter
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
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
    private val bookNum = (0..3).random()

    private var mAPIService: JsonPlaceHolderApi? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://jsonplaceholder.typicode.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val jsonPlaceHolderApi = retrofit.create(
//            JsonPlaceHolderApi::class.java
//        )
        // new
        mAPIService = ApiUtils.aPIService

        // findViewById
        cityChooser = findViewById<Spinner>(R.id.sp_city)
        dateChooser = findViewById<EditText>(R.id.et_date)
        welcomeText = findViewById<TextView>(R.id.welcome_text_1)
        welcomeText2 = findViewById<TextView>(R.id.welcome_text_2)

        // City list store here
        val cities = arrayOf("City#A", "City#B", "City#C", "City#D", "City#E") // For testing

        // Show first item on the cities array on the chosenCity spinner
        cityChooser.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cities)

        // On City Choosing Listener
        cityChooser.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) { /* Just a place holder */ }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                city = cities[position] // For testing
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
            val randomCenter = cities.indexOf(selectedCity) + (1..3).random()
            // Initialize the CENTER recycler view
            initRecyclerViewCenter(randomCenter)
            // Hide welcome texts
            welcomeText.text = ""
            welcomeText2.text = ""
            // Hide Recycler of ViewBooking
            rv_booking.adapter = null
            findViewById<RelativeLayout>(R.id.center_view).bringToFront()
//            sendPost((12..100).random(), "New title","Good morning")
            sendPost()

//            val url =  URL("http://10.0.2.2/api/hello")
//            val httpURLConnection : HttpURLConnection = url.openConnection() as HttpURLConnection
//            httpURLConnection.requestMethod = "POST"
//            httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8")
//            httpURLConnection.setRequestProperty("Accept","application/json")
//            httpURLConnection.doOutput
//            val jsonInputString  = JSONObject("""{"login":"test" ,"password":"test" }""")
//            httpURLConnection.outputStream.use { os ->
//                val input: ByteArray = jsonInputString.toString().toByteArray(Charsets.UTF_8)
//                os.write(input, 0, input.size)
//            }
//            BufferedReader(
//                InputStreamReader(httpURLConnection.inputStream, "utf-8")
//            ).use { br ->
//                val response = StringBuilder()
//                var responseLine: String? = null
//                while (br.readLine().also { responseLine = it } != null) {
//                    response.append(responseLine!!.trim { it <= ' ' })
//                }
//                Log.i("bb",response.toString())
//            }
        }
        // On button clicked Show My Bookings
        b_show_bookings.setOnClickListener {
            // Initialize the BOOKING recycler view
            initRecyclerViewBooking()
            // Hide welcome texts
            welcomeText.text = ""
            welcomeText2.text = ""
            // Hide 'Show Available Slots'
            rv_center.adapter = null
            findViewById<RelativeLayout>(R.id.booking_view).bringToFront()
            sendGet()
//            try {
//                val url = URL("http:10.0.2.2/api/hello?")
//                val conn = url.openConnection() as HttpURLConnection
//                conn.readTimeout = 10000
//                conn.connectTimeout = 15000
//                conn.requestMethod = "GET"
//                conn.doInput = true
//                val builder: Uri.Builder = Uri.Builder()
//                    .appendQueryParameter("name", "khuong")
//                val query: String ?= builder.build().encodedQuery
//                Log.i("bb",query.toString())
//                val os: OutputStream = conn.outputStream
//                val writer = BufferedWriter(
//                    OutputStreamWriter(os, "UTF-8")
//                )
//                writer.write(query.toString())
//                writer.flush()
//                writer.close()
//                os.close()
//                conn.connect()
//                Log.e("ERROR", conn.responseMessage)
//                Log.e("ERROR", conn.requestMethod)
//                Log.e("ERROR", java.lang.String.valueOf(conn.responseCode))
//            } catch (e: Exception) {
//                Log.e("ERROR", e.message.toString())
//            }
        }

    }

    // Generate fake data for testing recycler view
    private fun getBookingList() : ArrayList<Booking> {
        val list = ArrayList<Booking>()

        list += Booking("0001", "27/04/2020", "07:00 - 9:00", "Undue", "City#B, Center#C, Court#12", "06/04/2020 18:43")
        list += Booking("0004", "26/04/2020", "08:00 - 9:00", "Undue", "City#B, Center#1, Court#12", "06/04/2020 18:43")
        list += Booking("0008", "19/04/2020", "08:30 - 9:00", "Overdue", "City#1, Center#2, Court#12", "06/04/2020 18:43")

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
            adapter = BookingAdapter(bookList, this@MainScreenActivity)
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
        val requestFragment = AskForBookingFragment(date, city, center, court, slot, bookNum, this)
        requestFragment.show(fm, "Booking Request")
    }

    override fun callBack(date: String, city: String, center: String, court: String, slot: String) {
        val fm= supportFragmentManager
        val bookingFragment = BookingFragment(date, city, center, court, slot, this, this)
        bookingFragment.show(fm, "Booking Process")

    }

    private fun sendGet() {
        val parameters: MutableMap<String, String> = HashMap()
//        parameters["userId"] = "1"
//        parameters["_sort"] = "id"
//        parameters["_order"] = "desc"
        parameters.put("name" ,"Khuong")

        val gsonCall = Gson()
        val callJson = gsonCall.toJson(parameters)

        val call = mAPIService?.getBookings("Khuong")

        Log.i("bb",callJson.toString())

        call?.enqueue(object : Callback<List<BookingRequest>> {
            override fun onResponse(
                call: Call<List<BookingRequest>>,
                response: Response<List<BookingRequest>>
            ) {
                Log.i("bb",call.toString())
                if (!response.isSuccessful) {
//                    Toast.makeText(this@MainScreenActivity, response.code() , Toast.LENGTH_SHORT).show()
                    Log.i("bb","WORKKKKKKKKKKKKKKK444444444")

                    return
                }
//                val posts = response.body()!!
//                for (post in posts) {
//                    var content = ""
//                    content += """
//                    Code: ${response.code()}
//
//                    """.trimIndent()
//                    content += """
//                    Booking ID: ${post!!.bookingId}
//
//                    """.trimIndent()
//                    content += """
//                    Booking time: ${post!!.bookingTime}
//
//                    """.trimIndent()
//                    content += """
//                    Date: ${post!!.date}
//
//                    """.trimIndent()
//                    content += """
//                    Start Time: ${post!!.startTime}
//
//
//                    """.trimIndent()
//                    content += """
//                    End Time: ${post!!.endTime}
//
//
//                    """.trimIndent()
//                    content += """
//                    City Id: ${post!!.cityId}
//
//
//                    """.trimIndent()
//                    content += """
//                    Center Id: ${post!!.centerId}
//
//
//                    """.trimIndent()
//                    content += """
//                    Court Id: ${post!!.courtId}
//
//
//                    """.trimIndent()
//                    content += """
//                    Facebook Id: ${post!!.facebookId}
//
//
//                    """.trimIndent()
//                    Toast.makeText(this@MainScreenActivity, content , Toast.LENGTH_SHORT).show()
//                }
                Log.i("bb","WORKKKKKKKKKKKKKKK")

            }

            override fun onFailure(
                call: Call<List<BookingRequest>>,
                t: Throwable
            ) {
                Toast.makeText(this@MainScreenActivity, t.message , Toast.LENGTH_SHORT).show()
                Log.i("bb","WORKKKKKKKKKKKKKKK222222222")

            }
        })

    }
    private fun sendPost() {
        val bookingRequest = BookingRequest("booking2", "2020-04-07 09:27:18",
                                            "2022-05-01","10:00:00" , "10:45:00" ,
                                            "city1", "center1", "court1", "player1")
        val fields: MutableMap<String, String> = HashMap()
//        fields["userId"] = "25"
        fields.put("login" ,"test")
        fields.put("password", "test")

        val gsonCall = Gson()
        val callJson = gsonCall.toJson(fields)

//        val requestBody : RequestBody = RequestBody.create(MediaType.parse("application/json"),callJson)

        val call = mAPIService?.createBookings(bookingRequest)

        Log.i("bb",bookingRequest.toString())
//        Log.i("bb",call.toString())

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
                val jsonResponse: BookingRequest? = response.body()
                var content = ""
                    content += """
                    Code: ${response.code()}

                    """.trimIndent()
                    content += """
                    Booking ID: ${bookingRequest!!.pbookingid}

                    """.trimIndent()
                    content += """
                    Booking time: ${bookingRequest!!.ptimestamp}

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
    }

    // override function from BookingAdapter.CancelInterface, open cancel dialog fragment
    override fun moveToCancelFragment(message: String) {
        val fm= supportFragmentManager
        val bookingFragment = CancelBookingFragment(message, this)
        bookingFragment.show(fm, "Cancel Booking")
    }

    fun aaa(view: View) {
        Log.i("bb", "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV")
        Log.i("bb", "ahihi")
        Log.i("bb", view.toString())
    }

    override fun moveToFinishCancel(message: String) {

        val fm= supportFragmentManager

        var bookingFragment = CancelResultFragment(message, false)

        if (message == "0004") {
            // remove the booking in the list
            for (booking in bookList) {
                if (booking.id == message) {
                    bookList.remove(booking)
                    break
                }
            }

            //
            rv_booking.adapter = BookingAdapter(bookList, this@MainScreenActivity)
            bookingFragment = CancelResultFragment(message, true)
        }

        bookingFragment.show(fm, "Cancel Result")

    }

}




