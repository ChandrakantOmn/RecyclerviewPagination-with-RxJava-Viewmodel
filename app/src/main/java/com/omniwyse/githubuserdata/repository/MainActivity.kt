package com.omniwyse.githubuserdata.repository

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.omniwyse.githubuserdata.R
import com.omniwyse.githubuserdata.UserApplication
import com.omniwyse.githubuserdata.helpers.MainViewModelFactory
import com.omniwyse.githubuserdata.helpers.UserPagination
import com.omniwyse.githubuserdata.network.Api
import com.omniwyse.githubuserdata.view.adapter.UserListAdapter
import com.omniwyse.githubuserdata.viewmodel.UserListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: UserListViewModel

    companion object {
        const val LIST = 0
        const val GRID = 1
        const val PREFS_FILENAME = "paginationDemoPref"
        const val LIST_TYPE = "listType"
        const val PAGE_SIZE = 20
        var SINCE = -1 * PAGE_SIZE
    }

    private var prefs: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

         val factory =  MainViewModelFactory((application as UserApplication).retrofit.create(Api::class.java))

        viewModel = ViewModelProviders.of(this, factory).get(UserListViewModel::class.java)
        prefs = this.getSharedPreferences(PREFS_FILENAME, 0)

        toggleList(prefs!!.getInt(LIST_TYPE, 0))
    }


    private fun toggleList(type: Int) {
        val editor = prefs!!.edit()
        editor.putInt(LIST_TYPE, type)
        editor.apply()
        if (type == LIST) recyclerView.layoutManager = LinearLayoutManager(this) else
            recyclerView.layoutManager = GridLayoutManager(this, 2)
        val dividerItemDecoration = DividerItemDecoration(recyclerView!!.context, DividerItemDecoration.VERTICAL)
        recyclerView!!.addItemDecoration(dividerItemDecoration)
        loadData(recyclerView.layoutManager!!, type)
    }

    private fun loadData(layoutManager: RecyclerView.LayoutManager, type: Int) {
        recyclerView!!.layoutManager = layoutManager
        val mAdapter = UserListAdapter(this, type)
        recyclerView!!.adapter = mAdapter
        viewModel.user!!.observe(this, Observer {
            if (it != null) {
                mAdapter.addItems(it)
            }
        })

        recyclerView!!.addOnScrollListener(object : UserPagination(recyclerView.layoutManager!!) {
            override fun onLoadMore(i: Int, totalItemCount: Int, view: View) {
                viewModel.loadMore(i * 20)
            }
        })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_manu, menu)
        updateMenu(menu!!.findItem(R.id.action_list))
        return true
    }

    private fun updateMenu(item: MenuItem) =
        if (prefs!!.getInt(LIST_TYPE, 0) == 0) item.icon =
            ContextCompat.getDrawable(this, R.drawable.grid_icon)
        else item.icon = ContextCompat.getDrawable(this, R.drawable.list_icon)

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_list -> {
                if (prefs!!.getInt(LIST_TYPE, 0) != 0) {
                    item.icon = ContextCompat.getDrawable(this, R.drawable.grid_icon)
                    toggleList(LIST)
                } else {
                    item.icon = ContextCompat.getDrawable(this, R.drawable.list_icon)
                    toggleList(GRID)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


}