package com.metoer.clocktracker.ui.view.activity

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.metoer.clocktracker.BuildConfig
import com.metoer.clocktracker.R
import com.metoer.clocktracker.base.BaseActivity
import com.metoer.clocktracker.other.Constants
import com.metoer.clocktracker.other.hide
import com.metoer.clocktracker.other.invs
import com.metoer.clocktracker.other.show
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class ClockActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.home_fragment)
        clockMenuBar.setupWithNavController(navController)
        if (deviceHasMIUI() == true) {
            showPermissionAlert()
        }
    }

    private fun showPermissionAlert() {
        val sharedPreferences = this.getSharedPreferences(
            Constants.SHAREDPREFENCES_NAME,
            MODE_PRIVATE
        )
        if (!sharedPreferences.getBoolean("isQuestion", false)) {
            AlertDialog.Builder(this).apply {
                setTitle(R.string.permission_title)
                setMessage(R.string.permission_warning)
                setPositiveButton(R.string.go_settings_text) { _, _ ->
                    sharedPreferences.edit().apply {
                        putBoolean("isQuestion", true)
                    }.apply()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        val settingsIntent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                        )
                        startActivity(settingsIntent)
                    }
                }
                setNegativeButton(R.string.reddet) { _, _ ->
                    sharedPreferences.edit().apply {
                        putBoolean("isQuestion", true)
                    }.apply()
                }
                show()
            }
        }
    }

    fun deviceHasMIUI(): Boolean? {
        val line: String
        var input: BufferedReader? = null
        try {
            val p = Runtime.getRuntime().exec("getprop ro.miui.ui.version.name")
            input = BufferedReader(InputStreamReader(p.inputStream), 1024)
            line = input.readLine()
            input.close()
        } catch (ex: IOException) {
            return null
        } finally {
            if (input != null) {
                try {
                    input.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return !line.equals("")
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