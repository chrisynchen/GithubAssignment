package com.github.assignment.contract

import com.github.assignment.BaseView
import com.github.assignment.MainAdapter
import com.github.assignment.network.requests.GithubService
import com.github.assignment.presenter.BasePresenter

/**
 * Created by chris chen on 2019-08-06.
 */

interface MainView : BaseView {
    fun onFetchUsers(users: List<MainAdapter.Item>)
    fun getUserTitle(): String
}

abstract class MainPresenter(view: MainView, githubService: GithubService) : BasePresenter()