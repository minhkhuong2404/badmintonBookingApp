package com.example.courtbooking.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.courtbooking.PlayerBookingActivity
import com.example.courtbooking.R
import com.example.courtbooking.SelectionActivity
import kotlinx.android.synthetic.main.slot.view.*

class SlotAdapter(private val slotList: ArrayList<Slot>, private val parentContext: Context) :
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
//            Log.i(
//                "Slot",
//                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
//            )
//            //clickListener.onClickListener(listOf(currentItem.time), holder.adapterPosition)
//            clickListener.onClickListener(currentItem, holder.adapterPosition)
            createBookingDialog(parentContext, currentItem, "player1", position)
        }
    }

    override fun getItemCount() = slotList.size

    fun createBookingDialog(context: Context, slot: Slot, playerid: String, position: Int) {
        val center = slot.id.split("/")[0]
        val court = slot.id.split("/")[1]
        val time_slot = slot.id.split("/")[2]

        val positiveButtonClick = { dialog: DialogInterface, which: Int ->
            // handle create booking

            // Preparing to next activity
            val toPlayerBookingActivity = Intent(parentContext, PlayerBookingActivity::class.java)
            // To next activity
            startActivity(parentContext, toPlayerBookingActivity, Bundle())
        }
        val negativeButtonClick = { dialog: DialogInterface, which: Int ->
            // handle cancel create booking
        }

        val builder = AlertDialog.Builder(context)
        with(builder)
        {
            setTitle("Create new booking")
            setMessage("Place: $center, $court. Time slot: $time_slot.")
            setPositiveButton("CREATE", DialogInterface.OnClickListener(function = positiveButtonClick))
            setNegativeButton("CANCEL", negativeButtonClick)
            show()
        }
    }
}