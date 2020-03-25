package com.local.app.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.local.app.data.Event
import com.local.app.databinding.ItemRvEventBinding
import com.local.app.ui.event.EventFragment

class FeedRVAdapter(var events: List<Event>) : RecyclerView.Adapter<FeedRVAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): VH {
        return VH(ItemRvEventBinding.inflate(LayoutInflater.from(parent.context)));
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: VH,
                                  position: Int) {
        //        holder.binding.frEvent as EventFragment
    }

    inner class VH(var binding: ItemRvEventBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}