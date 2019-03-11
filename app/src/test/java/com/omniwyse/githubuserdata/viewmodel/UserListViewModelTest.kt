package com.omniwyse.githubuserdata.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.omniwyse.githubuserdata.helpers.LiveDataResult
import com.omniwyse.githubuserdata.model.User
import com.omniwyse.githubuserdata.network.Api
import io.reactivex.Maybe
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.net.SocketException

/**
 * Created by Chandra Kant on 11/3/19.
 */
@RunWith(JUnit4::class)
class UserListViewModelTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var userService: Api

    lateinit var mainViewModel: MainViewModel


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.mainViewModel = MainViewModel(this.userService)
    }

    @Test
    fun fetchRepositories_positiveResponse() {
        Mockito.`when`(this.userService.getUser2(0,10)).thenAnswer {
            return@thenAnswer Maybe.error<SocketException>(SocketException("No network here"))
        }

        val observer = Mockito.mock(Observer::class.java) as Observer<LiveDataResult<List<User>>>
        this.mainViewModel.repositoriesLiveData.observeForever(observer)

        this.mainViewModel.fetchUserRepositories(0)

        assertNotNull(this.mainViewModel.repositoriesLiveData.value)
        assertEquals(LiveDataResult.Status.ERROR, this.mainViewModel.repositoriesLiveData.value?.status)
        assert(this.mainViewModel.repositoriesLiveData.value?.err is Throwable)
    }


    @Test
    fun setLoadingVisibility_onSuccess() {
        Mockito.`when`(this.userService.getUser2(20,20)).thenAnswer {
            return@thenAnswer Maybe.just(listOf<User>())
        }

        val spiedViewModel = com.nhaarman.mockitokotlin2.spy(this.mainViewModel)

        spiedViewModel.fetchUserRepositories(0)
        Mockito.verify(spiedViewModel, Mockito.times(2)).setLoadingVisibility(ArgumentMatchers.anyBoolean())
    }

    @Test
    fun setLoadingVisibility_onError() {
        Mockito.`when`(this.userService.getUser2(-1,20)).thenAnswer {
            return@thenAnswer Maybe.error<SocketException>(SocketException())
        }

        val spiedViewModel = com.nhaarman.mockitokotlin2.spy(this.mainViewModel)

        spiedViewModel.fetchUserRepositories(-1)
        Mockito.verify(spiedViewModel, Mockito.times(2)).setLoadingVisibility(ArgumentMatchers.anyBoolean())
    }

    @Test
    fun setLoadingVisibility_onNoData() {
        Mockito.`when`(this.userService.getUser2(20000,20)).thenReturn(Maybe.create {
            it.onComplete()
        })

        val spiedViewModel = com.nhaarman.mockitokotlin2.spy(this.mainViewModel)

        spiedViewModel.fetchUserRepositories(20000)
        Mockito.verify(spiedViewModel, Mockito.times(2)).setLoadingVisibility(ArgumentMatchers.anyBoolean())
    }


}