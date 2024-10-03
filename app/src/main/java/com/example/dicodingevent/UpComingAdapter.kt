package com.example.dicodingevent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevent.data.response.ListEventsItem

class UpComingAdapter(private val listUpComingEvent: List<ListEventsItem>) :
    RecyclerView.Adapter<UpComingAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.image_item_event)
        val itemText: TextView = view.findViewById(R.id.title_item_event)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemUpComing = listUpComingEvent[position]
        holder.itemText.text = itemUpComing.name
        Glide.with(holder.itemView.context)
            .load(itemUpComing.mediaCover)
            .into(holder.itemImage)
    }

    override fun getItemCount(): Int = listUpComingEvent.size


}
