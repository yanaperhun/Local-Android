package com.local.app.ui.feed

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
        val snapHelper: SnapHelper = PagerSnapHelper()
        rvImages.onFlingListener = null;
        snapHelper.attachToRecyclerView(rvImages)
        rvImages.setRecycledViewPool(viewPool);
        rvImages.isNestedScrollingEnabled = false

        rvImages.apply {
            postDelayed(Runnable {
                run {

                    layoutManager = LinearLayoutManager(holder.itemView.context)
                    adapter = PhotoViewerAdapter(events[position].pictures)
                    rvImages.addItemDecoration(LinePagerIndicatorDecoration())
                    val clickListener = RecyclerItemClickListener(context, rvImages, object :
                        RecyclerItemClickListener.OnItemClickListener {

                        override fun onItemClick(view: View, position: Int) {
                            val pos = if (position == 1) 0 else position + 1
                            rvImages.scrollToPosition(pos)
                        }

                        override fun onLongItemClick(view: View, position: Int) {
                        }
                    })
                    rvImages.addOnItemTouchListener(clickListener)

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