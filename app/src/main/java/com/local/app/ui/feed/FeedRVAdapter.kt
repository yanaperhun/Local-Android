package com.local.app.ui.feed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.local.app.data.Event
import com.local.app.databinding.ItemRvEventBinding
import com.local.app.ui.adapters.PhotoViewerAdapter
import com.local.app.ui.custom.LinePagerIndicatorDecoration
import com.local.app.ui.custom.RecyclerItemClickListener

class FeedRVAdapter(private var events: List<Event>) : RecyclerView.Adapter<FeedRVAdapter.VH>() {

    private var viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        return VH(ItemRvEventBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(events[position])

        val rvImages = holder.binding.rvImages
        PagerSnapHelper().attachToRecyclerView(rvImages)

        rvImages.setRecycledViewPool(viewPool)
        rvImages.isNestedScrollingEnabled = false

        rvImages.apply {
            postDelayed(Runnable {
                run {
                    layoutManager = LinearLayoutManager(holder.itemView.context)
                    adapter = PhotoViewerAdapter(events[position].pictures)
                    rvImages.addItemDecoration(LinePagerIndicatorDecoration())
                    addedImageClickListener(rvImages, context)
                }
            }, 50)
        }
    }

    inner class VH(var binding: ItemRvEventBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.event = event
            binding.executePendingBindings()
        }
    }

    private fun addedImageClickListener(rv: RecyclerView, context: Context) {
        val clickListener = RecyclerItemClickListener(context, rv, object :
            RecyclerItemClickListener.OnItemClickListener {

            override fun onItemClick(view: View, position: Int) {
                val pos = if (position == 1) 0 else position + 1
                rv.scrollToPosition(pos)
            }

            override fun onLongItemClick(view: View, position: Int) {
            }
        })
        rv.addOnItemTouchListener(clickListener)
    }
}