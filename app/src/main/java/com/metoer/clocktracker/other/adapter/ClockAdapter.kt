package com.metoer.clocktracker.other.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.metoer.clocktracker.R
import com.metoer.clocktracker.data.db.ClockItem
import com.metoer.clocktracker.model.Alarm
import com.metoer.clocktracker.other.invs
import com.metoer.clocktracker.other.show
import kotlinx.android.synthetic.main.item_alarm_list.view.*

class ClockAdapter(
    var items: List<ClockItem>
) : RecyclerView.Adapter<ClockAdapter.ListViewHolder>() {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alarm_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val currentItems = items[position]
        holder.itemView.apply {
            tvAlarmTime.text = currentItems.time
            tvAlarmAgain.text = currentItems.date.toString()
            tvAlarmDescription.invs()
            tvAlarmDescription.text = "Alarm 9 saat 00 dk sonra çalacak"
            switchAlarm.setOnCheckedChangeListener { compoundButton, isChecked ->
                if(isChecked)
                    tvAlarmDescription.show()
                else
                    tvAlarmDescription.invs()
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}