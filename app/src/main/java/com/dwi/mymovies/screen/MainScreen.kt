package com.dwi.mymovies.screen

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
    val query by viewModel.query
    val uiState by remember {
        viewModel.uiState
    }.collectAsState()

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
                    onSubmitted = viewModel::searchMovie,
                    onClear = {}
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (uiState) {
                    ApiResponse.Loading -> {
                        Text(text = "Loading...")
                    }

                    is ApiResponse.Empty -> {
                        Text(text = (uiState as ApiResponse.Empty).message)
                    }

                    is ApiResponse.Error -> {
                        val message = (uiState as ApiResponse.Error).error
                        Text(text = "Error message -> $message")
                        Log.d("MainScreen", "Error message -> $message")
                    }

                    is ApiResponse.Success -> {
                        val data = (uiState as ApiResponse.Success).data
                        Log.d("MainScreen", "Success data -> $data")
                    }
                }
            }
        }
    }
}