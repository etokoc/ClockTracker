package com.metoer.clocktracker.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.metoer.clocktracker.base.BaseFragment
import com.metoer.clocktracker.databinding.FragmentAlarmSettingBinding
import com.metoer.clocktracker.ui.view.activity.ClockActivity

class AlarmSettingFragment : BaseFragment() {

    private var _binding: FragmentAlarmSettingBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlarmSettingBinding.inflate(inflater, container, false)
        hideBar()

        binding.apply {
            pickertime.setIs24HourView(true)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }


    override fun hideBar() {
        (requireActivity() as ClockActivity).hideBar()
    }
}