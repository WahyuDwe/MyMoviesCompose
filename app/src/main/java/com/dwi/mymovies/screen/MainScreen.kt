package com.dwi.mymovies.screen

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dwi.mymovies.R
import com.dwi.mymovies.component.DefaultLottieAnimation
import com.dwi.mymovies.component.SearchBar
import com.dwi.mymovies.component.SearchItem
import com.dwi.mymovies.network.ApiResponse
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel()
) {
    val focusManager = LocalFocusManager.current
    val query by viewModel.query
    val isInitial by viewModel.isInitial
    val uiState by remember {
        viewModel.uiState
    }.collectAsState()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                }
            },
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
                    ) {
                        SearchBar(
                            onSubmitted = viewModel::searchMovie,
                            onClear = {}
                        )
                    }
                },

                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    ),
            )
        },
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) {
        Column(
            modifier = modifier
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Log.d("MainScreen", "Empty -> $isInitial")
                when (uiState) {
                    ApiResponse.Loading -> {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        }
                    }

                    is ApiResponse.Empty -> {
                        val message = (uiState as ApiResponse.Empty).message
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            if (isInitial) {
                                DefaultLottieAnimation(
                                    rawRes = R.raw.search_animation,
                                )

                            } else {
                                DefaultLottieAnimation(
                                    rawRes = R.raw.empty_list_lottie,
                                    modifier = Modifier.size(300.dp)
                                )
                            }
                            Box(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = message,
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 18.sp,
                                        fontFamily = FontFamily.Monospace
                                    ),
                                )
                            }
                        }

                    }

                    is ApiResponse.Error -> {
                        val message = (uiState as ApiResponse.Error).error
                        Text(text = "Error message -> $message")
                        Log.d("MainScreen", "Error message -> $message")
                    }

                    is ApiResponse.Success -> {
                        val data = (uiState as ApiResponse.Success).data
                        Log.d("MainScreen", "Success data -> $data")
                        LazyColumn {
                            items(data) { item ->
                                SearchItem(resultsItem = item)
                            }
                        }
                    }
                }
            }
        }
    }
}

