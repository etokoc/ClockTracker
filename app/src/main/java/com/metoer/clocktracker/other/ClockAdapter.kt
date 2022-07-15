package com.metoer.clocktracker.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.metoer.clocktracker.R
import com.metoer.clocktracker.model.Alarm

class ClockAdapter(
    var alarmList: ArrayList<Alarm>
) : RecyclerView.Adapter<ClockAdapter.ListViewHolder>() {
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alarm_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.itemView.apply {

        }
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }
}