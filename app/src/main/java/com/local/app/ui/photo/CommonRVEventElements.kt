package com.local.app.ui.photo

import android.os.Build
import android.view.Gravity.CENTER_VERTICAL
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.local.app.R
import com.local.app.data.Photo
import com.local.app.ui.Utils.Companion.DP_INT
import com.local.app.ui.adapters.PhotoViewerAdapter
import com.local.app.ui.custom.LinePagerIndicatorDecoration
import com.local.app.ui.custom.RecyclerItemClickListener

class CommonRVEventElements {

    companion object {

        private val UI_RENDER_DELAY = 50L

        fun showImages(rvImages: RecyclerView, pictures: List<Photo>) {
            PagerSnapHelper().attachToRecyclerView(rvImages)
            rvImages.isNestedScrollingEnabled = false
            rvImages.apply {
                postDelayed({
                                run {
                                    layoutManager = LinearLayoutManager(rvImages.context)
                                    adapter = PhotoViewerAdapter(pictures)
                                    rvImages.addItemDecoration(LinePagerIndicatorDecoration())
                                    addedImageClickListener(rvImages)
                                }
                            }, UI_RENDER_DELAY)
            }
        }

         fun buildTagsView(llTags: LinearLayout, tagsValue: List<String>) {
            val context = llTags.context

            llTags.removeAllViews()

            for (tagValue in tagsValue) {
                val tvTag = TextView(context)
                tvTag.text = tagValue
                val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                             40 * DP_INT)
                layoutParams.setMargins(0, 0, 6 * DP_INT, 0)
                layoutParams.gravity = CENTER_VERTICAL
                tvTag.setPadding(8 * DP_INT, 0, 8 * DP_INT, 0)

                if (Build.VERSION.SDK_INT < 23) {
                    tvTag.setTextAppearance(context, R.style.App_Text_Tag)
                } else {
                    tvTag.setTextAppearance(R.style.App_Text_Tag)
                }

                tvTag.gravity = CENTER_VERTICAL

                tvTag.setBackgroundResource(R.drawable.rect_grey_solid_10dp_corner)
                llTags.addView(tvTag, layoutParams)
            }
        }

        private fun addedImageClickListener(rv: RecyclerView) {
            val clickListener = RecyclerItemClickListener(rv.context, rv, object :
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
}