package com.omniwyse.githubuserdata.network

import com.omniwyse.githubuserdata.model.User
import io.reactivex.Flowable
import io.reactivex.Maybe
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("/users")
    fun getUser(@Query("since") since: Int, @Query("per_page") perPage: Int): Flowable<Response<List<User>>>

    @GET("/users")
    fun getUser1(@Query("since") since: Int, @Query("per_page") perPage: Int): Call<List<User>>


    fun getUser2(@Query("since") since: Int, @Query("per_page") perPage: Int):  Maybe<List<User>>



}
