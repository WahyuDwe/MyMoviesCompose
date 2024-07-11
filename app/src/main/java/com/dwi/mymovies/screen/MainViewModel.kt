package com.dwi.mymovies.screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwi.mymovies.model.MovieResultsItem
import com.dwi.mymovies.network.ApiResponse
import com.dwi.mymovies.network.RemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {

    private val _uiState: MutableStateFlow<ApiResponse<List<MovieResultsItem>>> =
        MutableStateFlow(ApiResponse.Empty("Start Search"))
    val uiState: StateFlow<ApiResponse<List<MovieResultsItem>>> = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query


    fun searchMovie(query: String) {
        _query.value = query
        viewModelScope.launch {
            _uiState.value = ApiResponse.Loading
            try {
                remoteDataSource.searchMovie(_query.value)
                    .catch {
                        _uiState.value = ApiResponse.Error(it.message.toString())
                    }
                    .collect {
                        if (it.results.isEmpty()) {
                            _uiState.value =
                                ApiResponse.Empty("No Result Found for ${_query.value}")
                        } else {
                            _uiState.value = ApiResponse.Success(it.results)
                        }
                    }
            } catch (e: Exception) {
                _uiState.value = ApiResponse.Error(e.message.toString())
            }
        }
    }
}