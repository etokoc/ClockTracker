package com.metoer.clocktracker.ui.view.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.metoer.clocktracker.R
import com.metoer.clocktracker.base.BaseFragment
import com.metoer.clocktracker.databinding.FragmentAlarmSettingBinding
import com.metoer.clocktracker.other.showToastShort
import com.metoer.clocktracker.ui.view.activity.ClockActivity
import kotlinx.android.synthetic.main.fragment_alarm_setting.*


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
        binding.apply {
            //Zil Sesi Satırı
            linear_1.setOnClickListener {
                requireContext().showToastShort("Satır-1")
            }
            //Tekrar Satırı
            linear_2.setOnClickListener {
                showDialog()
            }
            //Etiket Satırı
            linear_3.setOnClickListener {
                requireContext().showToastShort("Satır-3")
            }
        }
    }


    override fun hideBar() {
        (requireActivity() as ClockActivity).hideBar()
    }

    fun showDialog() {
        val bottomDialog = Dialog(requireContext(), R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(requireContext()).inflate(R.layout.again_bottom_dialog, null)
        bottomDialog.setContentView(contentView)
        val layoutParams = contentView.layoutParams
        layoutParams.width = resources.displayMetrics.widthPixels
        contentView.layoutParams = layoutParams
        bottomDialog.window!!.setGravity(Gravity.BOTTOM)
        bottomDialog.setCanceledOnTouchOutside(true)
        bottomDialog.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        bottomDialog.show()
    }

}