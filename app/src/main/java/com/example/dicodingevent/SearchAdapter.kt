package com.example.dicodingevent

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevent.data.response.ListEventsItem
import com.example.dicodingevent.ui.search_event.SearchEventFragmentDirections

class SearchAdapter(private val listEvent: List<ListEventsItem>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.image_item_event_search)
        val itemText: TextView = view.findViewById(R.id.title_item_event_search)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_search_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listEvent = listEvent[position]
        Log.d("SearchAdapter", "onBindViewHolder: ${listEvent.name}")
        holder.itemText.text = listEvent.name
        Glide.with(holder.itemView.context)
            .load(listEvent.mediaCover)
            .into(holder.itemImage)

        holder.itemView.setOnClickListener { view ->
            val action = SearchEventFragmentDirections.actionNavigationSearchEventToDetailActivity(listEvent.id)
            view.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return listEvent.size
    }
}
