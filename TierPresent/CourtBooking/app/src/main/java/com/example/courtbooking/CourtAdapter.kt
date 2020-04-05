package com.example.courtbooking

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.court.view.*

class CourtAdapter(private val courtList: List<Court>, private val callbackInterface:CallbackInterface) : RecyclerView.Adapter<CourtAdapter.CourtViewHolder>(), SlotAdapter.OnItemClickListener {
    // Create ViewPool for child RecyclerView
    private var viewPool = RecyclerView.RecycledViewPool()
    private var adapterPosition: Int = 0

    // Setting up view holder
    class CourtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Assign to the views for each item
        val courtTextView: TextView = itemView.tv_court

        // Load recycler view of child: rv_court
        val recyclerViewSlot: RecyclerView = itemView.findViewById(R.id.rv_slot)

        //
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
        val currentCourt = courtList[position]

        // - replace the contents of the view with that element
        holder.courtTextView.text = currentCourt.name

        // setIsRecyclerable: avoid lag when scrolling
        holder.setIsRecyclable(false)

        // Call child adapter to show child recyclerview
        holder.recyclerViewSlot.apply {
            layoutManager = GridLayoutManager(holder.recyclerViewSlot.context, 4, GridLayoutManager.VERTICAL, false)
            adapter = SlotAdapter(currentCourt.slotList, this@CourtAdapter)
            setRecycledViewPool(viewPool)
        }



        adapterPosition = holder.adapterPosition

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = courtList.size

    // override onClickListener from SlotAdapter.OnItemClickListener
    override fun onClickListener(item: Slot, position: Int) {
        Log.i("Court", "Transfer Slot to Center")
        Log.i("Position Slot", position.toString())
        Log.i("Position Court", adapterPosition.toString())
        val slotTime = item.id
        callbackInterface.passDataCallback(item)
    }

    // create interface CallbackInterface
    interface CallbackInterface {
        fun passDataCallback(message: Slot)
    }


}