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
    @BindingAdapter(value = ["imageUrl", "placeholder"], requireAll = false)
    @JvmStatic
    fun loadImageInCircle(view: ImageView, imageUrl: String?, placeholder: Drawable? = null) {
        Glide.with(view.context)
            .load(imageUrl)
            .apply(RequestOptions.circleCropTransform())
            .error(placeholder)
            .into(view)
    }

    /**
     * Need further thinking this. Is this good? or we need get avoid to use this?
     */
    @BindingAdapter("android:visibility")
    @JvmStatic
    fun setVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }
}