package com.github.assignment.presenter

import android.util.Log
import com.github.assignment.MainAdapter
import com.github.assignment.contract.MainPresenter
import com.github.assignment.contract.MainView
import com.github.assignment.network.requests.FetchUsersRequest
import com.github.assignment.utility.RxUtil
import javax.inject.Inject

/**
 * @author chenchris on 2019/4/22.
 */
class MainPresenterImpl @Inject constructor(
    private val view: MainView,
    private val request: FetchUsersRequest
) : MainPresenter(view, request) {

    private val tag = MainPresenterImpl::class.java.simpleName

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