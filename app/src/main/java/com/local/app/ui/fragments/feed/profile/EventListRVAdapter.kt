package com.local.app.ui.fragments.feed.profile

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.local.app.R
import com.local.app.data.event.Event
import com.local.app.databinding.ItemEventProfileBinding
import com.local.app.utils.DateUtils
import com.local.app.utils.Utils

abstract class EventListRVAdapter : RecyclerView.Adapter<EventListRVAdapter.VH>() {

    private var inflater: LayoutInflater? = null
    private val events = ArrayList<Event>()

    abstract fun onEventClick(event: Event)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        return VH(ItemEventProfileBinding.inflate(inflater!!, parent, false))
    }

    override fun getItemCount(): Int {
        log("size ${events.size}")
        return events.size
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        val event = events[position]
        holder.binding.tvTitle.text = event.eventName
        holder.binding.tvDate.text = DateUtils.changeFormat(
            event.eventDate,
            DateUtils.FORMAT_UTC_TZ,
            DateUtils.FORMAT_dd_MMMM
        )
        holder.binding.tvPrice.text = event.getFormattedPrice()
        Utils.showRounderCornersImage(
            holder.binding.ivPhoto, event.getFirstPhoto()?.url?.lg ?: "", Utils
                .dpToPx(8)
                .toInt(),
            R.drawable.ic_test
        )

        holder.itemView.setOnClickListener { onEventClick(events[holder.adapterPosition]) }
    }

    fun addEvents(events: List<Event>) {
        this.events.addAll(events)

        notifyDataSetChanged()
    }

    fun isAdapterEmpty(): Boolean {
        return events.isEmpty()
    }

    inner class VH(val binding: ItemEventProfileBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    companion object {
        fun log(mess: String) {
            Log.d("EventListRVAdapter", mess)
        }
    }

}