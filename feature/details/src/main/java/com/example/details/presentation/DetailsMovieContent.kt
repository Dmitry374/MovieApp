package com.example.details.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.details.R
import com.example.semantics.colorId
import com.example.semantics.drawableId
import com.example.semantics.imageUrlProperty
import com.example.ui.R as UiResR

@Composable
fun DetailsMovieContent(
    title: String,
    description: String,
    posterUrl: String,
    backdropUrl: String?,
    genres: List<String>,
    releaseDate: String,
    rating: Double?,
    runtime: Int?,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavouriteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.clickable {
                    onBackClick()
                },
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = null
            )

            Text(
                text = stringResource(R.string.details),
                fontSize = 20.sp,
                fontWeight = FontWeight(600),
                fontFamily = FontFamily(Font(UiResR.font.googlesans_regular, FontWeight.Normal)),
                modifier = Modifier
                    .padding(start = 24.dp)
                    .testTag("details_toolbar_title")
            )

            Icon(
                modifier = Modifier
                    .clickable {
                        onFavouriteClick()
                    }
                    .testTag("details_add_to_favorite_button")
                    .semantics {
                        drawableId =
                            if (isFavorite) R.drawable.ic_love else R.drawable.ic_love_border
                        colorId = R.color.favorite
                    },
                painter = painterResource(id = if (isFavorite) R.drawable.ic_love else R.drawable.ic_love_border),
                contentDescription = null,
                tint = colorResource(R.color.favorite),
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomEnd = 20.dp,
                    bottomStart = 20.dp
                )
            ) {
                Box {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(210.dp)
                            .testTag("details_backdrop_url")
                            .semantics {
                                imageUrlProperty = backdropUrl ?: ""
                            },
                        model = backdropUrl,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                    )

                    if (rating != null) {
                        Card(
                            modifier = Modifier
                                .offset(x = 310.dp, y = 178.dp)
                                .background(
                                    color = colorResource(R.color.rating_background),
                                    shape = RoundedCornerShape(size = 8.dp)
                                )
                                .padding(start = 8.dp, end = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_favorite),
                                    contentDescription = null,
                                    tint = colorResource(R.color.rating_text_color),
                                    modifier = Modifier
                                        .width(16.dp)
                                        .height(16.dp)
                                        .testTag("details_rating_icon")
                                        .semantics {
                                            drawableId = R.drawable.ic_favorite
                                            colorId = R.color.rating_text_color
                                        }
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = rating.toString(),
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(UiResR.font.googlesans_regular)),
                                    fontWeight = FontWeight(600),
                                    color = colorResource(R.color.rating_text_color),
                                    modifier = Modifier
                                        .testTag("details_rating_text")
                                )
                            }
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .offset(x = 29.dp, y = 150.dp)
                    .width(95.dp)
                    .height(120.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Gray
                ),
                shape = RoundedCornerShape(16.dp),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                        .testTag("details_poster_url")
                        .semantics {
                            imageUrlProperty = posterUrl
                        },
                    model = posterUrl,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                )
            }

            // Title
            Text(
                modifier = Modifier
                    .width(210.dp)
                    .height(48.dp)
                    .offset(x = 140.dp, y = 220.dp)
                    .testTag("details_movie_title"),
                text = title,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(UiResR.font.googlesans_regular, FontWeight.Normal)),
                fontWeight = FontWeight(600),
            )
        }

        Spacer(modifier = Modifier.height(75.dp))

        val genre = genres.firstOrNull() ?: stringResource(R.string.unknown)

        HorizontalThreeOptions(releaseDate = releaseDate, duration = runtime, genre = genre)

        Spacer(modifier = Modifier.height(24.dp))

        // Description Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .testTag("details_description_title"),
            text = stringResource(R.string.description),
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(UiResR.font.googlesans_regular, FontWeight.Normal)),
            fontWeight = FontWeight(600),
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Description
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .testTag("details_description"),
            text = description,
            textAlign = TextAlign.Justify,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(UiResR.font.googlesans_regular, FontWeight.Normal)),
            fontWeight = FontWeight(400),
        )

        Spacer(modifier = Modifier.height(24.dp))

        val listGenres = genres.joinToString(separator = " • ") { it }

        // Genres Action * Horror * Comedy
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .testTag("details_genres"),
            text = listGenres,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(UiResR.font.googlesans_regular, FontWeight.Normal)),
            fontWeight = FontWeight(600),
        )
    }
}

@Composable
fun HorizontalThreeOptions(
    releaseDate: String,
    duration: Int?,
    genre: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(18.dp)
                .testTag("details_release_date_icon")
                .semantics {
                    drawableId = R.drawable.ic_calendar
                },
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = null,
            tint = Color.Gray,
        )

        Spacer(modifier = Modifier.width(4.dp))

        // Release date
        Text(
            text = releaseDate,
            fontSize = 14.sp,
            fontWeight = FontWeight(600),
            fontFamily = FontFamily(Font(UiResR.font.googlesans_regular, FontWeight.Normal)),
            modifier = Modifier.testTag("details_release_date_text")
        )


        if (duration != null) {
            Spacer(modifier = Modifier.width(12.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_vertical_line),
                contentDescription = null,
                tint = Color.Gray,
            )

            Spacer(modifier = Modifier.width(4.dp))

            // Duration
            Text(
                text = stringResource(R.string.runtime, duration),
                fontSize = 14.sp,
                fontWeight = FontWeight(600),
                fontFamily = FontFamily(Font(UiResR.font.googlesans_regular, FontWeight.Normal)),
                modifier = Modifier.testTag("details_duration")
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_vertical_line),
            contentDescription = null,
            tint = Color.Gray,
        )

        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            modifier = Modifier
                .size(18.dp)
                .testTag("details_genre_icon")
                .semantics {
                    drawableId = R.drawable.ic_ticket
                },
            painter = painterResource(id = R.drawable.ic_ticket),
            contentDescription = null,
            tint = Color.Gray,
        )

        Spacer(modifier = Modifier.width(4.dp))

        // Genre
        Text(
            text = genre,
            fontSize = 14.sp,
            fontWeight = FontWeight(600),
            fontFamily = FontFamily(Font(UiResR.font.googlesans_regular, FontWeight.Normal)),
            modifier = Modifier.testTag("details_genre_text")
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailsMovieContentPreview() {
    DetailsMovieContent(
        title = "Spiderman No Way Home",
        description = "Peter Parker's secret identity is revealed to the entire world. Desperate for help, Peter turns to Doctor Strange to make the world forget that he is Spider-Man. The spell goes horribly wrong and shatters the multiverse, bringing in monstrous villains that could destroy the world.",
        posterUrl = "https://cdn.watchmode.com/posters/03173903_poster_w185.jpg",
        backdropUrl = "https://cdn.watchmode.com/backdrops/03173903_bd_w780.jpg",
        genres = listOf("Action", "Comedy"),
        releaseDate = "2021-12-15",
        rating = 9.2,
        runtime = 118,
        isFavorite = false,
        onBackClick = {},
        onFavouriteClick = {}
    )
}
