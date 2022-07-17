package com.metoer.clocktracker.other.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.metoer.clocktracker.R
import com.metoer.clocktracker.model.RingtoneModel
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
        holder.itemView.ringtoneNameText.text = ringtoneList[position].ringtoneName.toString()
    }

    override fun getItemCount(): Int {
        return ringtoneList.size
    }
}