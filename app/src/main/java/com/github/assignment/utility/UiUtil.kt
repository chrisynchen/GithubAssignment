package com.github.assignment.utility

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by chris chen on 2019-08-08.
 */

object UiUtil {
    @BindingAdapter("imageUrl", "placeholder")
    fun loadImageInCircle(view: ImageView, imageUrl: String?, placeholder: Drawable?) {
        Glide.with(view.context)
            .load(imageUrl)
            .apply(RequestOptions.circleCropTransform())
            .error(placeholder)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }
}