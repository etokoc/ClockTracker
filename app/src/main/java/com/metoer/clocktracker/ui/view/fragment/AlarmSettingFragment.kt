package com.metoer.clocktracker.ui.view.fragment

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            button.setOnClickListener {
                val currentTime = Calendar.getInstance()
                val startHour = currentTime.get(Calendar.HOUR_OF_DAY)
                val startMinute = currentTime.get(Calendar.MINUTE)

                TimePickerDialog(
                    requireContext(), { view, horOfDay, minute ->

                    },
                    startHour,
                    startMinute,
                    true
                ).show()
            }
        }

        return binding.root
    }

}