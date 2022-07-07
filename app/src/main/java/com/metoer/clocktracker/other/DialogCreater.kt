package com.metoer.clocktracker.other

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View

//item -> inflate edilecek layout
//style -> style edilecek layout
//animation -> animation edilecek layout
class DialogCreater(val context: Context, val style: Int, val item: Int, val animation: Int) {
    fun showDialog(): Dialog {
        val bottomDialog = Dialog(context, style)
        val contentView: View =
            LayoutInflater.from(context).inflate(item, null)
        bottomDialog.setContentView(contentView)
        val layoutParams = contentView.layoutParams
        layoutParams.width = context.resources.displayMetrics.widthPixels
        contentView.layoutParams = layoutParams
        bottomDialog.window!!.setGravity(Gravity.BOTTOM)
        bottomDialog.setCanceledOnTouchOutside(true)
        bottomDialog.window!!.setWindowAnimations(animation)
        bottomDialog.show()

        return bottomDialog
    }
}