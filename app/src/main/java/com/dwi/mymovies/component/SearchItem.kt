package com.dwi.mymovies.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dwi.mymovies.model.MovieResultsItem

@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    resultsItem: MovieResultsItem
) {
    OutlinedCard(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Column {
                Text(text = resultsItem.title ?: "No Title", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = resultsItem.overview ?: "No Overview")
            }
        }
    }
}

//@Preview
//@Composable
//fun SearchItemPreview() {
//    SearchItem()
//}