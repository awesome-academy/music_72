package com.sunasterisk.music_72.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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
}
