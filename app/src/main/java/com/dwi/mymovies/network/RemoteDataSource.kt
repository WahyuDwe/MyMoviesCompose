package com.dwi.mymovies.network

import android.util.Log
import com.dwi.mymovies.model.MovieResponse
import com.dwi.mymovies.model.MovieResultsItem
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow

class RemoteDataSource(private val apiService: ApiService) {

    fun searchMovie(query: String): Flow<ApiResponse<List<MovieResultsItem>>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = apiService.searchMovie(query = query)
            Log.d("RemoteDataSource", "searchMovie: ${response.results}")
            if (response.results.isEmpty())
                emit(ApiResponse.Empty("No Data"))

            emit(ApiResponse.Success(response.results))
        } catch (e: Exception) {
            Log.e("RemoteDataSource", "searchMovie: ${e.localizedMessage}")
            emit(ApiResponse.Error(e.localizedMessage ?: "Unknown Error"))
        }
    }
}