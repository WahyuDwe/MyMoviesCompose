package com.dwi.mymovies.screen

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import com.dwi.mymovies.component.SearchBar
import com.dwi.mymovies.network.ApiResponse
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel()
) {
    val focusManager = LocalFocusManager.current
    val lifecycleOwner = LocalLifecycleOwner.current

    Scaffold {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = modifier.padding(horizontal = 16.dp)
            ) {
                SearchBar(
                    onSubmitted = { query ->
                        viewModel.searchMovie(query).observe(lifecycleOwner){ search ->
                            when(search) {
                                ApiResponse.Loading -> {
                                    Log.d("MainScreen", "Loading")
                                }
                                is ApiResponse.Empty -> {
                                    Log.d("MainScreen", "Empty")

                                }
                                is ApiResponse.Error -> {
                                    Log.d("MainScreen", "Error: ${search.error}")
                                }
                                is ApiResponse.Success -> {
                                    Log.d("MainScreen", "Success: ${search.data}")
                                }
                            }
                        }
                    },
                    onClear = {}
                )
            }
        }
    }
}