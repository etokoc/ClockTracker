package com.metoer.clocktracker.other

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.Toast

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.invs() {
    this.visibility = View.INVISIBLE
}

fun Context.showToastLong(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Context.showToastShort(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Activity.turnScreenOnAndKeyguardOff() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
        setShowWhenLocked(true)
        setTurnScreenOn(true)
    } else {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )
    }
    with(getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requestDismissKeyguard(this@turnScreenOnAndKeyguardOff, null)
        }
    }
}


