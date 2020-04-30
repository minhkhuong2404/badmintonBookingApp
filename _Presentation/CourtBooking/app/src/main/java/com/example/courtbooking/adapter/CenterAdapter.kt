package com.example.courtbooking.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courtbooking.R
import kotlinx.android.synthetic.main.center.view.*
import org.json.JSONArray


class CenterAdapter(
    private val parentContext: Context,
    private val selectedDate: String,
    private val selectedCity: String,
    private val playerId: String,
    private val centerList: JSONArray
) :
    RecyclerView.Adapter<CenterAdapter.CenterViewHolder>() {
    private var viewPool = RecyclerView.RecycledViewPool()

    init{
        // remove center that have no court
        for (i in (centerList.length() - 1) downTo 0) {
            val courtList = centerList.getJSONObject(i).getJSONArray("centerSlots")
            if (courtList.length() == 0) {
                centerList.remove(i)
            }
        }
    }

    class CenterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val centerTextView: TextView = itemView.tv_center
        val recyclerViewCourt: RecyclerView = itemView.findViewById(R.id.rv_court)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.center, parent, false)
        return CenterViewHolder(
            itemView
        )
    }
    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: CenterViewHolder, position: Int) {
        val currentCenter = centerList.getJSONObject(position)
        val centerId = currentCenter.getString("centerId")
        holder.centerTextView.text = centerId
        val courtList = currentCenter.getJSONArray("centerSlots")

        if (courtList.length() == 0) {
            holder.centerTextView.visibility = View.GONE
            holder.recyclerViewCourt.visibility = View.GONE
        } else {
            var count = 0
            for(i in 0 until courtList.length()){
                count += courtList.getJSONObject(i).getJSONArray("courtSlots").length()
            }
            if (count > 0){
                holder.recyclerViewCourt.apply {
                    layoutManager = LinearLayoutManager(holder.recyclerViewCourt.context, LinearLayout.VERTICAL, false)
                    adapter = CourtAdapter(
                        parentContext,
                        selectedDate,
                        selectedCity,
                        centerId,
                        playerId,
                        courtList
                    )
                    setRecycledViewPool(viewPool)
                }
            }
        }
    }

    override fun getItemCount() = centerList.length()
}
