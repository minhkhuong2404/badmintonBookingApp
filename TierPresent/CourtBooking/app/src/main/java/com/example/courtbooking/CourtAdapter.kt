package com.example.courtbooking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.court.view.*

class CourtAdapter(private val courtList: List<Court>) : RecyclerView.Adapter<CourtAdapter.CourtViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourtViewHolder {
        // This line is always written when create ViewHolder. Replace the item you want to 'slot'
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.court, parent, false)

        return CourtViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CourtViewHolder, position: Int) {
        // exampleList: List<ExampleItem>
        println("court item")
        val court = courtList[position]
        // Depends on the item (in this case the item contains one image, 2 texts)
        // you must add them as below. They are defined in below class 'ExampleViewHolder'
        holder.textView.text = court.name
    }

    override fun getItemCount() = courtList.size

    class CourtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.tv_court
    }
}