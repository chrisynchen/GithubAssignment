package com.github.assignment.extensions

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @author chenchris on 2019/4/22.
 */
fun <T : View> RecyclerView.ViewHolder.bindView(@IdRes resId: Int): Lazy<T> {
    return lazy {
        itemView.findViewById<T>(resId)
    }
}