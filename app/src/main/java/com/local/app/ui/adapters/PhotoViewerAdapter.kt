package com.local.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.bumptech.glide.Glide
import com.local.app.R
import com.local.app.data.Photo
import com.local.app.databinding.ItemPhotoViewerBinding

class PhotoViewerAdapter(private val photoList: List<Photo>) :
    RecyclerView.Adapter<PhotoViewerAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var view =
            ItemPhotoViewerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(parent.width, view)

    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        //        holder.itemView.layoutParams = ConstraintLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        holder.itemView.layoutParams.width = holder.w

        Glide
            .with(holder.itemView.context)
//                        .load(photoList[position].url.lg)
            .load("https://lh3.googleusercontent.com/a-/AOh14Gj9wtNNo_N9RFP2TO6vq5zk0Lyg01ctxoK8AejPZw=s96-c")
            .into(holder.binding.ivPhoto)
    }

    inner class VH(var w: Int, itemView: ItemPhotoViewerBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var binding = itemView
    }

}
