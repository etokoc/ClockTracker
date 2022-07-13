package com.metoer.clocktracker.ui.view.activity

import android.os.Bundle
import android.os.PersistableBundle
import com.metoer.clocktracker.R
import com.metoer.clocktracker.base.BaseActivity

class LockScreenActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_lock_screen)
    }
}