package com.dwi.mymovies.network

sealed class ApiResponse<out R> {
    data object Loading : ApiResponse<Nothing>()
    class Success<T>(val data: T) : ApiResponse<T>()
    class Empty(val message: String) : ApiResponse<Nothing>()
    class Error(val error: String) : ApiResponse<Nothing>()
}