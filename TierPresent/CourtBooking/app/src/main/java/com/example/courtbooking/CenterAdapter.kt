package com.example.courtbooking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.center.view.*

class CenterAdapter(private val listSlot: List<Center>) : RecyclerView.Adapter<CenterAdapter.CenterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterViewHolder {
        // This line is always written when create ViewHolder. Replace the item you want to 'slot'
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.center, parent, false)

        return CenterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CenterViewHolder, position: Int) {
        // exampleList: List<ExampleItem>
        val currentItem = listSlot[position]
        // Depends on the item (in this case the item contains one image, 2 texts)
        // you must add them as below. They are defined in below class 'ExampleViewHolder'
        holder.textView.text = currentItem.name
    }

    override fun getItemCount() = listSlot.size

    class CenterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.tv_center
    }
}