package com.dwi.mymovies.network

sealed class ApiResponse<out T : Any?> {
//    data object Initial : ApiResponse<Nothing>()
    data object Loading : ApiResponse<Nothing>()
    data class Success<out T : Any>(val data: T) : ApiResponse<T>()
    class Empty(val message: String) : ApiResponse<Nothing>()
    class Error(val error: String) : ApiResponse<Nothing>()
}