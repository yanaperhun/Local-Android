package com.local.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.local.app.R
import com.local.app.data.Photo
import com.local.app.databinding.ItemPhotoViewerBinding

class PhotoViewerAdapter(private val photoList: List<Photo>) :
    RecyclerView.Adapter<PhotoViewerAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_photo_viewer, parent, false)
        //            ItemPhotoViewerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //        view.root.layoutParams = ConstraintLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        return VH(parent.width, view)

    }

    override fun getItemCount(): Int {
        return 4
        //        return photoList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        //        holder.itemView.layoutParams = ConstraintLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                holder.itemView.layoutParams.width = holder.w
        //        Glide
        //            .with(holder.itemView.context)
        //            //            .load(photoList[position].url.lg)
        //            .load("https://sun9-20.userapi.com/c854228/v854228168/21919d/Xj0453LPIHE.jpg")
        //            .into(holder.binding.iv_photo)
    }

    inner class VH(var w  : Int, itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = itemView
    }
    //
    //    inner class VH(itemView: ItemPhotoViewerBinding) : RecyclerView.ViewHolder(itemView.root) {
    //        var binding = itemView.root
    //    }
}
