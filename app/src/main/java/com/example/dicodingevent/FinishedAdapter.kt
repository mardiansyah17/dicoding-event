package com.example.dicodingevent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevent.data.response.ListEventsItem

class FinishedAdapter(private val listFinishedEvent: List<ListEventsItem>) :
    RecyclerView.Adapter<FinishedAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.image_item_event_finished)
        val itemText: TextView = view.findViewById(R.id.title_item_event_finished)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_event_finished, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemFinished = listFinishedEvent[position]
        holder.itemText.text = itemFinished.name
        Glide.with(holder.itemView.context)
            .load(itemFinished.mediaCover)
            .into(holder.itemImage)
    }

    override fun getItemCount(): Int {
        return listFinishedEvent.size
    }
}
