package com.github.assignment

import android.databinding.DataBindingUtil
import android.support.annotation.IntDef
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.assignment.databinding.ViewHolderUserBinding
import com.github.assignment.network.responses.User
import com.github.assignment.viewholder.TitleHolder
import com.github.assignment.viewholder.UserHolder


/**
 * @author chenchris on 2019/4/22.
 */
class MainAdapter(private val listener: UserHolder.Listener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = arrayListOf<Item>()

    fun setItems(items: List<Item>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) ?: return

        when (item.type) {
            Item.TYPE_TITLE -> {
                (holder as TitleHolder).bind(item as Item.TitleItem)
            }
            Item.TYPE_USER -> {
                (holder as UserHolder).bind(item as Item.UserItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Item.TYPE_TITLE -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_title, parent, false)
                TitleHolder(itemView)
            }
            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val userBinding =
                    DataBindingUtil.inflate<ViewHolderUserBinding>(
                        layoutInflater,
                        R.layout.view_holder_user,
                        parent,
                        false
                    )
                UserHolder(userBinding, listener)
            }
        }
    }

    private fun getItem(position: Int): Item? {
        return if (position in 0 until itemCount) {
            items[position]
        } else null
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.type ?: Item.TYPE_USER
    }

    sealed class Item(@UserListType val type: Int) {

        companion object {
            const val TYPE_TITLE = 0
            const val TYPE_USER = 1

            @IntDef(TYPE_TITLE, TYPE_USER)
            @Retention(AnnotationRetention.SOURCE)
            annotation class UserListType
        }

        data class TitleItem(val titleText: String) : Item(TYPE_TITLE)

        data class UserItem(val user: User, val isLast: Boolean) : Item(TYPE_USER)
    }
}