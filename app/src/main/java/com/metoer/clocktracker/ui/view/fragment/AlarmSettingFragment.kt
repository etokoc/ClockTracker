package com.metoer.clocktracker.ui.view.fragment

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.metoer.clocktracker.R
import com.metoer.clocktracker.databinding.FragmentAlarmSettingBinding
import java.util.*

class AlarmSettingFragment : Fragment() {

    private var _binding: FragmentAlarmSettingBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding=FragmentAlarmSettingBinding.inflate(inflater,container,false)

        binding.apply {
            pickertime.setIs24HourView(true)
        }

        return binding.root
    }

}