package com.github.assignment.presenter

import android.util.Log
import com.github.assignment.MainAdapter
import com.github.assignment.contract.MainPresenter
import com.github.assignment.contract.MainView
import com.github.assignment.network.requests.GithubService
import com.github.assignment.utility.RxUtil
import javax.inject.Inject

/**
 * @author chenchris on 2019/4/22.
 */
class MainPresenterImpl @Inject constructor(
    private val view: MainView,
    private val githubService: GithubService
) : MainPresenter(view, githubService) {

    private val tag = MainPresenterImpl::class.java.simpleName

    override fun subscribe() {
        val disposable = githubService
            .getUsers()
            .compose(RxUtil.applyIoMainSchedulers())
            .doOnSubscribe {
                view.startLoading()
            }
            .doFinally {
                view.dismissLoading()
            }
            .subscribe({
                val item = mutableListOf<MainAdapter.Item>()
                item.add(MainAdapter.Item.TitleItem())
                it.body()?.let { users ->
                    for (i in 0 until users.size) {
                        item.add(MainAdapter.Item.UserItem(users[i], i == users.size - 1))
                    }
                }
                view.onFetchUsers(item)
            }, {
                Log.e(tag, it.toString())
            })
        addDisposable(disposable)
    }
}