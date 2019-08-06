package com.github.assignment.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.assignment.R
import com.github.assignment.UserDetailsView
import com.github.assignment.network.ApiManager
import com.github.assignment.network.requests.FetchUserDetailsRequest
import com.github.assignment.network.responses.UserDetails
import com.github.assignment.presenter.UserDetailsPresenter
import kotlinx.android.synthetic.main.activity_user_details.*

/**
 * @author chenchris on 2019/4/23.
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
            ApiManager.getInstance().create(FetchUserDetailsRequest::class.java), login
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
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
        close.setOnClickListener {
            finish()
        }
    }

    override fun startLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun dismissLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onFetchUserDetails(userDetails: UserDetails) {
        Glide.with(this)
            .load(userDetails.avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(avatar)
        nameTextView.text = userDetails.name
        bioTextView.text = userDetails.bio
        loginTextView.text = userDetails.login
        locationTextView.text = userDetails.location
        blogTextView.text = userDetails.blog
        siteAdminTextView.visibility =
            if (userDetails.siteAdmin) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }
}