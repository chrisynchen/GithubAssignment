package com.github.assignment.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.github.assignment.BR
import com.github.assignment.MainAdapter
import com.github.assignment.databinding.ViewHolderUserBinding


/**
 * @author chenchris on 2019/4/22.
 */
class UserHolder(private val viewDataBinding: ViewHolderUserBinding, listener: Listener) :
    RecyclerView.ViewHolder(viewDataBinding.root) {

    private var userItem: MainAdapter.Item.UserItem? = null

    init {
        viewDataBinding.root.setOnClickListener {
            userItem?.user?.login?.let {
                listener.onItemClick(it)
            }
        }
    }

    fun bind(userItem: MainAdapter.Item.UserItem?) {
        this.userItem = userItem
        viewDataBinding.apply {
            userItem?.user?.let {
                setVariable(BR.userItem, userItem)
                setVariable(BR.isLast, userItem.isLast)
                executePendingBindings()
            }
        }
    }

    interface Listener {
        fun onItemClick(login: String)
    }
}