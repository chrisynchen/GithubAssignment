package com.github.assignment.activity

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.github.assignment.R
import com.github.assignment.UserDetailsView
import com.github.assignment.databinding.ActivityUserDetailsBinding
import com.github.assignment.network.ApiManager
import com.github.assignment.network.requests.GithubService
import com.github.assignment.network.responses.UserDetails
import com.github.assignment.presenter.UserDetailsPresenter

/**
 * @author chenchris on 2019/4/23. Binding adapter example.
 */
class UserDetailsActivity : Activity(), UserDetailsView {

    companion object {
        const val EXTRA_LOGIN = "EXTRA_LOGIN"
    }

    private val login: String? by lazy {
        intent?.getStringExtra(EXTRA_LOGIN)
    }

    private val presenter: UserDetailsPresenter by lazy {
        UserDetailsPresenter(
            this,
            ApiManager.getInstance().create(GithubService::class.java), login
        )
    }

    private lateinit var binding: ActivityUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_details)
        initViews()
    }

    override fun onStart() {
        super.onStart()
        presenter.subscribe()
    }

    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
    }

    private fun initViews() {
        binding.close.setOnClickListener {
            finish()
        }
    }

    override fun startLoading() {
        binding.swipeRefreshLayout.isRefreshing = true
    }

    override fun dismissLoading() {
        binding.swipeRefreshLayout.isRefreshing = false
    }

    override fun onFetchUserDetails(userDetails: UserDetails) {
        binding.userDetails = userDetails
    }
}