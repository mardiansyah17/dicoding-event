package com.example.dicodingevent.ui.upcoming

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.EventAdapter
import com.example.dicodingevent.data.Result
import com.example.dicodingevent.databinding.FragmentUpComingBinding
import com.example.dicodingevent.ui.EventViewModel
import com.example.dicodingevent.ui.ViewModelFactory

class UpComingFragment : Fragment() {

    private var _binding: FragmentUpComingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUpComingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: EventViewModel by viewModels {
            factory
        }
        val layoutManager = LinearLayoutManager(context)

        viewModel.getAllEvent().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBarUpComingEvent.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBarUpComingEvent.visibility = View.GONE
                        val eventData = result.data
                        Log.d("UpComingFragment", "eventData: $eventData")
                        val eventAdapter = EventAdapter(eventData, object : EventAdapter.OnEventClickListener {
                            override fun onEventClick(eventId: Int) {
                                // Do something
                            }
                        })
                        binding.rvUpComing.layoutManager = layoutManager
                        binding.rvUpComing.adapter = eventAdapter

                    }

                    is Result.Error -> TODO()
                }
            }
        }


    }


}
