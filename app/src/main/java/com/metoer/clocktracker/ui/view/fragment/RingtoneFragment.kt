package com.metoer.clocktracker.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.metoer.clocktracker.databinding.FragmentRingtoneBinding
import com.metoer.clocktracker.model.RingtoneModel
import com.metoer.clocktracker.other.adapter.RingtoneAdapter

class RingtoneFragment : Fragment() {

    private lateinit var viewModel: RingtoneViewModel
    private var _binding: FragmentRingtoneBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRingtoneBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(RingtoneViewModel::class.java)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.apply {
            viewModel.apply {
                ringtoneRecylerView.layoutManager = LinearLayoutManager(context)
                loadLocalRingtonesUris(requireActivity())?.apply {
                    observe(viewLifecycleOwner) {
                        val adapter =
                            RingtoneAdapter(it as ArrayList<RingtoneModel>)
                        ringtoneRecylerView.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}