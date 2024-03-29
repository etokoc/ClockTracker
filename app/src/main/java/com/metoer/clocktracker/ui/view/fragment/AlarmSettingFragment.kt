package com.metoer.clocktracker.ui.view.fragment

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.metoer.clocktracker.R
import com.metoer.clocktracker.base.BaseFragment
import com.metoer.clocktracker.data.db.ClockDatabase
import com.metoer.clocktracker.data.db.ClockItem
import com.metoer.clocktracker.data.repositories.ClockRepository
import com.metoer.clocktracker.databinding.FragmentAlarmSettingBinding
import com.metoer.clocktracker.day.DayController
import com.metoer.clocktracker.day.DayStatusEnum
import com.metoer.clocktracker.other.DialogCreater
import com.metoer.clocktracker.other.ViewListController
import com.metoer.clocktracker.other.alarm.AlarmService
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

    private var selectedRingtoneUri: Uri? = null
    private var selectedTag: String? = ""
    private var selectedTime: String? = "09:00"
    private var selectedDate: String? = "0000000"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlarmSettingBinding.inflate(inflater, container, false)
        if (savedInstanceState != null) {
            Log.i("INSTANCE", savedInstanceState?.get("tag").toString())
            binding.tvTagDescription.text = savedInstanceState["tag"].toString()
        }
        //Hatalı yazış
        val database = ClockDatabase(requireContext())
        val repository = ClockRepository(database)
        val factory = AlarmSettingsViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(AlarmSettingsViewModel::class.java)
        hideBar()

        binding.apply {
            pickertime.setIs24HourView(true)
        }


        binding.tvTagDescription.text = viewModel!!._tagLiveData.value ?: ""


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBundle("tag", bundleOf(Pair("tag", selectedTag.toString())))
        Log.i("INSTANCE", "instance girdi")
    }*/


    var dayController = DayController()
    override fun onResume() {
        super.onResume()

        requireActivity().ib_Success.setOnClickListener {
            viewModel!!.updateAdd(
                ClockItem(
                    selectedTime ?: "09:00",
                    selectedDate ?: "0000000",
                    "Mehter MARŞI",
                    selectedTag ?: "Alarm",
                    true
                )
            )
            Navigation.findNavController(requireView())
                .navigate(R.id.action_alarmSettingFragment_to_alarmFragment)
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
                        val inDay = Calendar.getInstance().time.day - 1
                        val days =
                            arrayListOf(false, false, false, false, false, false, false)
                        days[inDay] = true
                        selectedDate = dayController.selectDay(DayStatusEnum.ONEDAY, days)
                        binding.tvAgainDayDescription.text = "Birkez"
                        cancel()
                    }

                    rbEveryDay.setOnClickListener {
                        selectedDate = dayController.selectDay(DayStatusEnum.EVERYDAY, null)
                        context.showToastShort(Calendar.DATE.toString())
                        binding.tvAgainDayDescription.text = "Her gün"
                        cancel()
                    }

                    rbWeekDay.setOnClickListener {
                        selectedDate = dayController.selectDay(DayStatusEnum.WEEKDAY, null)
                        binding.tvAgainDayDescription.text = "Hafta içi"
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
                                selectedDate =
                                    dayController.selectDay(DayStatusEnum.SPECIALDAY, getSelection)
                                binding.tvAgainDayDescription.text = "Özel"
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
                        selectedTag = tag_edittext.text.toString()
                        viewModel!!._tagLiveData.value = selectedTag
                        binding.tvTagDescription.text = selectedTag
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
        val action = AlarmSettingFragmentDirections.actionAlarmSettingFragmentToRingtoneFragment("")
        action.tagArgs = viewModel!!._tagLiveData.value.toString()
        Navigation.findNavController(requireView())
            .navigate(R.id.action_alarmSettingFragment_to_ringtoneFragment)
        val service = AlarmService(requireActivity().applicationContext, "")
        service.createAlarm(getTimeFromPicker())
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

            var selectHour = hourOfDay.toString()
            var selectMinute = minute.toString()
            if (hourOfDay < 10)
                selectHour = "0$hourOfDay"
            if (minute < 10)
                selectMinute = "0$minute"

            selectedTime = "$selectHour:$selectMinute"
        }
    }
}