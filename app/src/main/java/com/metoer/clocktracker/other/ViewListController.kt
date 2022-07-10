package com.metoer.clocktracker.other

import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.view.iterator

class ViewListController {

    companion object {
        fun getSelection(view: ViewGroup): ArrayList<Boolean> {
            val list = ArrayList<Boolean>()
            for (item in view) {
                if (item is CompoundButton ) {
                    list.add(item.isChecked)
                }
            }
            return list
        }
    }

}