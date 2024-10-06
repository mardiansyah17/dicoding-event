package com.example.dicodingevent.ui.search_event

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.SearchAdapter
import com.example.dicodingevent.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel.listEvent.observe(this) {
            val adapter = SearchAdapter(it)
            binding.rvSearchEvent.layoutManager = LinearLayoutManager(this)
            binding.rvSearchEvent.adapter = adapter
        }

        binding.searchEventInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.findEventByQuery(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

    }
}
