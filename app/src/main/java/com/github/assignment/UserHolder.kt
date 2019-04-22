package com.github.assignment

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.assignment.extensions.bindView

/**
 * @author chenchris on 2019/4/22.
 */
class UserHolder(itemView: View, listener: Listener) : RecyclerView.ViewHolder(itemView) {

    private var userItem: MainAdapter.Item.UserItem? = null

    init {
        itemView.setOnClickListener {
            userItem?.user?.login?.let {
                listener.onItemClick(it)
            }
        }
    }

    private val nameTextView: TextView by bindView(R.id.nameTextView)
    private val avatarImageView: ImageView by bindView(R.id.avatar)
    private val siteAdminTextView: TextView by bindView(R.id.siteAdminTextView)
    private val separatorView: View by bindView(R.id.separator)

    fun bind(userItem: MainAdapter.Item.UserItem?) {
        this.userItem = userItem
        userItem?.user?.let {
            nameTextView.text = it.login
            separatorView.visibility =
                if (userItem.isLast) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            siteAdminTextView.visibility =
                if (it.siteAdmin) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            Glide.with(itemView.context)
                .load(it.avatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(avatarImageView)
        }
    }

    interface Listener {
        fun onItemClick(login: String)
    }
}