package com.local.app.ui.photo

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.local.app.data.photo.PhotoEntity
import com.local.app.ui.adapters.PhotoViewerAdapter
import com.local.app.ui.adapters.tags.TagsRFAdapter
import com.local.app.ui.custom.LinePagerIndicatorDecoration
import com.local.app.ui.custom.RecyclerItemClickListener

class CommonRVEventElements {

    companion object {

        private val UI_RENDER_DELAY = 50L

        fun showImages(rvImages: RecyclerView, pictures: List<PhotoEntity>) {
            rvImages.onFlingListener = null
            PagerSnapHelper().attachToRecyclerView(rvImages)
            rvImages.isNestedScrollingEnabled = false
            rvImages.apply {
                postDelayed({
                                run {
                                    layoutManager = LinearLayoutManager(rvImages.context)
                                    adapter = PhotoViewerAdapter(pictures)
                                    rvImages.addItemDecoration(LinePagerIndicatorDecoration())
                                    addedImageClickListener(rvImages, pictures.size)
                                }
                            }, UI_RENDER_DELAY)
            }
        }

        fun buildTagsView(rvTags: RecyclerView,
                          tags: List<String>?,
                          isGridMode: Boolean = false) {
            if (tags.isNullOrEmpty()) return
            val adapter = TagsRFAdapter(tags)
            rvTags.layoutManager = if (isGridMode) GridLayoutManager(rvTags.context, 3) else
                LinearLayoutManager(rvTags.context, RecyclerView.HORIZONTAL, false)
            rvTags.adapter = adapter
        }

        private fun addedImageClickListener(rv: RecyclerView, imagesCount  : Int) {
            val clickListener = RecyclerItemClickListener(rv.context, rv, object :
                RecyclerItemClickListener.OnItemClickListener {

                override fun onItemClick(view: View, position: Int) {
                    val pos = if (position == imagesCount - 1) 0 else position + 1
                    rv.scrollToPosition(pos)
                }

                override fun onLongItemClick(view: View, position: Int) {
                }
            })
            rv.addOnItemTouchListener(clickListener)
        }
    }
}