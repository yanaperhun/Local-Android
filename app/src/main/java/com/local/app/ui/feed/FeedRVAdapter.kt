package com.local.app.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.local.app.data.Event
import com.local.app.databinding.ItemRvEventBinding
import com.local.app.ui.adapters.PhotoViewerAdapter
import com.local.app.ui.custom.LinePagerIndicatorDecoration

class FeedRVAdapter(private var events: List<Event>) : RecyclerView.Adapter<FeedRVAdapter.VH>() {

    var viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        return VH(ItemRvEventBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        var i: Int
        holder.bind(events[position])
        val rvImages = holder.binding.rvImages
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rvImages)
        rvImages.setRecycledViewPool(viewPool);
        rvImages.isNestedScrollingEnabled = false

        rvImages.apply {
                        postDelayed(Runnable {
                            run {

            layoutManager = LinearLayoutManager(holder.itemView.context)
            adapter = PhotoViewerAdapter(events[position].pictures)
            rvImages.addItemDecoration(LinePagerIndicatorDecoration())
                            }

                        }, 50)
        }


        rvImages.invalidate()
    }

    inner class VH(var binding: ItemRvEventBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.event = event
            binding.executePendingBindings();
        }

    }
}