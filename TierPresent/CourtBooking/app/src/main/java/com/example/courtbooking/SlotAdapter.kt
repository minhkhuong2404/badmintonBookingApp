package com.example.courtbooking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.slot.view.*

class SlotAdapter(private val listSlot: List<Slot>) : RecyclerView.Adapter<SlotAdapter.SlotViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotViewHolder {
        // This line is always written when create ViewHolder. Replace the item you want to 'slot'
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.slot, parent, false)

        return SlotViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SlotViewHolder, position: Int) {
        // exampleList: List<ExampleItem>
        val currentItem = listSlot[position]
        // Depends on the item (in this case the item contains one image, 2 texts)
        // you must add them as below. They are defined in below class 'ExampleViewHolder'
        holder.textView.text = currentItem.time
    }

    override fun getItemCount() = listSlot.size

    class SlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.tv_slot
    }
}