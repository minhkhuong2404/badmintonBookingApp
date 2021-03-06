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
    private val centerList: ArrayList<Center>
) :
    RecyclerView.Adapter<CenterAdapter.CenterViewHolder>() {
    private var viewPool = RecyclerView.RecycledViewPool()

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
        val currentCenter = centerList.get(position)
        val centerId = currentCenter.getId()
        holder.centerTextView.text = centerId
        val courtList = currentCenter.getCourtList()

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

    override fun getItemCount() = centerList.size
}
