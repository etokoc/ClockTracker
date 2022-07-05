package com.metoer.clocktracker.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.metoer.clocktracker.R
import kotlinx.android.synthetic.main.activity_main.*

class ClockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController=findNavController(R.id.home_fragment)
        clockMenuBar.setupWithNavController(navController)
    }
}