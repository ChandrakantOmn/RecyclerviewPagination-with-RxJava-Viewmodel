package com.omniwyse.githubuserdata.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.omniwyse.githubuserdata.model.User
import com.omniwyse.githubuserdata.network.Api
import com.omniwyse.githubuserdata.repository.UserRepository


class UserListViewModel(api: Api) : ViewModel() {
    var user: MutableLiveData<ArrayList<User>>? = null
    var repo: UserRepository = UserRepository(api)

    init {
        user = repo.getUsers(0)

    }

    fun loadMore(page: Int) {
        repo.getUsers(page)!!.observeForever {
            if (it != null)
                user!!.value = it
        }
    }

}
