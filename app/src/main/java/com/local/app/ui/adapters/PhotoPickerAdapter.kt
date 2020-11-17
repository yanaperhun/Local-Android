package com.local.app.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.local.app.GlideApp
import com.local.app.databinding.ItemAddPhotoBinding
import com.local.app.databinding.ItemPhotoBinding
import com.local.app.utils.Utils

abstract class PhotoPickerAdapter : RecyclerView.Adapter<PhotoPickerAdapter.VH>() {

    private val TYPE_ADD_BUTTON = 1
    private val TYPE_PHOTO = 0
    var photos = ArrayList<Uri>()

    abstract fun onAddBtnClick()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding: ViewBinding
//        viewBinding = ItemAddPhotoBinding.inflate(layoutInflater, parent, false)
        //        Utils.setSizeForItem(viewBinding.root, parent)
        //        return VHAddPhoto(viewBinding)
        return if (viewType == TYPE_ADD_BUTTON) {
            viewBinding = ItemAddPhotoBinding.inflate(layoutInflater, parent, false)
            Utils.setSizeForItem(viewBinding.root, parent)
            VHAddPhoto(viewBinding)
        } else {
            viewBinding = ItemPhotoBinding.inflate(layoutInflater, parent, false)
            Utils.setSizeForItem(viewBinding.root, parent)
            VHPhoto(viewBinding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == photos.size) return TYPE_ADD_BUTTON
        return TYPE_PHOTO
    }

    override fun getItemCount(): Int {
        return photos.size + 1
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        when (holder) {
            is VHAddPhoto -> {
                holder.itemView.setOnClickListener { onAddBtnClick() }
            }
            is VHPhoto -> {

                showRounderCornersImage(holder.itemPhotoBinding.ivPhoto, photos[position], Utils.dpToPx(8).toInt())
            }
        }
    }

    fun showRounderCornersImage(imageView: ImageView, imageUrl: Uri, radius: Int) {
        GlideApp
            .with(imageView.context)
            .load(imageUrl)
            .transform(CenterCrop(), RoundedCorners(radius))
            .into(imageView)

    }

    class VHPhoto(val itemPhotoBinding: ItemPhotoBinding) : VH(itemPhotoBinding.root)

    class VHAddPhoto(private val itemAddPhotoBinding: ItemAddPhotoBinding) :
        VH(itemAddPhotoBinding.root)

    open class VH(view: View) : RecyclerView.ViewHolder(view) {

    }
}