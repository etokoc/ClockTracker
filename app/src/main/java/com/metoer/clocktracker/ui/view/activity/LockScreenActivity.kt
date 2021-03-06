package com.metoer.clocktracker.ui.view.activity

import android.os.Bundle
import com.metoer.clocktracker.R
import com.metoer.clocktracker.base.BaseActivity
import com.metoer.clocktracker.other.turnScreenOnAndKeyguardOff

class LockScreenActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_screen)
    }
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        turnScreenOnAndKeyguardOff()
    }
}