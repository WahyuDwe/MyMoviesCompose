package com.dwi.mymovies.network

import com.dwi.mymovies.BuildConfig
import com.dwi.mymovies.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("search/movie")
    suspend fun searchMovie(
        @Header("Authorization") bearerToken: String = BuildConfig.AUTHORIZATION,
        @Header("Accept") accept: String = "application/json",
        @Query("query") query: String
    ): MovieResponse
}
