package com.metoer.clocktracker.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.metoer.clocktracker.R
import com.metoer.clocktracker.databinding.FragmentAlarmBinding

class AlarmFragment : Fragment() {

    private var _binding: FragmentAlarmBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlarmBinding.inflate(inflater, container, false)

        binding.apply {

            fabTimePickerDialog.setOnClickListener {
                 Navigation.findNavController(it).navigate(R.id.action_alarmFragment_to_alarmSettingFragment)
            }

        }

        return binding.root
    }

}