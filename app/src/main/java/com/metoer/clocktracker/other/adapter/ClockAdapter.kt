package com.metoer.clocktracker.other.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.metoer.clocktracker.R
import com.metoer.clocktracker.data.db.ClockItem
import com.metoer.clocktracker.day.DayController
import com.metoer.clocktracker.other.*
import com.metoer.clocktracker.ui.viewmodel.AlarmViewModel
import kotlinx.android.synthetic.main.item_alarm_list.view.*

class ClockAdapter(
    var items: List<ClockItem>,
    private val viewmodel: AlarmViewModel,
    val deleteFloatButton: FloatingActionButton,
    val addFloatButton: FloatingActionButton
) : RecyclerView.Adapter<ClockAdapter.ListViewHolder>() {
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    val selectedItemForDelete = ArrayList<ClockItem>()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alarm_list, parent, false)
        return ListViewHolder(view)
    }

    private var shouldShowCheckBoxes = false

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
            switchAlarm.visibility = if (shouldShowCheckBoxes) View.INVISIBLE else View.VISIBLE
            alarmDeleteCheckBox.visibility =
                if (shouldShowCheckBoxes) View.VISIBLE else View.INVISIBLE
            //long click
                itemAlarmConstraint.setOnLongClickListener {
                shouldShowCheckBoxes = true
                notifyDataSetChanged()
                deleteFloatButton.show()
                addFloatButton.hide()
                alarmDeleteCheckBox.isChecked = true
                selectedItemForDelete.add(currentItems)
                true
            }
            itemAlarmConstraint.setOnClickListener {
                alarmDeleteCheckBox.isChecked = if (alarmDeleteCheckBox.isChecked) {
                    selectedItemForDelete.remove(currentItems)
                    false
                } else {
                    selectedItemForDelete.add(currentItems)
                    true
                }
            }
            deleteFloatButton.setOnClickListener {
                selectedItemForDelete.forEach {
                    viewmodel.deleteAlarm(it)
                    addFloatButton.show()
                    deleteFloatButton.hide()
                    notifyDataSetChanged()
                }
            }
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