package com.local.app.ui.fragments.feed.profile

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.local.app.data.event.Event
import com.local.app.databinding.ItemEventPreviewBinding

class EventListRVAdapter : RecyclerView.Adapter<EventListRVAdapter.VH>() {

    private var inflater: LayoutInflater? = null
    private val events = ArrayList<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        return VH(ItemEventPreviewBinding.inflate(inflater!!, parent, false))
    }

    override fun getItemCount(): Int {
        log("size ${events.size}")
        return events.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
//        holder.binding.tvTitle.text = events[position].eventName
    }

    fun addEvents(events: List<Event>) {
        this.events.addAll(events)

        notifyDataSetChanged()
    }

    fun isAdapterEmpty() : Boolean{
        return events.isEmpty()
    }
    inner class VH(val binding: ItemEventPreviewBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    companion object{
        fun log(mess:String) {
            Log.d("EventListRVAdapter", mess)
        }
    }

}