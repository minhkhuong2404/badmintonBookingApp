package com.example.courtbooking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courtbooking.R
import kotlinx.android.synthetic.main.court.view.*
import org.json.JSONArray

class CourtAdapter(
    private val parentContext: Context,
    private val selectedDate: String,
    private val selectedCity: String,
    private val centerId: String,
    private val playerId: String,
    private val courtList: JSONArray

    ) : RecyclerView.Adapter<CourtAdapter.CourtViewHolder>() {

    // Constants
    private var COURT_VIEW_TYPE_DEFAULT = 0
    private var COURT_VIEW_TYPE_BLUE = 1
    private var COLUMN_OF_SLOT = 3

    // Create view pool for child recycler view
    private var viewPool = RecyclerView.RecycledViewPool()
    private var adapterPosition: Int = 0

    class CourtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Assign to the views for each item
        val courtTextView: TextView = itemView.tv_court
        // Load recycler view of child: rv_court
        val recyclerViewSlot: RecyclerView = itemView.findViewById(R.id.rv_slot)
    }

    // Determine court view type of item
    override fun getItemViewType(position: Int): Int {
        if (position % 2 == 0) {
            return COURT_VIEW_TYPE_BLUE
        } else {
            return COURT_VIEW_TYPE_DEFAULT
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourtViewHolder {
        // Create a new view for "court"
        return if (viewType == COURT_VIEW_TYPE_BLUE) {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.court, parent, false)
            CourtViewHolder(
                itemView
            )
        } else {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.court_gray, parent, false)
            CourtViewHolder(
                itemView
            )
        }
    }

    // Assign the contents to a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: CourtViewHolder, position: Int) {
        val currentCourt = courtList.getJSONObject(position)
        val courtId = currentCourt.getString("courtId")
        holder.courtTextView.text = courtId
        val slotList = currentCourt.getJSONArray("courtSlots")
        holder.setIsRecyclable(false)
        if (slotList.length() == 0){
            holder.recyclerViewSlot.visibility = View.GONE
            holder.courtTextView.visibility = View.GONE
        } else {
            // Call child adapter to show child recycler view
            holder.recyclerViewSlot.apply {
                layoutManager = GridLayoutManager(
                    holder.recyclerViewSlot.context,
                    COLUMN_OF_SLOT,
                    GridLayoutManager.VERTICAL,
                    false
                )
                adapter = SlotAdapter(
                    parentContext,
                    selectedDate,
                    selectedCity,
                    centerId,
                    courtId,
                    playerId,
                    slotList
                )
                setRecycledViewPool(viewPool)
            }
            adapterPosition = holder.adapterPosition
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = courtList.length()
}