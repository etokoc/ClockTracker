package com.metoer.clocktracker.ui.view.activity

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.metoer.clocktracker.R
import com.metoer.clocktracker.base.BaseActivity
import com.metoer.clocktracker.other.hide
import com.metoer.clocktracker.other.invs
import com.metoer.clocktracker.other.show
import com.metoer.clocktracker.other.turnScreenOnAndKeyguardOff
import kotlinx.android.synthetic.main.activity_main.*

class ClockActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        turnScreenOnAndKeyguardOff()
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.home_fragment)
        clockMenuBar.setupWithNavController(navController)
    }

    override fun hideBar() {
        clockMenuBar.invs()
        ib_Success.show()
        ibExit.show()
    }

    override fun visibleBar() {
        clockMenuBar.show()
        ib_Success.hide()
        ibExit.hide()
    }
}