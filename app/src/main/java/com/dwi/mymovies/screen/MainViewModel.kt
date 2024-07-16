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
        MutableStateFlow(ApiResponse.Empty("Cari filem kesukaanmu disini..."))
    val uiState: StateFlow<ApiResponse<List<MovieResultsItem>>> = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    private val _isInitial = mutableStateOf(true)
    val isInitial: State<Boolean> get() = _isInitial

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
                            _isInitial.value = false
                            _uiState.value =
                                ApiResponse.Empty("Filem yang kamu cari tidak ditemukan, coba cari filem yang lain...")
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