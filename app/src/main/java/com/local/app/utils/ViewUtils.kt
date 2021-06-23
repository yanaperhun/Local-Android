package com.local.app.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.local.app.R

class ViewUtils {
    companion object {


        fun getMainBGColorDrawable(context: Context): ColorDrawable {
            return ColorDrawable(context.resources.getColor(R.color.colorMainDark))
        }

        fun showImage(imageView: ImageView, imageUrl: String, @DrawableRes placeholder: Int = 0) {
            Glide
                .with(imageView.context)
                .load(imageUrl)
                .placeholder(placeholder)
                .into(imageView)
        }

        fun showRoundImage(
            imageView: ImageView,
            imageUrl: String,
            @DrawableRes placeholder: Int = 0
        ) {
            Glide
                .with(imageView.context)
                .load(imageUrl)
                .placeholder(placeholder)
                .centerCrop()
                .circleCrop()
                .into(imageView)
        }

        fun showRounderCornersImage(
            imageView: ImageView,
            imageUrl: String,
            radius: Int,
            @DrawableRes placeholder: Int = 0
        ) {
            Glide
                .with(imageView.context)
                .load(imageUrl)
                .placeholder(placeholder)
                .transform(CenterCrop(), RoundedCorners(radius))
                .into(imageView)

        }

        fun pxToDp(px: Float): Float {
            return px / Resources.getSystem().displayMetrics.density
        }

        fun dpToPx(dp: Int): Float {
            return dp * Resources.getSystem().displayMetrics.density
        }

        fun setSizeForItem(view: View, parent: ViewGroup) {
            val params = view.layoutParams
            val gridSpace =
                dpToPx(parent.resources.getDimension(R.dimen.control_small_margin).toInt()).toInt()

            //            Timber.d("Call setSizeForItem : ${parent.width}")

            params.width = (parent.width - gridSpace * 2) / 3
            params.height = (params.width * 1.4).toInt()
            Log.d("Utils", "h : ${params.height}, w : ${params.width}, ph: ${parent.width}")
//            view.findViewById<ImageView>(R.id.iv_product).layoutParams.height =
//                (params.height * 0.45).toInt()
            view.layoutParams = params
        }
    }

}