package com.omniwyse.githubuserdata.viewmodel

import android.arch.lifecycle.ViewModel
import com.omniwyse.githubuserdata.model.User
import com.omniwyse.githubuserdata.network.Api
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


/**
 * Created by Chandra Kant on 11/3/19.
 */
class RxViewModel(val gitHubApi: Api) : ViewModel() {
    var tempList: ArrayList<User> = ArrayList()

    fun getUserList(fromId: Int): Flowable<Response<List<User>>> {
        return gitHubApi.getUser(fromId, 20)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

}