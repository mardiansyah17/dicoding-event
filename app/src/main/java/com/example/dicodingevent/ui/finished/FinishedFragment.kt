package com.example.dicodingevent.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.FinishedAdapter
import com.example.dicodingevent.databinding.FragmentFinishedBinding

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = FinishedFragment()
    }

    private val viewModel: FinishedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(context)
        binding.rvFinished.layoutManager = layoutManager



        viewModel.listFinished.observe(viewLifecycleOwner) { finishedEvents ->
            val adapter = FinishedAdapter(finishedEvents)
            binding.rvFinished.adapter = adapter

        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBarFinishedEvent.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage(errorMessage)
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
