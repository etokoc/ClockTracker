package com.metoer.clocktracker.ui.view.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.metoer.clocktracker.R
import com.metoer.clocktracker.base.BaseFragment
import com.metoer.clocktracker.databinding.FragmentAlarmSettingBinding
import com.metoer.clocktracker.other.DialogCreater
import com.metoer.clocktracker.other.alarm.AlarmService
import com.metoer.clocktracker.ui.view.activity.ClockActivity
import com.metoer.clocktracker.ui.viewmodel.AlarmSettingsViewModel
import kotlinx.android.synthetic.main.fragment_alarm_setting.*
import kotlinx.android.synthetic.main.tag_bottom_dialog.*


class AlarmSettingFragment : BaseFragment() {

    private var viewModel: AlarmSettingsViewModel? = null
    private var _binding: FragmentAlarmSettingBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlarmSettingBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(AlarmSettingsViewModel::class.java)
        hideBar()

        binding.apply {
            pickertime.setIs24HourView(true)
        }

        return binding.root
    }

    var tagText = ""
    override fun onResume() {
        super.onResume()
        binding.apply {
            //Zil Sesi Satırı
            linear_1.setOnClickListener {
                getRingtone()
            }
            //Tekrar Satırı
            linear_2.setOnClickListener {
                showDialog(
                    R.style.BottomDialog,
                    R.layout.again_bottom_dialog,
                    R.style.BottomDialog_Animation
                )
            }
            //Etiket Satırı
            linear_3.setOnClickListener {
                val addTagDialog = showDialog(
                    R.style.BottomDialog,
                    R.layout.tag_bottom_dialog,
                    R.style.BottomDialog_Animation
                )
                addTagDialog.apply {
                    confirmButton.setOnClickListener {
                        tagText = tag_edittext.text.toString()
                        binding.tvTagDescription.text = tagText
                        cancel()
                    }
                    cancelButton.setOnClickListener { cancel() }
                }
            }
        }
    }

    override fun hideBar() {
        (requireActivity() as ClockActivity).hideBar()
    }

    fun showDialog(style: Int, item: Int, animation: Int): Dialog {
        val dialog = DialogCreater(
            requireContext(),
            style,
            item,
            animation
        ).showDialog()
        return dialog
    }

    private fun getRingtone() {
//        val intent = Intent()
//        intent.setType("audio/*")
//        intent.setAction(Intent.ACTION_GET_CONTENT)
//        requireContext().startActivity(intent)
        val service = AlarmService(requireActivity().applicationContext)
        service.createAlarm()
    }


}