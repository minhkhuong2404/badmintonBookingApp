package com.example.courtbooking

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.slot.view.*

class SlotAdapter(private val listSlot: List<Slot>, var clickListener: OnItemClickListener) : RecyclerView.Adapter<SlotAdapter.SlotViewHolder>() {

    // Setting up view holder
    class SlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Assign to the views for each item
        val textView: Button = itemView.b_slot
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotViewHolder {
        // Create a new view for "court"
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.slot, parent, false)
        // Set the view's size, margins, paddings and layout parameters...

        return SlotViewHolder(itemView)
    }

    // Assign the contents to a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: SlotViewHolder, position: Int) {
        // - get  element from your dataset at this position
        val currentItem = listSlot[position]

        // - replace the contents of the view with that element
        holder.textView.text = currentItem.id.split("/").last()

        // setIsRecyclerable: avoid lag when scrolling
        holder.setIsRecyclable(false)

        holder.textView.setOnClickListener {
            Log.i("Slot","XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
            Log.i("Slot","Transfer to court")
            //clickListener.onClickListener(listOf(currentItem.time), holder.adapterPosition)
            clickListener.onClickListener(currentItem, holder.adapterPosition)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = listSlot.size

    // create interface OnItemClickListener
    interface OnItemClickListener {
        fun onClickListener(item: Slot, position: Int)
    }

}