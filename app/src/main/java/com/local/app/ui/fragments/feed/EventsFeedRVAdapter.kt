package com.local.app.ui.fragments.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.local.app.data.event.Event
import com.local.app.databinding.LayoutEventBinding
import com.local.app.ui.photo.CommonRVEventElements

abstract class EventsFeedRVAdapter : RecyclerView.Adapter<EventsFeedRVAdapter.VH>() {

    var events = ArrayList<Event>()

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

    override fun onBindViewHolder(vh: VH, position: Int) {
        vh.bind(events[position])
        val rvImages = vh.binding.rvImages

        CommonRVEventElements.buildTagsView(vh.binding.rvTags, events[position].tagsDefault)
        CommonRVEventElements.showImages(rvImages, events[position].pictures)
        vh.binding.rvTags.setRecycledViewPool(RecyclerView.RecycledViewPool())
        vh.binding.tvPrice.text = events[position].getFormattedPrice()
        val adapterPos = vh.adapterPosition
        vh.binding.ivDislike.setOnClickListener { onClicks(Clicks.Dislike(events[adapterPos].id)) }
        vh.binding.ivLike.setOnClickListener { onClicks(Clicks.Like(events[adapterPos].id)) }
        vh.binding.tvName.setOnClickListener { onClicks(Clicks.Event(events[adapterPos].id)) }
        vh.binding.viewBlur.setOnClickListener { onClicks(Clicks.Event(events[adapterPos].id)) }

    }

    abstract fun onClicks(click: Clicks)

    fun setEvents(list: List<Event>) {
        events.clear()
        events.addAll(list)
        notifyDataSetChanged()
    }

    inner class VH(var binding: LayoutEventBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.event = event
            binding.executePendingBindings()
        }
    }

}