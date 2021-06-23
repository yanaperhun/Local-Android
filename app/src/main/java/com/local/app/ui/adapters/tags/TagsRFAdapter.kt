package com.local.app.ui.adapters.tags

import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.local.app.R
import com.local.app.databinding.ItemTagBinding
import com.local.app.utils.ViewUtils

class TagsRFAdapter(val items: List<String>) : RecyclerView.Adapter<TagsRFAdapter.VH>() {

    inner class VH(val binding: ItemTagBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.tvTag.text = items[position]
    }

    private fun createTagView(context: Context): TextView {
        val tvTag = TextView(context)

        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewUtils
            .dpToPx(40)
            .toInt())
        layoutParams.setMargins(0, 0, ViewUtils
            .dpToPx(6)
            .toInt(), 0)
        layoutParams.gravity = Gravity.CENTER_VERTICAL
        tvTag.setPadding(ViewUtils
                             .dpToPx(8)
                             .toInt(), 0, ViewUtils
                             .dpToPx(8)
                             .toInt(), 0)

        if (Build.VERSION.SDK_INT < 23) {
            tvTag.setTextAppearance(context, R.style.App_Text_Tag)
        } else {
            tvTag.setTextAppearance(R.style.App_Text_Tag)
        }

        tvTag.gravity = Gravity.CENTER_VERTICAL

        tvTag.setBackgroundResource(R.drawable.rect_grey_solid_10dp_corner)
        return tvTag
    }
}