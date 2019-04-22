package com.github.assignment

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author chenchris on 2019/4/22.
 */
abstract class BasePresenter {
    private val compositeDisposable = CompositeDisposable()

    abstract fun subscribe()

    fun addDisposable(vararg disposables: Disposable) {
        disposables.forEach {
            compositeDisposable.addAll(it)
        }
    }

    fun unsubscribe() {
        compositeDisposable.clear()
    }
}