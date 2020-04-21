package com.example.courtbooking.booking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.courtbooking.R
import kotlinx.android.synthetic.main.booking.view.*

class BookingAdapter (private val listBooking : ArrayList<Booking>, private var cancelInterface: CancelInterface) : RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {
    private var bookingViewTypeDefault = 0
    private var bookingViewTypeOverdue = 1

    // Setting up view holder
    class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Assign to the views for each item
        val tvId : TextView = itemView.tv_booking_id
        val tvDate : TextView = itemView.tv_date
        val tvTime : TextView = itemView.tv_time
        val tvPaymentStatus : TextView = itemView.tv_payment_status
        val tvCourt : TextView = itemView.tv_court
        val tvCreatedOn : TextView = itemView.tv_created_on
        val cancelBtn: Button = itemView.cancelButton
    }
    // Determine COURTVIEWTYPE of item
    override fun getItemViewType(position: Int): Int {
        return if (listBooking[position].paymentStatus == "Overdue") {
            bookingViewTypeOverdue
        } else {
            bookingViewTypeDefault
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        // Create a new view for "court"
        return if (viewType == bookingViewTypeOverdue) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pending_booking, parent, false)
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
        val currentItem = listBooking[position]

        // - replace the contents of the view with that element
        holder.tvId.text = currentItem.id
        holder.tvDate.text = currentItem.date
        holder.tvTime.text = currentItem.time
        holder.tvCourt.text = currentItem.court
        holder.tvCreatedOn.text = currentItem.createdOn

        // setIsRecyclerable: avoid lag when scrolling
        holder.setIsRecyclable(false)

        holder.cancelBtn.setOnClickListener {
            cancelInterface.moveToCancelFragment(currentItem.id)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = listBooking.size

    interface CancelInterface {
        fun moveToCancelFragment(message: String)
    }

}