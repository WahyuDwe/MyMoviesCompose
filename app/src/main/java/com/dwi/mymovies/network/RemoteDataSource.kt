package com.dwi.mymovies.network

import com.dwi.mymovies.model.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun searchMovie(query: String): Flow<MovieResponse> =
        flowOf(apiService.searchMovie(query = query))

}