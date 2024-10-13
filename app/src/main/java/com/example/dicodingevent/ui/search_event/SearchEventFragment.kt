package com.example.dicodingevent.ui.search_event

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.EventAdapter
import com.example.dicodingevent.R
import com.example.dicodingevent.databinding.FragmentSearchEventBinding

class SearchEventFragment : Fragment() {

    private var _binding: FragmentSearchEventBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchEventBinding.inflate(inflater, container, false)
        binding.rvSearchEvent.layoutManager = LinearLayoutManager(context)


       
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
