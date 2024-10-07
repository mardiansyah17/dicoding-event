package com.example.dicodingevent.ui.search_event


import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.R
import com.example.dicodingevent.SearchAdapter
import com.example.dicodingevent.databinding.ActivitySearchBinding


class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        viewModel.listEvent.observe(this) {
            val adapter = SearchAdapter(it)
            binding.rvSearchEvent.layoutManager = LinearLayoutManager(this)
            binding.rvSearchEvent.adapter = adapter
        }


    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_appbar, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        val searchEditText =
            searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
        searchEditText.setTextColor(ContextCompat.getColor(this, R.color.white))

        // Handle query text listener
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.findEventByQuery(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text changes here
                return true
            }
        })
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {

            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
