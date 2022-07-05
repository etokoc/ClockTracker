package com.metoer.clocktracker.ui.view.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.metoer.clocktracker.R
import kotlinx.android.synthetic.main.activity_main.*

class ClockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI()
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.home_fragment)
        clockMenuBar.setupWithNavController(navController)
    }

    // This snippet hides the system bars.
    private fun hideSystemUI() {

    }
}