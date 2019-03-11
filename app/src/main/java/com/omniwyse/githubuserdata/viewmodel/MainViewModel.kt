package com.omniwyse.githubuserdata.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.omniwyse.githubuserdata.helpers.LiveDataResult
import com.omniwyse.githubuserdata.model.User
import com.omniwyse.githubuserdata.network.Api
import io.reactivex.MaybeObserver
import io.reactivex.disposables.Disposable

class MainViewModel(
    private val userService: Api
) : ViewModel() {

    val repositoriesLiveData = MutableLiveData<LiveDataResult<List<User>>>()

    val loadingLiveData = MutableLiveData<Boolean>()

    /**
     * Request user's repositories
     * @param githubUser Github usename
     */
    fun fetchUserRepositories(page : Int) {
        this.setLoadingVisibility(true)
        this.userService.getUser2(page, 10).subscribe(GetRepositoriesConsumer())
    }

    /**
     * Set a progress dialog visible on the View
     * @param visible visible or not visible
     */
    fun setLoadingVisibility(visible: Boolean) {
        loadingLiveData.postValue(visible)
    }

    /**
     * userService.fetchUserRepositories() Observer
     */
    inner class GetRepositoriesConsumer : MaybeObserver<List<User>> {
        override fun onSubscribe(d: Disposable) {
            this@MainViewModel.repositoriesLiveData.postValue(LiveDataResult.loading())
        }

        override fun onError(e: Throwable) {
            this@MainViewModel.repositoriesLiveData.postValue(LiveDataResult.error(e))
            this@MainViewModel.setLoadingVisibility(false)
        }

        override fun onSuccess(t: List<User>) {
            this@MainViewModel.repositoriesLiveData.postValue(LiveDataResult.succes(t))
            this@MainViewModel.setLoadingVisibility(false)
        }

        override fun onComplete() {
            this@MainViewModel.setLoadingVisibility(false)
        }

    }
}
