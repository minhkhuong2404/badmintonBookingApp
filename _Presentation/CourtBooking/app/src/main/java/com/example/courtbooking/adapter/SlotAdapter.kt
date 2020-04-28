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

class SlotAdapter(
    private val slotList: ArrayList<Slot>,
    private val parentContext: Context,
    private val playerId: String,
    private val selectedCity: String,
    private val selectedDate: String
) :
    RecyclerView.Adapter<SlotAdapter.SlotViewHolder>() {

    class SlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val slotBtn: Button = itemView.b_slot
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.slot, parent, false)
        return SlotViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SlotViewHolder, position: Int) {
        val currentItem = slotList[position]
        holder.slotBtn.text = currentItem.id.split("/").last()
        holder.setIsRecyclable(false)
        holder.slotBtn.setOnClickListener {
            // Show confirm booking creation dialog
            createBookingDialog(parentContext, currentItem, playerId, position)
        }
    }

    override fun getItemCount() = slotList.size

    fun createBookingDialog(context: Context, slot: Slot, playerid: String, position: Int) {
        val center = slot.id.split("/")[0]
        val court = slot.id.split("/")[1]
        val time_slot = slot.id.split("/")[2]
        val start = time_slot.split(" - ").get(0)
        val end = time_slot.split(" - ").get(1)

        val positiveButtonClick = { dialog: DialogInterface, which: Int ->
            // Prepare intent
            val toCreateBookingActivity = Intent(parentContext, CreateBookingActivity::class.java)
            toCreateBookingActivity.putExtra("player", playerId)
            toCreateBookingActivity.putExtra("city", selectedCity)
            toCreateBookingActivity.putExtra("date", selectedDate)
            toCreateBookingActivity.putExtra("center", center)
            toCreateBookingActivity.putExtra("court", court)
            toCreateBookingActivity.putExtra("start", start)
            toCreateBookingActivity.putExtra("end", end)
            toCreateBookingActivity.putExtra("Minimum length-of-playing", "45")
            toCreateBookingActivity.putExtra("Maximum length-of-playing", "90")

            // Go to CreateBookingActivity
            startActivity(parentContext, toCreateBookingActivity, Bundle())
        }
        val negativeButtonClick = { dialog: DialogInterface, which: Int ->
            // handle cancel create booking
        }

        val builder = AlertDialog.Builder(context)
        with(builder)
        {
            setTitle("Confirm booking creation")
            setMessage("On ${selectedDate} with time slot from $start to $end in city $selectedCity center $center court $court. Do you want to create?")
            setPositiveButton(
                "CREATE",
                DialogInterface.OnClickListener(function = positiveButtonClick)
            )
            setNegativeButton("CANCEL", negativeButtonClick)
            show()
        }
    }
}