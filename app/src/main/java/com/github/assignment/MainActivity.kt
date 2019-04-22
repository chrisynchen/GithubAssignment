package com.github.assignment

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.github.assignment.network.ApiManager
import com.github.assignment.network.requests.FetchUsersRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView, UserHolder.Listener {
    private val presenter = MainPresenter(this, ApiManager.getInstance().create(FetchUsersRequest::class.java))
    private val adapter = MainAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
