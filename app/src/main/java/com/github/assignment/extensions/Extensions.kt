package com.github.assignment.extensions

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

/**
 * @author chenchris on 2019/4/22.
 */
fun <T : View> RecyclerView.ViewHolder.bindView(@IdRes resId: Int): Lazy<T> {
    return lazy {
        itemView.findViewById<T>(resId)
    }
}