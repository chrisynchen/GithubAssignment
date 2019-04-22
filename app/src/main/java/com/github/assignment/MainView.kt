package com.github.assignment

/**
 * @author chenchris on 2019/4/22.
 */
interface MainView : BaseView {
    fun onFetchUsers(users: List<MainAdapter.Item>)
    fun getUserTitle(): String
}