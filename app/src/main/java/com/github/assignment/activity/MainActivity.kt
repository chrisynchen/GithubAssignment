package com.github.assignment.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.github.assignment.MainAdapter
import com.github.assignment.R
import com.github.assignment.contract.MainPresenter
import com.github.assignment.contract.MainView
import com.github.assignment.dagger.DaggerMainPresenterComponent
import com.github.assignment.network.ApiManager
import com.github.assignment.network.requests.FetchUsersRequest
import com.github.assignment.viewholder.UserHolder
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView, UserHolder.Listener {

    private val adapter = MainAdapter(this)

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val component = DaggerMainPresenterComponent.builder()
            .view(this)
            .userRequest(ApiManager.getInstance().create(FetchUsersRequest::class.java))
            .build()
        component.inject(this)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
        swipeRefreshLayout.setOnRefreshListener {
            presenter.subscribe()
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.subscribe()
    }

    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
    }

    override fun startLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun dismissLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onFetchUsers(users: List<MainAdapter.Item>) {
        adapter.setItems(users)
    }

    override fun getUserTitle(): String {
        return getString(R.string.users)
    }

    override fun onItemClick(login: String) {
        startActivity(Intent(this, UserDetailsActivity::class.java)
            .apply {
                putExtra(UserDetailsActivity.EXTRA_LOGIN, login)
            })
    }
}
