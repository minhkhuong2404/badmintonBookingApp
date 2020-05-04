package com.example.courtbooking.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.courtbooking.CreateBookingActivity
import com.example.courtbooking.R
import kotlinx.android.synthetic.main.slot.view.*
import java.time.LocalDate
import java.time.LocalTime


class SlotAdapter(
    private val parentContext: Context,
    private val selectedDate: String,
    private val selectedCity: String,
    private val centerId: String,
    private val courtId: String,
    private val playerId: String,
    private var slotList: ArrayList<Slot>
) :
    RecyclerView.Adapter<SlotAdapter.SlotViewHolder>() {
    private val PLAYTIME_MINIMUM: Long = 45 // minutes

    init {
        val today = LocalDate.now()
        val chosenDate = LocalDate.parse(selectedDate)

        if (chosenDate.isEqual(today)) {
            // if it is today, check if some slots need to be updated
            val current = LocalTime.now()

            for (i in (slotList.size - 1) downTo 0) {
                val startString = slotList[i].getStart()
                val endString = slotList[i].getEnd()
                val start = LocalTime.parse(startString)
                val end = LocalTime.parse(endString)

                if (current.isAfter(end.minusMinutes(PLAYTIME_MINIMUM))) {
                    // remove slot with length less than PLAYTIME_MINIMUM
                    slotList.removeAt(i)
                } else if (current.isAfter(start)) {
                    // if still available but the current > start: change start
                    slotList[i].setStart(current.toString().substring(0,8))
                }
            }
        }
    }

    class SlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val slotBtn: Button = itemView.b_slot
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.slot, parent, false)
        return SlotViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SlotViewHolder, position: Int) {
        val currentSlot = slotList.get(position)
        val start = currentSlot.getStart().substring(0,5)
        val end = currentSlot.getEnd().substring(0,5)
        holder.slotBtn.text = "$start - $end"
        holder.setIsRecyclable(false)
        holder.slotBtn.setOnClickListener {
            // Show confirm booking creation dialog
            createBookingDialog(parentContext, currentSlot, position)
        }
    }

    override fun getItemCount() = slotList.size

    // Initialize create booking dialog when it is called.
    private fun createBookingDialog(context: Context, slot: Slot, position: Int) {
        val start = slot.getStart()
        val end = slot.getEnd()

        val positiveButtonClick = { dialog: DialogInterface, which: Int ->
            // Prepare intent
            val toCreateBookingActivity = Intent(parentContext, CreateBookingActivity::class.java)
            toCreateBookingActivity.putExtra("player", playerId)
            toCreateBookingActivity.putExtra("city", selectedCity)
            toCreateBookingActivity.putExtra("date", selectedDate)
            toCreateBookingActivity.putExtra("center", centerId)
            toCreateBookingActivity.putExtra("court", courtId)
            toCreateBookingActivity.putExtra("start", start)
            toCreateBookingActivity.putExtra("end", end)

            // Go to CreateBookingActivity
            startActivity(parentContext, toCreateBookingActivity, Bundle())
        }
        val negativeButtonClick = { dialog: DialogInterface, which: Int ->
            // handle cancel create booking
        }

        val builder = AlertDialog.Builder(context)
        with(builder)
        {
            setTitle("Please confirm")
            setMessage("On $selectedDate from ${start.subSequence(0,5)} to ${end.subSequence(0,5)} in city $selectedCity center $centerId court $courtId. Do you want to create new booking with this slot?")
            setPositiveButton(
                "CREATE",
                DialogInterface.OnClickListener(function = positiveButtonClick)
            )
            setNegativeButton("CANCEL", negativeButtonClick)
            show()
        }
    }
}
