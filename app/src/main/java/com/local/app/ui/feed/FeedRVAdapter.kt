package com.local.app.ui.feed

import android.content.Context
import android.os.Build
import android.view.Gravity.CENTER_VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.local.app.R
import com.local.app.data.Event
import com.local.app.databinding.ItemRvEventBinding
import com.local.app.ui.Utils
import com.local.app.ui.adapters.PhotoViewerAdapter
import com.local.app.ui.custom.LinePagerIndicatorDecoration
import com.local.app.ui.custom.RecyclerItemClickListener

abstract class FeedRVAdapter(private var events: List<Event>) : RecyclerView.Adapter<FeedRVAdapter.VH>() {

    sealed class Clicks {
        class Like(var eventId: Long) : Clicks()
        class Dislike(var eventId: Long) : Clicks()
    }

    private val UI_RENDER_DELAY = 50L

    private var viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        return VH(ItemRvEventBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(events[position])
        val context = holder.itemView.context
        val rvImages = holder.binding.rvImages
        PagerSnapHelper().attachToRecyclerView(rvImages)

        rvImages.setRecycledViewPool(viewPool)
        rvImages.isNestedScrollingEnabled = false

        buildTagsView(holder.binding.llTags, events[position].tagsDefault)


        rvImages.apply {
            postDelayed({
                            run {
                                layoutManager = LinearLayoutManager(context)
                                adapter = PhotoViewerAdapter(events[position].pictures)
                                rvImages.addItemDecoration(LinePagerIndicatorDecoration())
                                addedImageClickListener(rvImages, context)
                            }
                        }, UI_RENDER_DELAY)
        }

        holder.binding.ivDislike.setOnClickListener {
            onClicks(Clicks.Dislike(events[position].id))
        }
        holder.binding.ivLike.setOnClickListener { onClicks(Clicks.Like(events[position].id)) }

    }

    abstract fun onClicks(click: Clicks)

    inner class VH(var binding: ItemRvEventBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.event = event
            binding.executePendingBindings()
        }
    }

    private fun buildTagsView(llTags: LinearLayout, tagsValue: List<String>) {
        val context = llTags.context

        llTags.removeAllViews()

        for (tagValue in tagsValue) {
            val tvTag = TextView(context)
            tvTag.text = tagValue
            val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                         Utils.dpToPx(40, context.resources))
            layoutParams.setMargins(0, 0, Utils.dpToPx(6, context.resources), 0)
            layoutParams.gravity = CENTER_VERTICAL
            tvTag.setPadding(Utils.dpToPx(8, context.resources), 0,
                             Utils.dpToPx(8, context.resources), 0)

            if (Build.VERSION.SDK_INT < 23) {
                tvTag.setTextAppearance(context, R.style.App_Text_Tag)
            } else {
                tvTag.setTextAppearance(R.style.App_Text_Tag)
            }

            tvTag.gravity = CENTER_VERTICAL

            tvTag.setBackgroundResource(R.drawable.rect_grey_10dp_corner)
            llTags.addView(tvTag, layoutParams)
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