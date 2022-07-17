package com.metoer.clocktracker.ui.view.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.metoer.clocktracker.R
import com.metoer.clocktracker.base.BaseFragment
import com.metoer.clocktracker.data.db.ClockDatabase
import com.metoer.clocktracker.data.db.ClockItem
import com.metoer.clocktracker.data.repositories.ClockRepository
import com.metoer.clocktracker.databinding.FragmentAlarmSettingBinding
import com.metoer.clocktracker.day.DayController
import com.metoer.clocktracker.day.DayEnum
import com.metoer.clocktracker.other.DialogCreater
import com.metoer.clocktracker.other.ViewListController
import com.metoer.clocktracker.other.showToastLong
import com.metoer.clocktracker.other.showToastShort
import com.metoer.clocktracker.ui.view.activity.ClockActivity
import com.metoer.clocktracker.ui.view.factories.AlarmSettingsViewModelFactory
import com.metoer.clocktracker.ui.viewmodel.AlarmSettingsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.again_bottom_dialog.*
import kotlinx.android.synthetic.main.again_day_dialog.*
import kotlinx.android.synthetic.main.fragment_alarm_setting.*
import kotlinx.android.synthetic.main.tag_bottom_dialog.*
import java.util.*


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

        //Hatalı yazış
        val database = ClockDatabase(requireContext())
        val repository = ClockRepository(database!!)
        val factory = AlarmSettingsViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(AlarmSettingsViewModel::class.java)
        hideBar()

        binding.apply {
            pickertime.setIs24HourView(true)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    var dayController = DayController()
    private var tagText = ""
    override fun onResume() {
        super.onResume()

        requireActivity().ib_Success.setOnClickListener {
            viewModel!!.updateAdd(ClockItem("09:00",1111111,"Mehter MARŞI", "Ömeri uyutmama"))
            context?.showToastShort("Tıkladım")
        }

        syncTimePicker()
        binding.apply {
            //Zil Sesi Satırı
            linear_1.setOnClickListener {
                context?.showToastLong("Click Zil Sesi")
                getRingtone()
            }
            //Tekrar Satırı
            linear_2.setOnClickListener {
                val againDialog = showDialog(
                    R.style.BottomDialog,
                    R.layout.again_bottom_dialog,
                    R.style.BottomDialog_Animation
                )
                againDialog.apply {
                    rbOnceDay.setOnClickListener {
                        dayController.selectDay(DayEnum.ONEDAY, null)
                    }

                    rbEveryDay.setOnClickListener {
                        dayController.selectDay(DayEnum.EVERYDAY, null)
                        context.showToastShort(Calendar.DATE.toString())
                        cancel()
                    }

                    rbWeekDay.setOnClickListener {
                        dayController.selectDay(DayEnum.WEEKDAY, null)
                        context.showToastShort("Haftaiçi")
                        cancel()
                    }

                    rbSpecialDay.setOnClickListener {
                        cancel()
                        val specialDialog = showDialog(
                            R.style.BottomDialog,
                            R.layout.again_day_dialog,
                            R.style.BottomDialog_Animation
                        )
                        specialDialog.apply {
                            btnAgainCancel.setOnClickListener {
                                cancel()
                            }
                            btnAgainConfirm.setOnClickListener {
                                val getSelection = ViewListController.getSelection(linearLayout)
                                dayController.selectDay(DayEnum.SPECIALDAY, getSelection)
                                cancel()
                            }
                        }
                    }
                }
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
        Navigation.findNavController(requireView())
            .navigate(R.id.action_alarmSettingFragment_to_ringtoneFragment)
//        intent.setType("audio/*")
//        intent.setAction(Intent.ACTION_GET_CONTENT)
//        requireContext().startActivity(intent)
//        val service = AlarmService(requireActivity().applicationContext, "")
//        service.createAlarm(getTimeFromPicker())
    }


    private val calNow: Calendar? = Calendar.getInstance()
    private val calSet = calNow?.clone() as Calendar
    private fun getTimeFromPicker(): Calendar {
        return calSet
    }

    private fun syncTimePicker() {
        pickertime.setOnTimeChangedListener { view, hourOfDay, minute ->
            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calSet.set(Calendar.MINUTE, minute)
            calSet.set(Calendar.SECOND, 0)
            calSet.set(Calendar.MILLISECOND, 0)

            if (calSet <= calNow!!) {
                calSet.add(Calendar.DATE, 1)
            }
        }
    }
}