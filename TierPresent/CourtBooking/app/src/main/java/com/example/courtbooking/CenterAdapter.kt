package com.example.courtbooking

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.center.view.*


class CenterAdapter(private val listCenter: List<Center>) : RecyclerView.Adapter<CenterAdapter.CenterViewHolder>() {

    // Referring to the views for each data item
    class CenterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.tv_center

        // Load recycler view of child: rv_court
        val recyclerViewCourt: RecyclerView = itemView.findViewById(R.id.rv_court)
    }

    // Create ViewPool for child RecyclerView
    private var viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterViewHolder {

        // Create a new view for "center"
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.center, parent, false)
        // Set the view's size, margins, paddings and layout parameters...

        return CenterViewHolder(itemView)
    }
    // Assign the contents to a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: CenterViewHolder, position: Int) {
        // - get  element from your dataset at this position
        val currentCenter = listCenter[position]

        // - replace the contents of the view with that element
        holder.textView.text = currentCenter.name

        // Call child adapter to show child recyclerview
        holder.recyclerViewCourt.apply {
            layoutManager = LinearLayoutManager(holder.recyclerViewCourt.context, LinearLayout.HORIZONTAL, false)
            adapter = CourtAdapter(currentCenter.courtList)
            setRecycledViewPool(viewPool)
        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = listCenter.size
}