package com.dwi.mymovies.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dwi.mymovies.network.RemoteDataSource

class MainViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {

    fun searchMovie(query: String) = remoteDataSource.searchMovie(query).asLiveData()
}