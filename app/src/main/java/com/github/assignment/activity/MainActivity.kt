package com.github.assignment.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.assignment.MainAdapter
import com.github.assignment.R
import com.github.assignment.dagger.DaggerMainComponent
import com.github.assignment.databinding.ActivityMainBinding
import com.github.assignment.viewholder.UserHolder
import com.github.assignment.viewmodel.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity(), UserHolder.Listener {

    private val adapter = MainAdapter(this)

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val component = DaggerMainComponent.builder()
            .build()
        component.inject(this)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            //            presenter.subscribe()
            viewModel.fetchUsers()
        }

        viewModel.progressVisibility.observe(this, Observer {
            binding.swipeRefreshLayout.isRefreshing = it
        })

        viewModel.items.observe(this, Observer {
            adapter.setItems(it)
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchUsers()
    }

    override fun onItemClick(login: String) {
        startActivity(Intent(this, UserDetailsActivity::class.java)
            .apply {
                putExtra(UserDetailsActivity.EXTRA_LOGIN, login)
            })
    }
}
