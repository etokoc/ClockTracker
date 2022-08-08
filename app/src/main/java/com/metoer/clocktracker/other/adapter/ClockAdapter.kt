package com.metoer.clocktracker.other.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.metoer.clocktracker.R
import com.metoer.clocktracker.data.db.ClockItem
import com.metoer.clocktracker.day.DayController
import com.metoer.clocktracker.other.*
import com.metoer.clocktracker.ui.viewmodel.AlarmViewModel
import kotlinx.android.synthetic.main.item_alarm_list.view.*

class ClockAdapter(
    var items: List<ClockItem>,
    private val viewmodel: AlarmViewModel,
    private val showFabDelete: (Boolean,Boolean) -> Unit,
) : RecyclerView.Adapter<ClockAdapter.ListViewHolder>() {

    private var isEnable = false
    private val itemSelectedList = mutableListOf<Int>()

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alarm_list, parent, false)
        return ListViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val currentItems = items[position]
        holder.itemView.apply {
            alarmDeleteCheckBox.hide()
            switchAlarm.show()
            if (currentItems.enableAlarm == true) {
                setSwitchStatus(true, currentItems, this)
            }
            switchAlarm.isChecked = currentItems.enableAlarm
            switchAlarm.setOnCheckedChangeListener { compoundButton, isChecked ->
                viewmodel.updateAdd(setSwitchStatus(isChecked, currentItems, this))
            }
            //long click
            itemAlarmConstraint.setOnLongClickListener {
                selectItem(holder, currentItems, position)
                true
            }
            itemAlarmConstraint.setOnClickListener {
                if (itemSelectedList.contains(position)) {
                    //itemSelectedList.removeAt(position)
                    switchAlarm.show()
                    alarmDeleteCheckBox.hide()
                    currentItems.enableAlarm = false
                    if (itemSelectedList.isEmpty()) {
                        showFabDelete(false,true)
                        isEnable = false
                    }
                } else if (isEnable) {
                    selectItem(holder, currentItems, position)
                }
            }
        }
    }

    fun selectItem(holder: ListViewHolder, item: ClockItem, position: Int) {
        isEnable = true
        item.enableAlarm = true
        showFabDelete(true,false)
        itemSelectedList.add(position)
        holder.itemView.apply {
            switchAlarm.hide()
            alarmDeleteCheckBox.show()
        }
    }

    fun deleteSelectItem() {
        if (itemSelectedList.isNotEmpty()) {
            isEnable = false
            itemSelectedList
            //viewmodel.deleteAlarm(currentItems)
        }
    }

    fun setSwitchStatus(isChecked: Boolean, currentItems: ClockItem, view: View): ClockItem {
        view.apply {
            tvAlarmTime.text = currentItems.time
            tvAlarmAgain.text = DayController().getDayString(currentItems.date)
            tvAlarmTag.text = currentItems.tag
            tvAlarmDescription.invs()
            tvAlarmDescription.text =
                DayController().remaining(currentItems.time, currentItems.date)
            if (isChecked) {
                currentItems.enableAlarm = true
                tvAlarmTime.textColors(R.color.black)
                tvAlarmAgain.textColors(R.color.alarmTextColorSelected)
                tvAlarmDescription.textColors(R.color.alarmTextColorSelected)
                tvAlarmTag.textColors(R.color.alarmTextColorSelected)
                tvAlarmDescription.show()
            } else {
                currentItems.enableAlarm = false
                tvAlarmTime.textColors(R.color.alarmTextColorUnSelected)
                tvAlarmAgain.textColors(R.color.alarmTextColorUnSelected)
                tvAlarmDescription.textColors(R.color.alarmTextColorUnSelected)
                tvAlarmTag.textColors(R.color.alarmTextColorUnSelected)
                tvAlarmDescription.invs()
            }
        }
        return currentItems
    }
}