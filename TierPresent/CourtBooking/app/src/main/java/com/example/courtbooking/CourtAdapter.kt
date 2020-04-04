package com.example.courtbooking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.court.view.*

class CourtAdapter(private val courtList: List<Court>) : RecyclerView.Adapter<CourtAdapter.CourtViewHolder>() {

    // Referring to the views for each data item
    class CourtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courtTextView: TextView = itemView.tv_court
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourtViewHolder {

        // Create a new view for "court"
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.court, parent, false)
        // Set the view's size, margins, paddings and layout parameters...

        return CourtViewHolder(itemView)
    }

    // Assign the contents to a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: CourtViewHolder, position: Int) {
        // - get  element from your dataset at this position
        val court = courtList[position]

        // - replace the contents of the view with that element
        holder.courtTextView.text = court.name
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = courtList.size
}