package com.metoer.clocktracker.other.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.metoer.clocktracker.R
import com.metoer.clocktracker.data.db.ClockItem
import com.metoer.clocktracker.model.Alarm
import com.metoer.clocktracker.other.invs
import com.metoer.clocktracker.other.show
import com.metoer.clocktracker.other.textColors
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
            tvAlarmTag.text = "Burada kullanıcının eklediği tag yazacak"
            tvAlarmDescription.invs()
            tvAlarmDescription.text = "Buraya alarmın kaç saat ve dakika sonra çalacağı yazacak"
            switchAlarm.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (isChecked) {
                    tvAlarmTime.textColors(R.color.black)
                    tvAlarmAgain.textColors(R.color.alarmTextColorSelected)
                    tvAlarmDescription.textColors(R.color.alarmTextColorSelected)
                    tvAlarmTag.textColors(R.color.alarmTextColorSelected)
                    tvAlarmDescription.show()
                } else {
                    tvAlarmTime.textColors(R.color.alarmTextColorUnSelected)
                    tvAlarmAgain.textColors(R.color.alarmTextColorUnSelected)
                    tvAlarmDescription.textColors(R.color.alarmTextColorUnSelected)
                    tvAlarmTag.textColors(R.color.alarmTextColorUnSelected)
                    tvAlarmDescription.invs()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}