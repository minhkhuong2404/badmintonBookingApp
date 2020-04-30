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
import org.json.JSONArray
import org.json.JSONObject

class SlotAdapter(
    private val parentContext: Context,
    private val selectedDate: String,
    private val selectedCity: String,
    private val centerId: String,
    private val courtId: String,
    private val playerId: String,
    private val slotList: JSONArray
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
        val currentSlot = slotList.getJSONObject(position)
        val start = currentSlot.getString("start").substring(0, 5)
        val end = currentSlot.getString("end").substring(0, 5)
        holder.slotBtn.text = "$start - $end"
        holder.setIsRecyclable(false)
        holder.slotBtn.setOnClickListener {
            // Show confirm booking creation dialog
            createBookingDialog(parentContext, currentSlot, position)
        }
    }

    override fun getItemCount() = slotList.length()

    // Initialize create booking dialog when it is called.
    fun createBookingDialog(context: Context, slot: JSONObject, position: Int) {
        val start = slot.getString("start")
        val end = slot.getString("end")

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
            setMessage("On ${selectedDate} from ${start.subSequence(0,5)} to ${end.subSequence(0,5)} in city $selectedCity center $centerId court $courtId. Do you want to create new booking with this slot?")
            setPositiveButton(
                "CREATE",
                DialogInterface.OnClickListener(function = positiveButtonClick)
            )
            setNegativeButton("CANCEL", negativeButtonClick)
            show()
        }
    }
}