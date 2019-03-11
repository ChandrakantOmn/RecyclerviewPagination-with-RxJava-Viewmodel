package com.omniwyse.githubuserdata.repository


import android.arch.lifecycle.MutableLiveData
import com.omniwyse.githubuserdata.model.User
import com.omniwyse.githubuserdata.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class UserRepository(private val apiInterface :Api) {
    fun getUsers(page: Int): MutableLiveData<ArrayList<User>>? {
        val userListForReturn = MutableLiveData<ArrayList<User>>()
        val call = apiInterface.getUser1(page, 10)
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.body() != null) {
                    userListForReturn.value = response.body() as ArrayList<User>?
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {

            }
        })

        return userListForReturn
    }

}
