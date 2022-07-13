package com.metoer.clocktracker.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.metoer.clocktracker.base.BaseFragment
import com.metoer.clocktracker.databinding.FragmentCounterBinding
import com.metoer.clocktracker.ui.view.activity.ClockActivity

class CounterFragment : BaseFragment() {

    private var _binding: FragmentCounterBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentCounterBinding.inflate(inflater,container,false)



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        visibleBar()
    }

    override fun visibleBar() {
        (requireActivity() as ClockActivity).visibleBar()
    }
}