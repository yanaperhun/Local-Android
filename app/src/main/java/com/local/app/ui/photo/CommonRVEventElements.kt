package com.local.app.ui.photo

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.local.app.R
import com.local.app.data.photo.PhotoEntity
import com.local.app.ui.adapters.PhotoViewerAdapter
import com.local.app.ui.adapters.tags.TagsRFAdapter
import com.local.app.ui.custom.LinePagerIndicatorDecoration
import com.local.app.ui.custom.RecyclerItemClickListener
import com.local.app.utils.Utils

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
                                    layoutManager = object : LinearLayoutManager(rvImages.context) {
                                        override fun canScrollVertically(): Boolean {
                                            return false
                                        }
                                    }
                                    adapter = PhotoViewerAdapter(pictures)
                                    rvImages.addItemDecoration(LinePagerIndicatorDecoration())
                                    addedImageClickListener(rvImages, pictures.size)
                                }
                            }, UI_RENDER_DELAY)
            }
        }

        fun buildTagsView(rvTags: RecyclerView, tags: List<String>?) {
            if (tags.isNullOrEmpty()) return
            val adapter = TagsRFAdapter(tags)
            rvTags.layoutManager = LinearLayoutManager(rvTags.context, RecyclerView.HORIZONTAL, false)
            rvTags.adapter = adapter
        }

        fun buildTagsView(chipGroup: ChipGroup, tags: List<String>?) {
            if (tags.isNullOrEmpty()) return
            chipGroup.removeAllViews()
            for (i in tags.indices) {
                val context = chipGroup.context
                val chipView = Chip(context)
                chipView.text = tags[i]
                chipView.setChipStrokeColorResource(R.color.colorGreen)
                chipView.chipStrokeWidth = Utils.dpToPx(2)
//                chipView.setChipMinHeightResource(R.dimen.chip_touch_min_size)
                chipGroup.addView(chipView)

            }
        }

        private fun addedImageClickListener(rv: RecyclerView, imagesCount: Int) {
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