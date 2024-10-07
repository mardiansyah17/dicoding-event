package com.example.dicodingevent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevent.data.response.ListEventsItem

class EventAdapter(private val listEvent: List<ListEventsItem>, private val listener: OnEventClickListener) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    interface OnEventClickListener {
        fun onEventClick(eventId: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.image_item_event)
        val itemText: TextView = view.findViewById(R.id.title_item_event)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemEvent = listEvent[position]

        holder.itemText.text = itemEvent.name
        Glide.with(holder.itemView.context)
            .load(itemEvent.mediaCover)
            .into(holder.itemImage)
        holder.itemView.setOnClickListener {
            listener.onEventClick(itemEvent.id)
        }
//        holder.itemView.setOnClickListener { view ->
//            val action = UpComingFragmentDirections.actionNavigationUpcomingToDetailActivity(itemUpComing.id)
//            view.findNavController().navigate(action)
//        }
    }

    override fun getItemCount(): Int = listEvent.size


}
