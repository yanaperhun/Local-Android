package com.local.app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.local.app.data.photo.PhotoEntity
import com.local.app.databinding.ItemPhotoViewerBinding
import com.local.app.utils.Utils

class PhotoViewerAdapter(private val photoEntityList: List<PhotoEntity>) :
    RecyclerView.Adapter<PhotoViewerAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view =
            ItemPhotoViewerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(parent.width, view)

    }

    override fun getItemCount(): Int {
        return photoEntityList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        //        holder.itemView.layoutParams = ConstraintLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        holder.itemView.layoutParams.width = holder.w
        Utils.showImage(holder.binding.ivPhoto,
                        photoEntityList[position].url.lg)
    }

    inner class VH(var w: Int, itemView: ItemPhotoViewerBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var binding = itemView
    }

}
