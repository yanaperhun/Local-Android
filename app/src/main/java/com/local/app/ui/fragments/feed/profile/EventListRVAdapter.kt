package com.local.app.ui.fragments.feed.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.local.app.data.event.Event
import com.local.app.databinding.ItemEventPreviewBinding

class EventListRVAdapter : RecyclerView.Adapter<EventListRVAdapter.VH>() {

    private var inflater: LayoutInflater? = null
    private val events = ArrayList<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        inflater?.let { inflater = LayoutInflater.from(parent.context) }
        return VH(ItemEventPreviewBinding.inflate(inflater!!))
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.tvTitle.text = events[position].eventName
    }

    fun addEvents(events: List<Event>) {
        this.events.addAll(events)
        notifyDataSetChanged()
    }

    inner class VH(val binding: ItemEventPreviewBinding) : RecyclerView.ViewHolder(binding.root) {

    }

}