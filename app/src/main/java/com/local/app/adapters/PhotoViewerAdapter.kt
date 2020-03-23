package com.local.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.local.app.data.Photo
import com.local.app.databinding.ItemPhotoViewerBinding
import kotlinx.android.synthetic.main.item_photo_viewer.view.*

class PhotoViewerAdapter(private val photoList: List<Photo>) :
    RecyclerView.Adapter<PhotoViewerAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemPhotoViewerBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        holder.binding.iv_photo
    }

    inner class VH(itemView: ItemPhotoViewerBinding) : RecyclerView.ViewHolder(itemView.root) {
        var binding = itemView.root
    }
}
