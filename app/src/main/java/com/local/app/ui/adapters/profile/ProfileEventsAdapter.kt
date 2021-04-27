package com.local.app.ui.adapters.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.local.app.data.event.Event
import com.local.app.databinding.ItemProfileEventBinding
import com.local.app.utils.Utils

class ProfileEventsAdapter() : RecyclerView.Adapter<ProfileEventsAdapter.VH>() {
    private var events = ArrayList<Event>()

    inner class VH(val binding: ItemProfileEventBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            ItemProfileEventBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.tvTitle.text = events[position].eventName
        holder.binding.tvPrice.text = events[position].getFormattedPrice()
        events[position].getFirstPhoto()?.url?.md?.let {
            Utils.showRounderCornersImage(holder.binding.ivPhoto, it, Utils
                .dpToPx(12)
                .toInt())
        }

    }

    override fun getItemCount(): Int {
        return events.size
    }
}