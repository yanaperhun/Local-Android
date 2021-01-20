package com.local.app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.local.app.data.photo.PhotoEntity
import com.local.app.databinding.ItemPhotoViewerBinding

class PhotoViewerAdapter(private val photoEntityList: List<PhotoEntity>) :
    RecyclerView.Adapter<PhotoViewerAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var view =
            ItemPhotoViewerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(parent.width, view)

    }

    override fun getItemCount(): Int {
        return photoEntityList.size
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
