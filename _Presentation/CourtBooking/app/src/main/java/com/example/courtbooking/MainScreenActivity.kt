package com.example.courtbooking

import android.app.DatePickerDialog
import android.net.http.HttpResponseCache
import android.os.Bundle
import android.util.JsonReader
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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.io.InputStreamReader
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

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    // random user's number of booking
    private val bookNum = (0..3).random()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        // findViewById
        cityChooser = findViewById<Spinner>(R.id.sp_city)
        dateChooser = findViewById<EditText>(R.id.et_date)
        welcomeText = findViewById<TextView>(R.id.welcome_text_1)
        welcomeText2 = findViewById<TextView>(R.id.welcome_text_2)
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
        et_date.setText("$day/$month/$year")
        //result2.text = et_date.text // For testing

        // On click of date chooser

        dateChooser.setOnClickListener {

            // Setting up date picker dialog
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ _: DatePicker?, mYear: Int, mMonth: Int, mDay: Int ->
                // Add 1, since in Kotlin start from 0 - 11
                val finalMonth : Int = mMonth + 1;
                // Set result to EditText
                et_date.setText("$mDay/$finalMonth/$mYear")
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
            // Initialize the CENTER recycler view
            initRecyclerViewCenter(randomCenter)
            // Hide welcome texts
            welcomeText.text = ""
            welcomeText2.text = ""
            // Hide Recycler of ViewBooking
            rv_booking.adapter = null
            findViewById<RelativeLayout>(R.id.center_view).bringToFront()
//            getTest()
            Log.i("bb","SUCCESSSSSSSSSSSSSSSSS")
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
            Log.i("bb","WORKKKKKKKKKKKKKKK")

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
        }
    }

    private fun initRecyclerViewCenter(numOfCenter: Int){
        // Calling the recycler view for Center
        centerList = getCenterCourtSlotList(numOfCenter)
        rv_center.apply {
            layoutManager = LinearLayoutManager(this@MainScreenActivity, LinearLayout.VERTICAL, false)
            adapter = CenterAdapter(centerList, this@MainScreenActivity)
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
//        sendGet(center, court, slot)
    }

    override fun callBack(date: String, city: String, center: String, court: String, slot: String) {
        val fm= supportFragmentManager
        val bookingFragment = BookingFragment(date, city, center, court, slot, this)
        bookingFragment.show(fm, "Booking Process")

    }

    fun sendGet() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val jsonPlaceHolderApi = retrofit.create(
            JsonPlaceHolderApi::class.java
        )

        val call = jsonPlaceHolderApi.getPosts()

        call!!.enqueue(object : Callback<List<Post>> {
            override fun onResponse(
                call: Call<List<Post>>,
                response: Response<List<Post>>
            ) {
                if (!response.isSuccessful) {
                    Log.i("bb","Code: " + response.code())
                    return
                }
                val posts = response.body()!!
                for (post in posts) {
                    var content = ""
                    content += """
                        ID: ${post.id}
                        
                        """.trimIndent()
                    content += """
                        User ID: ${post.userId}
                        
                        """.trimIndent()
                    content += """
                        Title: ${post.title}
                        
                        """.trimIndent()
                    content += """
                        Text: ${post.text}
                        
                        
                        """.trimIndent()
                    Log.i("bb","SUCCESSSSSSSSSSS")
                }
            }

            override fun onFailure(
                call: Call<List<Post>>,
                t: Throwable
            ) {
                Log.i("bb",t.message)
            }
        })
    }
    fun getTest() {
//        var sb = StringBuilder()
        val http = "https://api.github.com/"

//        var urlConnection: HttpURLConnection? = null
        var urlConnection: HttpURLConnection? = null
        val url = URL(http)

        urlConnection = url.openConnection() as HttpURLConnection
//            urlConnection.doInput = true
//            urlConnection.doOutput = true
        urlConnection.requestMethod = "GET"
//            urlConnection.connectTimeout = 10000
//            urlConnection.readTimeout = 10000
        urlConnection.setRequestProperty("User-Agent", "my-rest-app-v0.1")
        urlConnection.setRequestProperty(
            "Accept",
            "application/vnd.github.v3+json"
        )
        urlConnection.setRequestProperty(
            "Contact-Me",
            "hathibelagal@example.com"
        )
        urlConnection.connect()

        //Create JSONObject here
//            val gson = Gson()
//            val test = test("K", 21, "K@gmail.com")
//            val json = gson.toJson(test)
//
//            val out = OutputStreamWriter(urlConnection!!.outputStream)
//            out.write(json.toString())
//            out.close()
//            val HttpResult = urlConnection!!.responseCode
        if (urlConnection.getResponseCode() == 200) {
            val responseBody: InputStream = urlConnection.getInputStream()
            val responseBodyReader = InputStreamReader(responseBody, "UTF-8")
            val jsonReader = JsonReader(responseBodyReader)
            jsonReader.beginObject() // Start processing the JSON object

            while (jsonReader.hasNext()) { // Loop through all keys
                val key: String = jsonReader.nextName() // Fetch the next key
                if (key == "organization_url") { // Check if desired key
                    // Fetch the value as a String
                    val value: String = jsonReader.nextString()
                    Log.i("bb", value)

                    break // Break out of the loop
                } else {
                    jsonReader.skipValue() // Skip values of other keys
                }
            }
            jsonReader.close()
            urlConnection.disconnect()
        }
    }

    fun sendTest(){
        val httpbinEndpoint = URL("http://localhost:8000/api/hello")
        val myConnection =
            httpbinEndpoint.openConnection() as HttpURLConnection

        myConnection.requestMethod = "POST"
        // Create the data
        val myData = "Marcin"

        // Enable writing
        myConnection.doOutput = true

        // Write the data
        myConnection.outputStream.write(myData.toByteArray())
        val myCache = HttpResponseCache.install(
            cacheDir, 100000L
        )
        if (myCache.getHitCount() > 0) {
            // The cache is working
            Log.i("bb","WORK!!!!!!!!!")
        }
    }

    override fun callBackFail() {
        val fm= supportFragmentManager
        val bookingFragment = MoreThan3Fragment()
        bookingFragment.show(fm, "Booking Stop")
    }

    override fun confirm(date: String, city: String, center: String, court: String, slot: String) {
        val fm= supportFragmentManager
        val bookingFragment = FinishBookingFragment(date, city, center, court, slot, this)
        bookingFragment.show(fm, "Booking Finish")
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

