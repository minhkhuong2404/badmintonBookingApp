package com.example.courtbooking.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.courtbooking.R
import com.example.courtbooking.request.ApiUtils
import com.example.courtbooking.request.MySingleton
import kotlinx.android.synthetic.main.booking.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BookingAdapter (private val listBooking : JSONArray) : RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {
    private var BOOKING_VIEW_TYPE_DEFAULT = 0
    private var BOOKING_VIEW_TYPE_OVERDUE = 1

    // Setting up view holder
    class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Assign to the views for each item
        val tvId : TextView = itemView.tv_booking_id
        val tvDate : TextView = itemView.tv_date
        val tvTime : TextView = itemView.tv_time
        val tvPlace : TextView = itemView.tv_court
        val tvTimestamp : TextView = itemView.tv_created_on
        val cancelBtn: Button = itemView.cancelButton
    }
    // Determine COURTVIEWTYPE of item
    override fun getItemViewType(position: Int): Int {
        val booking : JSONObject = listBooking.getJSONObject(position)
        val booking_time_string = Date(booking.getLong("date")).toString() + ' ' + booking.getString("start")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val booking_time = LocalDateTime.parse(booking_time_string, formatter);
        val current_time = LocalDateTime.now()
        val payment_status = booking.getInt("status")
        return if (booking_time.compareTo(current_time) < 0 && payment_status == 0) {
            BOOKING_VIEW_TYPE_OVERDUE
        } else {
            BOOKING_VIEW_TYPE_DEFAULT
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {

        return if (viewType == BOOKING_VIEW_TYPE_OVERDUE) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.booking_overdue, parent, false)
            BookingViewHolder(
                itemView
            )
        } else {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.booking, parent, false)
            BookingViewHolder(
                itemView
            )
        }

        // Set the view's size, margins, paddings and layout parameters...

    }

    // Assign the contents to a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        // - get  element from your dataset at this position
        val currentItem : JSONObject = listBooking.getJSONObject(position)
        val start : String = currentItem.getString("start")
        val end : String = currentItem.getString("end")
        val city : String = currentItem.getString("cityId")
        val center : String = currentItem.getString("centerId")
        val court : String = currentItem.getString("courtId")
        val playerid: String = currentItem.getString("playerId")

        // - replace the contents of the view with that element
        holder.tvId.text = currentItem.getInt("bookingId").toString()
        holder.tvDate.text = Date(currentItem.getLong("date")).toString()
        holder.tvTime.text = "$start - $end"
        holder.tvPlace.text = "$city, $center, $court"
        holder.tvTimestamp.text = Timestamp(currentItem.getLong("timestamp")).toString()

        // setIsRecyclerable: avoid lag when scrolling
        holder.setIsRecyclable(false)

        // on click cancel
        holder.cancelBtn.setOnClickListener {
            Log.i("cancel", "clicked")
            cancelBookingDialog(holder.tvId, playerid, position)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = listBooking.length()

    fun cancelBookingDialog(tvId: TextView, playerid: String, position: Int) {

        val positiveButtonClick = { dialog: DialogInterface, which: Int ->
            // handle cancel booking here
            requestCancelBooking(tvId, playerid, position)
        }
        val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        }

        val builder = AlertDialog.Builder(tvId.context)

        with(builder)
        {
            setTitle("Confirm cancellation")
            setMessage("Cancellation must be done before 24 hours of start time. Please confirm to cancel booking #${tvId.text}.")
            setPositiveButton("CONFIRM", DialogInterface.OnClickListener(function = positiveButtonClick))
            setNegativeButton("KEEP", negativeButtonClick)
            show()
        }
    }

    private fun removeCancelledBooking(position: Int) {
        listBooking.remove(position)
        if (listBooking.length() == 0){
        }
        notifyDataSetChanged()
    }

    // request cancel booking
    private fun requestCancelBooking(tvId: TextView, playerId: String, position: Int) {
        var cancelObj = JSONObject()
        cancelObj.put("id", tvId.text)
        cancelObj.put("playerid", playerId)

        // Get a RequestQueue
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, ApiUtils.URL_BOOKING_CANCEL, cancelObj,
            Response.Listener { response ->
                val result_code = response.getString("code")
                var msg = ""
                if (result_code == "200") {
                    removeCancelledBooking(position)
                    msg = "Booking #${tvId.text} was cancelled."
                } else {
                    if (result_code == "CA-005")
                        msg = "Cancellation can only be done before 24 hours of start time."
                    else msg = "Booking #${tvId.text} was not cancelled."
                }
                Toast.makeText(tvId.context, msg, Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    tvId.context,
                    "Cancellation Error: #${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(tvId.context).addToRequestQueue(jsonObjectRequest)
    }
}