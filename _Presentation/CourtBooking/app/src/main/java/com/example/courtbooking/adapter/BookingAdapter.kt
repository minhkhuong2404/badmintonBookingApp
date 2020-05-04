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

class BookingAdapter (private val listBooking : ArrayList<Booking>) : RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {
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
        val currentBooking = listBooking[position]
        val bookingTimeString = currentBooking.getDate() + ' ' + currentBooking.getStart()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val bookingTime = LocalDateTime.parse(bookingTimeString, formatter);
        val currentTime = LocalDateTime.now()
        val paymentStatus = currentBooking.getStatus()
        return if (bookingTime.compareTo(currentTime) < 0 && paymentStatus == 0) {
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
        val currentItem= listBooking[position]
        val start : String = currentItem.getStart()
        val end : String = currentItem.getEnd()
        val city : String = currentItem.getCityId()
        val center : String = currentItem.getCenterId()
        val court : String = currentItem.getCourtId()
        val playerid: String = currentItem.getPlayerId()

        // - replace the contents of the view with that element
        holder.tvId.text = currentItem.getBookingId()
        holder.tvDate.text = currentItem.getDate()
        holder.tvTime.text = "$start - $end"
        holder.tvPlace.text = "$city, $center, $court"
        holder.tvTimestamp.text = currentItem.getTimestamp()

        // setIsRecyclerable: avoid lag when scrolling
        holder.setIsRecyclable(false)

        // on click cancel
        holder.cancelBtn.setOnClickListener {
            Log.i("cancel", "clicked")
            cancelBookingDialog(holder.tvId, playerid, position)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = listBooking.size

    private fun cancelBookingDialog(tvId: TextView, playerid: String, position: Int) {

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
        listBooking.removeAt(position)
        if (listBooking.size == 0){
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
                val resultCode = response.getString("code")

                val msg = if (resultCode == "200") {
                    removeCancelledBooking(position)
                    "Booking #${tvId.text} was cancelled."
                } else {
                    if (resultCode == "CA-005")
                        "Cancellation can only be done before 24 hours of start time."
                    else "Booking #${tvId.text} was not cancelled."
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
