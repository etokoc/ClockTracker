package com.metoer.clocktracker.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.metoer.clocktracker.R
import com.metoer.clocktracker.base.BaseFragment
import com.metoer.clocktracker.databinding.FragmentAlarmBinding
import com.metoer.clocktracker.other.MyAnimations
import com.metoer.clocktracker.other.adapter.ClockAdapter
import com.metoer.clocktracker.ui.view.activity.ClockActivity
import com.metoer.clocktracker.ui.view.factories.AlarmViewModelFactory
import com.metoer.clocktracker.ui.viewmodel.AlarmViewModel
import kotlinx.android.synthetic.main.fragment_alarm.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class AlarmFragment : BaseFragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: AlarmViewModelFactory by instance()

    private var clicked = false

    private lateinit var viewmodel: AlarmViewModel
    private lateinit var adapter: ClockAdapter

    private var _binding: FragmentAlarmBinding? = null
    private val binding
        get() = _binding!!

    val ani = MyAnimations()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlarmBinding.inflate(inflater, container, false)

        viewmodel = ViewModelProvider(this, factory).get(AlarmViewModel::class.java)

        viewmodel.apply {
            binding.rvAlarmList.layoutManager = LinearLayoutManager(context)
            personsList.apply {
                observe(viewLifecycleOwner, Observer {
                    adapter = ClockAdapter(this.value!!, viewmodel,fabDeleteAlarmItem,fabTimePickerDialog)
                    binding.rvAlarmList.adapter = adapter

                })
            }
        }

        binding.apply {

            fabTimePickerDialog.setOnClickListener {
                onAddButtonClicked()
                Navigation.findNavController(it)
                    .navigate(R.id.action_alarmFragment_to_alarmSettingFragment)
            }

        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isClicked", clicked)
    }

    override fun onResume() {
        super.onResume()
        visibleBar()
        viewmodel.getAllAlarmData()
        if (clicked) {
            setAnimation(true)
            clicked = false
        }
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