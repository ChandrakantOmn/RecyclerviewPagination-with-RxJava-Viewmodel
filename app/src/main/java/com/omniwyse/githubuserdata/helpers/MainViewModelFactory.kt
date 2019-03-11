package com.omniwyse.githubuserdata.helpers

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.omniwyse.githubuserdata.network.Api
import com.omniwyse.githubuserdata.viewmodel.RxViewModel
import com.omniwyse.githubuserdata.viewmodel.UserListViewModel

class MainViewModelFactory(
    private val userService: Api
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RxViewModel::class.java!!)) {
            return RxViewModel(this.userService) as T
        }

        if (modelClass.isAssignableFrom(UserListViewModel::class.java!!)) {
            return UserListViewModel(this.userService) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }


}