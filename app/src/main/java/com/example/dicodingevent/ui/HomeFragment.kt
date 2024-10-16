package com.example.dicodingevent.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.EventAdapter
import com.example.dicodingevent.data.Result
import com.example.dicodingevent.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: EventViewModel by viewModels {
            factory
        }


        viewModel.getAllEvent(1).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBarUpcomingEvent.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBarUpcomingEvent.visibility = View.GONE
                        val eventData = result.data
                        val eventAdapter =
                            EventAdapter(eventData, object : EventAdapter.OnEventClickListener {
                                override fun onEventClick(eventId: Int) {
                                    val action =
                                        HomeFragmentDirections.actionHomeFragmentToDetailActivity(
                                            eventId
                                        )
                                    view.findNavController().navigate(action)

                                }
                            }, UPCOMING_EVENT_ITEM_WIDTH)
                        binding.rvUpComing.apply {
                            layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            adapter = eventAdapter
                            setHasFixedSize(true)
                        }
                    }

                    is Result.Error -> {
                        binding.progressBarUpcomingEvent.visibility = View.GONE
                        val dialog = AlertDialog.Builder(requireActivity())
                            .setTitle("Error")
                            .setMessage(result.error)
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                        dialog.show()
                    }
                }
            }
        }

        viewModel.getAllEvent(0).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBarFinishedEvent.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBarFinishedEvent.visibility = View.GONE
                        val eventData = result.data
                        val eventAdapter =
                            EventAdapter(eventData, object : EventAdapter.OnEventClickListener {
                                override fun onEventClick(eventId: Int) {
                                    val action =
                                        HomeFragmentDirections.actionHomeFragmentToDetailActivity(
                                            eventId
                                        )
                                    view.findNavController().navigate(action)

                                }
                            })
                        binding.rvFinished.apply {
                            layoutManager = LinearLayoutManager(
                                requireContext(),
                            )
                            adapter = eventAdapter
                            setHasFixedSize(true)
                        }
                    }

                    is Result.Error -> {
                        binding.progressBarFinishedEvent.visibility = View.GONE
                        val dialog = AlertDialog.Builder(requireActivity())
                            .setTitle("Error")
                            .setMessage(result.error)
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                        dialog.show()
                    }
                }
            }
        }

    }

    companion object {
        private const val UPCOMING_EVENT_ITEM_WIDTH = 300
    }

}
