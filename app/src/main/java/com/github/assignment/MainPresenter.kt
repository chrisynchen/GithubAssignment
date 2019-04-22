package com.github.assignment

import android.util.Log
import com.github.assignment.network.requests.FetchUsersRequest
import com.github.assignment.utility.RxUtil

/**
 * @author chenchris on 2019/4/22.
 */
class MainPresenter(
    private val view: MainView,
    private val request: FetchUsersRequest
) : BasePresenter() {

    private val tag = MainPresenter::class.java.simpleName

    override fun subscribe() {
        val disposable = request
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
                item.add(MainAdapter.Item.TitleItem(view.getUserTitle()))
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