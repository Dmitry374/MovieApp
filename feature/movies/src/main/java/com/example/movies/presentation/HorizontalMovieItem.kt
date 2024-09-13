package com.example.movies.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.common.utils.convertDateToFormattedString
import com.example.movies.R
import com.example.ui.R as UiResR

@Composable
fun HorizontalMovieItem(
    title: String,
    imageUrl: String,
    seasonNumber: Int?,
    sourceName: String,
    releaseDate: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Card(
                modifier = Modifier
                    .height(150.dp)
                    .align(CenterVertically)
                    .fillMaxWidth(0.3f),
                shape = RoundedCornerShape(0.dp)
            ) {
                Column(
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        placeholder = painterResource(R.drawable.ic_placeholder_movie),
                        modifier = Modifier.fillMaxHeight()
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    maxLines = 2,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily(
                        Font(
                            UiResR.font.googlesans_regular,
                            FontWeight.Normal
                        )
                    ),
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (seasonNumber != null) {
                    Text(
                        text = stringResource(R.string.season_number, seasonNumber),
                        fontFamily = FontFamily(
                            Font(
                                UiResR.font.googlesans_regular,
                                FontWeight.Normal
                            )
                        ),
                        fontSize = 14.sp
                    )
                }

                Text(
                    text = convertDateToFormattedString(releaseDate),
                    fontFamily = FontFamily(
                        Font(
                            UiResR.font.googlesans_regular,
                            FontWeight.Normal
                        )
                    ),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = sourceName,
                    fontFamily = FontFamily(
                        Font(
                            UiResR.font.googlesans_regular,
                            FontWeight.Normal
                        )
                    ),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun HorizontalMovieItemFilmPreview() {
    HorizontalMovieItem(
        title = "Fast & Furious X",
        imageUrl = "https://image.tmdb.org/t/p/w500/1E5baAaEse26fej7uHcjOgEE2t2.jpg",
        seasonNumber = null,
        sourceName = "Netflix",
        releaseDate = "2024-11-19",
        onClick = {}
    )
}

@Preview
@Composable
fun HorizontalMovieItemTvSeriesPreview() {
    HorizontalMovieItem(
        title = "Lost",
        imageUrl = "https://image.tmdb.org/t/p/w500/1E5baAaEse26fej7uHcjOgEE2t2.jpg",
        seasonNumber = 2,
        sourceName = "Netflix",
        releaseDate = "2005-05-11",
        onClick = {}
    )
}
