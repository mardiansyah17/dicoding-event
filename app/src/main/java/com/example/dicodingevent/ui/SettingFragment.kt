package com.example.dicodingevent.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dicodingevent.R
import com.example.dicodingevent.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: EventViewModel by viewModels {
            factory
        }

        val trackStates = resources.getColorStateList(R.color.switch_track_color, null)
        val thumbStates = resources.getColorStateList(R.color.switch_thumb_color, null)
        binding.switchTheme.apply {
            thumbTintList = thumbStates
            trackTintList = trackStates
        }

        viewModel.getTheme().observe(viewLifecycleOwner) { theme ->
            if (theme) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveTheme(isChecked)
        }
    }


}