package com.example.dicodingevent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.EventAdapter
import com.example.dicodingevent.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.rvHomeUpcomingEvent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvHomeFinishedEvent.layoutManager = LinearLayoutManager(context)

        viewModel.upComingEvent.observe(viewLifecycleOwner) {
            val adapter = EventAdapter(it, object : EventAdapter.OnEventClickListener {
                override fun onEventClick(eventId: Int) {
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailActivity(eventId)
                    findNavController().navigate(action)
                }

            }, 300)

            binding.rvHomeUpcomingEvent.adapter = adapter
        }

        viewModel.finishedEvent.observe(viewLifecycleOwner) {
            val adapter = EventAdapter(it, object : EventAdapter.OnEventClickListener {
                override fun onEventClick(eventId: Int) {
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailActivity(eventId)
                    findNavController().navigate(action)
                }
            })
            binding.rvHomeFinishedEvent.isNestedScrollingEnabled = false
            binding.rvHomeFinishedEvent.adapter = adapter
        }

        return binding.root
    }
}
