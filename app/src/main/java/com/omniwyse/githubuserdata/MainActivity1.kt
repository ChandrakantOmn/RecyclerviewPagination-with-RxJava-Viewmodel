package com.omniwyse.githubuserdata

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.omniwyse.githubuserdata.network.Api
import com.omniwyse.githubuserdata.view.adapter.UserListAdapter
import com.omniwyse.githubuserdata.viewmodel.UserListViewModel
import com.omniwyse.githubuserdata.helpers.MainViewModelFactory
import com.omniwyse.githubuserdata.helpers.UserPagination
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity1 : AppCompatActivity() {
    lateinit var viewModel: UserListViewModel
    var currentPage=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val factory =
            MainViewModelFactory((application as UserApplication).retrofit.create(Api::class.java))
        viewModel = ViewModelProviders.of(this, factory).get(UserListViewModel::class.java)
        loadData()
    }

    private fun loadData() {
        recyclerView!!.layoutManager = LinearLayoutManager(applicationContext)
        val mAdapter = UserListAdapter(this, 0)
        recyclerView!!.adapter = mAdapter
        viewModel.user!!.observe(this, Observer {
            if (it!= null){
             mAdapter.addItems(it)
            }
        })

        recyclerView!!.addOnScrollListener(object : UserPagination(recyclerView.layoutManager!!) {
            override fun onLoadMore(i: Int, totalItemCount: Int, view: View) {
                viewModel.loadMore(i*20)
            }
        })

    }


}