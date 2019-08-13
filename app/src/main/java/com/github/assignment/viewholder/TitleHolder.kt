package com.github.assignment.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.assignment.MainAdapter
import com.github.assignment.R
import com.github.assignment.extensions.bindView

/**
 * @author chenchris on 2019/4/22.
 */
class TitleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val titleTextView: TextView by bindView(R.id.title)

    fun bind(titleItem: MainAdapter.Item.TitleItem) {
        titleTextView.text = titleItem.titleText
    }
}