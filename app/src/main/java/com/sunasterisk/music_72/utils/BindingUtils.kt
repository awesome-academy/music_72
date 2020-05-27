package com.sunasterisk.music_72.utils

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.sunasterisk.music_72.R

object BindingUtils {
    @JvmStatic
    @BindingAdapter("imageResource")
    fun image(view: ImageView, res: Int) {
        Glide.with(view)
            .load(res)
            .apply(RequestOptions.errorOf(R.drawable.bg_all_music))
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("imageCircleUrl")
    fun image(view: ImageView, url: String) {
        Glide.with(view)
            .asBitmap()
            .load(url)
            .apply(RequestOptions.errorOf(R.drawable.logo_app))
            .into(object : BitmapImageViewTarget(view) {
                override fun setResource(resource: Bitmap?) {
                    val circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(view.context.resources, resource)
                    circularBitmapDrawable.isCircular = true
                    view.setImageDrawable(circularBitmapDrawable)
                }
            })
    }
}
