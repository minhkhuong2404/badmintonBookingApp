package com.example.courtbooking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.center.view.*

class CenterAdapter(private val listCenter: List<Center>) : RecyclerView.Adapter<CenterAdapter.CenterViewHolder>() {

    // Add this line when this is a parent adapter of nested recyclerview
    private var viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterViewHolder {
        // This line is always written when create ViewHolder. Replace the item you want to 'slot'
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.center, parent, false)

        return CenterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CenterViewHolder, position: Int) {
        println("center")
        // exampleList: List<ExampleItem>
        val currentCenter = listCenter[position]
        // Depends on the item (in this case the item contains one image, 2 texts)
        // you must add them as below. They are defined in below class 'ExampleViewHolder'
        holder.textView.text = currentCenter.name
        // Call child adapter to show child recyclerview

        holder.recyclerViewCourt.apply {
            layoutManager = LinearLayoutManager(holder.recyclerViewCourt.context, LinearLayout.HORIZONTAL, false)
            adapter = CourtAdapter(currentCenter.courtList)
            setRecycledViewPool(viewPool)
        }
    }

    override fun getItemCount() = listCenter.size

    class CenterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Load recycler view of child: rv_court
        val recyclerViewCourt: RecyclerView = itemView.findViewById(R.id.rv_court)

        val textView: TextView = itemView.tv_center
    }
}