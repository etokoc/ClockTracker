package com.metoer.clocktracker.other.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.metoer.clocktracker.R
import com.metoer.clocktracker.model.RingtoneModel
import com.metoer.clocktracker.other.showToastShort
import kotlinx.android.synthetic.main.ringtone_row_item.view.*

class RingtoneAdapter(var ringtoneList: ArrayList<RingtoneModel>) :
    RecyclerView.Adapter<RingtoneAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.ringtone_row_item, parent, false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.apply {
            ringtoneNameText.text = ringtoneList[position].ringtoneName.toString()
            setOnClickListener {
                context.showToastShort(ringtoneList[position].ringtoneUri.toString())
                findNavController().navigate(
                    R.id.action_ringtoneFragment_to_alarmSettingFragment,
                    bundleOf(
                        Pair(
                            ringtoneList[position].ringtoneUri.toString(),
                            ringtoneList[position].ringtoneName
                        )
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return ringtoneList.size
    }
}