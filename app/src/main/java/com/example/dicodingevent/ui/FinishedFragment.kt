package com.example.dicodingevent.ui

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
import com.example.dicodingevent.databinding.FragmentFinishedBinding

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: EventViewModel by viewModels {
            factory
        }

        val layoutManager = LinearLayoutManager(requireActivity())

        viewModel.getAllEvent(STATUS_EVENT).observe(viewLifecycleOwner) { result ->
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
                                    // Do nothing
                                    val action =
                                        FinishedFragmentDirections.actionNavigationFinishedToDetailActivity(
                                            eventId
                                        )
                                    view.findNavController().navigate(action)
                                }
                            })
                        binding.rvFinishedEvent.layoutManager = layoutManager
                        binding.rvFinishedEvent.adapter = eventAdapter
                    }

                    is Result.Error -> {
                        android.app.AlertDialog.Builder(requireActivity())
                            .setTitle("Error")
                            .setMessage(result.error)
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val STATUS_EVENT = 0
    }
}
