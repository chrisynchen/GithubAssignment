package com.github.assignment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.assignment.MainAdapter
import com.github.assignment.repository.IUserRepository
import com.github.assignment.utility.RxUtil
import javax.inject.Inject


class MainViewModel @Inject constructor(private val userRepository: IUserRepository) : BaseViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _items = MutableLiveData<List<MainAdapter.Item>>()
    val items: LiveData<List<MainAdapter.Item>>
        get() = _items

    private val _progressVisibility = MutableLiveData<Boolean>()
    val progressVisibility: LiveData<Boolean>
        get() = _progressVisibility

    fun onRefresh() {
        val disposable = userRepository.loadUser()
            .compose(RxUtil.applyIoMainSchedulers())
            .doOnSubscribe {
                _progressVisibility.postValue(true)
            }
            .doFinally {
                _progressVisibility.postValue(false)
            }
            .subscribe({
                val item = mutableListOf<MainAdapter.Item>()
                item.add(MainAdapter.Item.TitleItem())
                it.body()?.let { users ->
                    for (i in 0 until users.size) {
                        item.add(MainAdapter.Item.UserItem(users[i], i == users.size - 1))
                    }
                }
                _items.postValue(item)
            }, {
                Log.e(TAG, it.toString())
            })
        addDisposable(disposable)
    }
}