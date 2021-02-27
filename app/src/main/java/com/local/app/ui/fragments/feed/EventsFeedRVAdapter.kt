package com.local.app.ui.fragments.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.local.app.data.event.Event
import com.local.app.databinding.LayoutEventBinding
import com.local.app.ui.photo.CommonRVEventElements

abstract class EventsFeedRVAdapter(private var events: List<Event>) :
    RecyclerView.Adapter<EventsFeedRVAdapter.VH>() {

    sealed class Clicks {
        class Like(var eventId: Long) : Clicks()
        class Dislike(var eventId: Long) : Clicks()
        class Event(var eventId: Long) : Clicks()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutEventBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(events[position])
        val rvImages = holder.binding.rvImages

        CommonRVEventElements.buildTagsView(holder.binding.rvTags, events[position].tags)
        CommonRVEventElements.showImages(rvImages, events[position].pictures)

        holder.binding.ivDislike.setOnClickListener {
            onClicks(Clicks.Dislike(events[position].id))
        }
        holder.binding.ivLike.setOnClickListener { onClicks(Clicks.Like(events[position].id)) }
        holder.binding.tvName.setOnClickListener { onClicks(Clicks.Like(events[position].id)) }

    }

    abstract fun onClicks(click: Clicks)

    inner class VH(var binding: LayoutEventBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.event = event
            binding.executePendingBindings()
        }
    }

}