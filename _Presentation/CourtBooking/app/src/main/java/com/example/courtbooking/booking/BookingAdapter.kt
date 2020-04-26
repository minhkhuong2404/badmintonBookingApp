package com.example.courtbooking.booking

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.courtbooking.R
import kotlinx.android.synthetic.main.booking.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.sql.Date
import java.sql.Timestamp
import java.util.*

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
        val date = Date(booking.getLong("date"))
        val payment_status = booking.getInt("status")
        val date_now = Date(Calendar.getInstance().getTimeInMillis())
        return if (date.compareTo(date_now) < 0 && payment_status == 0) {
            BOOKING_VIEW_TYPE_OVERDUE
        } else {
            BOOKING_VIEW_TYPE_DEFAULT
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {

        return if (viewType == BOOKING_VIEW_TYPE_OVERDUE) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.booking_overdue, parent, false)
            BookingViewHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.booking, parent, false)
            BookingViewHolder(itemView)
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

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = listBooking.length()

    interface CancelInterface {
        fun moveToCancelFragment(id: Int)
    }
}