package com.github.assignment

import android.util.Log
import com.github.assignment.network.requests.FetchUserDetailsRequest
import com.github.assignment.utility.RxUtil

/**
 * @author chenchris on 2019/4/24.
 */
class UserDetailsPresenter(
    private val view: UserDetailsView,
    private val request: FetchUserDetailsRequest,
    private val login: String?
) : BasePresenter() {

    private val tag = UserDetailsPresenter::class.java.simpleName

    override fun subscribe() {
        if (login.isNullOrEmpty()) return

        val disposable = request
            .getUserDetails(login)
            .compose(RxUtil.applyIoMainSchedulers())
            .doOnSubscribe {
                view.startLoading()
            }
            .doFinally {
                view.dismissLoading()
            }
            .subscribe({
                it?.body()?.let { userDetails ->
                    view.onFetchUserDetails(userDetails)
                }
            }, {
                Log.e(tag, it.toString())
            })
        addDisposable(disposable)
    }
}