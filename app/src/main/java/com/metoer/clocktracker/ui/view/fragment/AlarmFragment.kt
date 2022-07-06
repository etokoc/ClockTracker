package com.metoer.clocktracker.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.metoer.clocktracker.R
import com.metoer.clocktracker.base.BaseFragment
import com.metoer.clocktracker.databinding.FragmentAlarmBinding
import com.metoer.clocktracker.other.MyAnimations
import com.metoer.clocktracker.ui.view.activity.ClockActivity
import kotlinx.android.synthetic.main.fragment_alarm.*

class AlarmFragment : BaseFragment() {

    private var clicked = false

    private var _binding: FragmentAlarmBinding? = null
    private val binding
        get() = _binding!!

    val ani = MyAnimations()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlarmBinding.inflate(inflater, container, false)

        binding.apply {

            fabTimePickerDialog.setOnClickListener {
                onAddButtonClicked()
                Navigation.findNavController(it)
                    .navigate(R.id.action_alarmFragment_to_alarmSettingFragment)
            }

        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        visibleBar()
    }

    private fun onAddButtonClicked() {
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            fabTimePickerDialog.startAnimation(
                ani.animationSet(
                    requireContext(),
                    R.anim.rotate_open
                )
            )
        } else {
            fabTimePickerDialog.startAnimation(
                ani.animationSet(
                    requireContext(),
                    R.anim.rotate_close
                )
            )
        }
    }

    override fun visibleBar() {
        (requireActivity() as ClockActivity).visibleBar()
    }


}