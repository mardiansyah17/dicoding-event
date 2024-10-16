// SearchEventFragment.kt
package com.example.dicodingevent.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.EventAdapter
import com.example.dicodingevent.data.Result
import com.example.dicodingevent.databinding.FragmentSearchEventBinding

class SearchEventFragment : Fragment() {

    private var _binding: FragmentSearchEventBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EventViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchEventBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchEvents("")
    }

    fun searchEvents(query: String) {
        if (query.isEmpty()) {
            return
        }
        viewModel.getAllEvent(-1, query).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE

                        val eventData = result.data
                        Log.d("SearchEventFragment", "Success to get data: $eventData")
                        if (eventData.isEmpty()) {
                            binding.llEmptyEvent.visibility = View.VISIBLE
                            binding.rvSearchEvent.visibility = View.GONE
                            return@observe
                        }
                        binding.llEmptyEvent.visibility = View.GONE
                        binding.rvSearchEvent.visibility = View.VISIBLE

                        val eventAdapter =
                            EventAdapter(eventData, object : EventAdapter.OnEventClickListener {
                                override fun onEventClick(eventId: Int) {
                                    val action =
                                        SearchEventFragmentDirections.actionNavigationSearchEventToDetailActivity(
                                            eventId
                                        )
                                    findNavController().navigate(action)
                                }
                            })
                        binding.rvSearchEvent.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = eventAdapter
                        }
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        AlertDialog.Builder(requireContext())
                            .setTitle("Error")
                            .setMessage(result.error)
                            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
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
}