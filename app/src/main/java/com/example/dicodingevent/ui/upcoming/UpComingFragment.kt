package com.example.dicodingevent.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.UpComingAdapter
import com.example.dicodingevent.databinding.FragmentUpComingBinding

class UpComingFragment : Fragment() {

    private var _binding: FragmentUpComingBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = UpComingFragment()
    }

    private val viewModel: UpComingViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpComingBinding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(context)
        binding.rvUpComing.layoutManager = layoutManager

        viewModel.listUpComing.observe(viewLifecycleOwner) {
            val adapter = UpComingAdapter(it)
            binding.rvUpComing.adapter = adapter
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
