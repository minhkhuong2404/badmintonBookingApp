package com.example.courtbooking

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.center.view.*


class CenterAdapter(private val centerList: List<Center>, private val callbackInterface:CallbackInterface) : RecyclerView.Adapter<CenterAdapter.CenterViewHolder>(), CourtAdapter.CallbackInterface {
    // Create ViewPool for child RecyclerView
    private var viewPool = RecyclerView.RecycledViewPool()

    // Referring to the views for each data item
    class CenterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView: TextView = itemView.tv_center

        // Load recycler view of child: rv_court
        val recyclerViewCourt: RecyclerView = itemView.findViewById(R.id.rv_court)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterViewHolder {

        // Create a new view for "center"
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.center, parent, false)
        // Set the view's size, margins, paddings and layout parameters...

        return CenterViewHolder(itemView)
    }
    // Assign the contents to a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: CenterViewHolder, position: Int) {
        // - get  element from your dataset at this position
        val currentCenter = centerList[position]


        // - replace the contents of the view with that element
        holder.textView.text = currentCenter.name

        holder.recyclerViewCourt.apply {
            layoutManager = LinearLayoutManager(holder.recyclerViewCourt.context, LinearLayout.VERTICAL, false)
            adapter = CourtAdapter(currentCenter.courtList, this@CenterAdapter)
            setRecycledViewPool(viewPool)
        }



//        var click: Int = 0
//        holder.textView.setOnClickListener {
//            if (click == 0) {
//                // Call child adapter to show child recyclerview
//                holder.recyclerViewCourt.apply {
//                    layoutManager = LinearLayoutManager(holder.recyclerViewCourt.context, LinearLayout.VERTICAL, false)
//                    adapter = CourtAdapter(currentCenter.courtList)
//                    setRecycledViewPool(viewPool)
//                }
//                click = 1
//            } else {
//                holder.recyclerViewCourt.adapter = null
//                click = 0
//            }
//        }

    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = centerList.size


    // create interface CallbackInterface
    interface CallbackInterface {
        fun passDataCallback(message: Slot)
    }

    // override passDataCallback from CourtAdapter.CallbackInterface
    override fun passDataCallback(message: Slot) {
        Log.i("Center", "Transfer to Main")
        callbackInterface.passDataCallback(message)
    }


}