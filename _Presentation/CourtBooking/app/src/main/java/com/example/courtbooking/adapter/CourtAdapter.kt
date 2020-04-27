package com.example.courtbooking.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courtbooking.R
import kotlinx.android.synthetic.main.court.view.*

class CourtAdapter(
    private val courtList: ArrayList<Court>,
    private val parentContext : Context
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
        val currentCourt = courtList[position]
        holder.courtTextView.text = currentCourt.name
        holder.setIsRecyclable(false)

        // Call child adapter to show child recycler view
        holder.recyclerViewSlot.apply {
            layoutManager = GridLayoutManager(
                holder.recyclerViewSlot.context,
                COLUMN_OF_SLOT,
                GridLayoutManager.VERTICAL,
                false
            )
            adapter = SlotAdapter(
                currentCourt.slotList,
                parentContext
            )
            setRecycledViewPool(viewPool)
        }
        adapterPosition = holder.adapterPosition
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = courtList.size

//    // override onClickListener from SlotAdapter.OnItemClickListener
//    override fun onClickListener(item: Slot, position: Int) {
//        Log.i("Court", "Transfer Slot to Center")
//        Log.i("Position Slot", position.toString())
//        Log.i("Position Court", adapterPosition.toString())
//        val slotTime = item.id
//        callbackInterface.passDataCallback(item)
//    }
//
//    // create interface CallbackInterface
//    interface CallbackInterface {
//        fun passDataCallback(message: Slot)
//    }
}